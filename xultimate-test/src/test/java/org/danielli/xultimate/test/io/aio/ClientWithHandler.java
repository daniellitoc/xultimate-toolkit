package org.danielli.xultimate.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class ClientWithHandler {
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 9999)).get();
		final ByteBuffer buffer = ByteBuffer.allocate(8);
		socketChannel.read(buffer, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				System.out.println("信息接收完成，接受到用" + result + "个字节");
				System.out.println(new Date(SerializerUtils.decodeLong(buffer.array())));
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
			}
		});
		Thread.sleep(5 * 1000);
		socketChannel.close();
	}
}
