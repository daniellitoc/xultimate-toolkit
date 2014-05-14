package org.danielli.xultimate.context.net.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.danielli.xultimate.core.io.support.RpcProtostuffObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtostuffObjectOutput;
import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;

/**
 * 通过{@code RpcProtostuffSerializer}提供的功能完成序列化/解序列化支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@Sharable
public class RpcProtostuffObjectCodec extends ChannelHandlerAdapter {

	protected KryoGenerator kryoGenerator = ThreadLocalKryoGenerator.INSTANCE;
	
	protected int bufferSize = 256;
	
	public RpcProtostuffObjectCodec() {

	}
	
	public RpcProtostuffObjectCodec(KryoGenerator kryoGenerator) {
		this.kryoGenerator = kryoGenerator;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        	RpcProtostuffObjectCodec.this.encode(ctx, msg, out);
        }
    };

    private final MessageToMessageDecoder<ByteBuf> decoder = new MessageToMessageDecoder<ByteBuf>() {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        	RpcProtostuffObjectCodec.this.decode(ctx, msg, out);
        }
    };
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        decoder.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        encoder.write(ctx, msg, promise);
    }
    
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out)  throws Exception {
    	RpcProtostuffObjectOutput protostuffObjectOutput = new RpcProtostuffObjectOutput(bufferSize, LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize), kryoGenerator.generate()) ;
    	try {
    		protostuffObjectOutput.writeObject(msg);
    		byte[] result = protostuffObjectOutput.toBytes();
    		out.add(Unpooled.wrappedBuffer(result));
    	} finally {
    		protostuffObjectOutput.close();
    	}
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    	RpcProtostuffObjectInput protostuffObjectInput = new RpcProtostuffObjectInput(new ByteBufInputStream(msg), bufferSize, kryoGenerator.generate());
    	try {
    		while (protostuffObjectInput.available() > 0) {
    			out.add(protostuffObjectInput.readObject());
        	}
    	} finally {
    		protostuffObjectInput.close();
    	}
    }
}
