package org.danielli.xultimate.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class Client {
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 9999)).get();
		ByteBuffer buffer = ByteBuffer.allocate(8);
		Future<Integer> integerFuture = socketChannel.read(buffer);
		System.out.println("等待信息接收完成");
		Integer integer = integerFuture.get();
		System.out.println("信息接收完成，接受到用" + integer + "个字节");
		System.out.println(new Date(SerializerUtils.decodeLong(buffer.array())));
		socketChannel.close();
	}
}
