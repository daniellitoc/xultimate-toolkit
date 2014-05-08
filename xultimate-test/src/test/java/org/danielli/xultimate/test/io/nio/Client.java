package org.danielli.xultimate.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class Client {
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9999));
		ByteBuffer buffer = ByteBuffer.allocate(8);
		socketChannel.read(buffer);
		System.out.println(new Date(SerializerUtils.decodeLong(buffer.array())));
		socketChannel.close();

	}
}
