package org.danielli.xultimate.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class Server {
	private ServerSocketChannel serverSocketChannel;
	
	private volatile boolean next = true;
	
	private Selector selector; 
	
	public void bind() throws IOException {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(9999));
		serverSocketChannel.configureBlocking(false);
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void close() throws IOException {
		selector.close();
		next = false;
		serverSocketChannel.close();
	}
	
	public void startInternal() throws InterruptedException, ExecutionException, IOException {
		while (next) {
			int channelCount = selector.select();
			if (channelCount == 0 ) continue;
			
			Iterator<SelectionKey> iterator =  selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				if (selectionKey.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
					SocketChannel socketChannel = server.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_WRITE);
				} else if (selectionKey.isWritable()) {
					ByteBuffer buffer = ByteBuffer.wrap(SerializerUtils.encodeLong(new Date().getTime(), true));
					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
					socketChannel.write(buffer);
					next = false;
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		Server server = new Server();
		server.bind();
		server.startInternal();
		server.close();
	}
}
