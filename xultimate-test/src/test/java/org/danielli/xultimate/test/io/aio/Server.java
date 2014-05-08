package org.danielli.xultimate.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class Server {
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
		while (next) {
			Future<AsynchronousSocketChannel> socketChannelFuture = socketChannel.accept();
			// 等待接受一个AsynchronousSocketChannel
			AsynchronousSocketChannel socketChannel = socketChannelFuture.get();
			
			ByteBuffer buffer = ByteBuffer.wrap(SerializerUtils.encodeLong(new Date().getTime(), true));
			// 异步开始写的操作
			Future<Integer> integerFuture = socketChannel.write(buffer);
			System.out.println("等待信息写入完毕");
			Integer integer = integerFuture.get();
			System.out.println("信息写入完成，占用" + integer + "个字节");
			socketChannel.close();
			next = false;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		Server server = new Server();
		server.bind();
		server.startInternal();
		server.close();
	}
}
