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

import org.danielli.xultimate.core.serializer.kryo.RpcKryoSerializer;

import com.esotericsoftware.kryo.io.Input;

/**
 * 通过{@code RpcKryoSerializer}提供的功能完成序列化/解序列化支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@Sharable
public class KryoCodec extends ChannelHandlerAdapter {

	private RpcKryoSerializer rpcSerializer;
	
	public KryoCodec(RpcKryoSerializer rpcSerializer) {
		this.rpcSerializer = rpcSerializer;
	}
	
	private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        	KryoCodec.this.encode(ctx, msg, out);
        }
    };

    private final MessageToMessageDecoder<ByteBuf> decoder = new MessageToMessageDecoder<ByteBuf>() {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        	KryoCodec.this.decode(ctx, msg, out);
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
    	byte[] data = rpcSerializer.serialize(msg);
    	out.add(Unpooled.wrappedBuffer(data));
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    	Input input = new Input(new ByteBufInputStream(msg));
    	try {
    		while (input.available() > 0) {
        		Object object = rpcSerializer.deserialize(input, Object.class);
    			out.add(object);
        	}
    	} finally {
    		input.close();
    	}
    	
    }
}
