package org.danielli.xultimate.context.net.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.Serializable;
import java.util.List;

import org.danielli.xultimate.core.serializer.support.BaseTypeDeserializer;
import org.danielli.xultimate.core.serializer.support.BaseTypeSerializer;

/**
 * 通过{@code ObjectSerializer}提供的功能完成序列化/解序列化支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@Sharable
public class ObjectCodec extends ChannelHandlerAdapter {

//	private ObjectSerializer rpcSerializer;
//	
//	public ObjectCodec(ObjectSerializer rpcSerializer) {
//		this.rpcSerializer = rpcSerializer;
//	}
	
	protected BaseTypeSerializer serializer;
	
	protected BaseTypeDeserializer deserializer;
	
	public ObjectCodec(BaseTypeSerializer serializer, BaseTypeDeserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}
	
	private final MessageToMessageEncoder<Serializable> encoder = new MessageToMessageEncoder<Serializable>() {

        @Override
        protected void encode(ChannelHandlerContext ctx, Serializable msg, List<Object> out) throws Exception {
        	ObjectCodec.this.encode(ctx, msg, out);
        }
    };

    private final MessageToMessageDecoder<ByteBuf> decoder = new MessageToMessageDecoder<ByteBuf>() {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        	ObjectCodec.this.decode(ctx, msg, out);
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
    
    protected void encode(ChannelHandlerContext ctx, Serializable msg, List<Object> out)  throws Exception {
//    	byte[] data = rpcSerializer.serialize(msg);
//    	out.add(Unpooled.wrappedBuffer(data));
    	ByteBuf byteBuf = Unpooled.directBuffer();
    	ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf);
    	try {
    		serializer.serialize(msg, byteBufOutputStream);
    	} finally {
    		byteBufOutputStream.close();
    	}
    	out.add(byteBuf);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
    	ByteBufInputStream inputStream = new ByteBufInputStream(msg);
    	try {
    		while (inputStream.available() > 0) {
    			Serializable object = deserializer.deserialize(inputStream, Serializable.class);
        		out.add(object);
        	}
    	} finally {
    		inputStream.close();
    	}
    }
}
