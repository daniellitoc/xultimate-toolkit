package org.danielli.xultimate.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class ServerWithHandler {
	private AsynchronousServerSocketChannel socketChannel;
	
	private volatile boolean next = true;
	
	public void bind() throws IOException {
		socketChannel = AsynchronousServerSocketChannel.open();
		socketChannel.bind(new InetSocketAddress(9999));
	}
	
	public void close() throws IOException {
		next = false;
		socketChannel.close();
	}
	
	public void startInternal() throws InterruptedException, ExecutionException, IOException {
			Future<AsynchronousSocketChannel> socketChannelFuture = socketChannel.accept();
			AsynchronousSocketChannel socketChannel = socketChannelFuture.get();
			ByteBuffer buffer = ByteBuffer.wrap(SerializerUtils.encodeLong(new Date().getTime(), true));
			socketChannel.write(buffer, null, new CompletionHandler<Integer, Object>() {
				@Override
				public void completed(Integer result, Object attachment) {
					System.out.println("信息写入完成，占用" + result + "个字节");
					next = false;
					if (next) {
						try {
							startInternal();
						} catch (InterruptedException | ExecutionException | IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				@Override
				public void failed(Throwable exc, Object attachment) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		ServerWithHandler server = new ServerWithHandler();
		server.bind();
		server.startInternal();
		System.out.println("开始等待");
		while (server.next) {
			Thread.yield();
		}
		server.close();
	}
}
