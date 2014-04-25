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

import java.util.List;

import org.danielli.xultimate.core.serializer.support.BaseTypeDeserializer;
import org.danielli.xultimate.core.serializer.support.BaseTypeSerializer;

/**
 * 通过{@code RpcProtobufSerializer}提供的功能完成序列化/解序列化支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@Sharable
public class ProtobufCodec extends ChannelHandlerAdapter {

//	private RpcProtobufSerializer rpcSerializer;
//	
//	public ProtobufCodec(RpcProtobufSerializer rpcSerializer) {
//		this.rpcSerializer = rpcSerializer;
//	}
	
	protected BaseTypeSerializer serializer;
	
	protected BaseTypeDeserializer deserializer;
	
	public ProtobufCodec(BaseTypeSerializer serializer, BaseTypeDeserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}
	
	private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        	ProtobufCodec.this.encode(ctx, msg, out);
        }
    };

    private final MessageToMessageDecoder<ByteBuf> decoder = new MessageToMessageDecoder<ByteBuf>() {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        	ProtobufCodec.this.decode(ctx, msg, out);
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
//    	// TODO 此处可改进。不一定要4个字节。见Protostuff源码。
//    	byte[] data = rpcSerializer.serialize(msg);
//    	out.add(ctx.alloc().buffer(SerializerUtils.INT_BYTE_SIZE).writeInt(data.length));
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
//    	// TODO 此处可改进。不一定要4个字节。见Protostuff源码。
//    	for (int i = msg.readerIndex(), length = 0; i < msg.readableBytes(); i += length) {
//    		length = msg.getInt(i);
//        	i += SerializerUtils.INT_BYTE_SIZE;
//        	byte[] data = new byte[length];
//        	msg.getBytes(i, data, 0, length);
//            out.add(rpcSerializer.deserialize(data, Object.class));
//    	}
    	ByteBufInputStream inputStream = new ByteBufInputStream(msg);
    	try {
    		while (inputStream.available() > 0) {
    			Object object = deserializer.deserialize(inputStream, Object.class);
        		out.add(object);
        	}
    	} finally {
    		inputStream.close();
    	}
    }
}
