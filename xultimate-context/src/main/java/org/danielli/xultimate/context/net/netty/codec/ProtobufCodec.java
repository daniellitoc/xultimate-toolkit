package org.danielli.xultimate.context.net.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.danielli.xultimate.core.compression.Compressor;
import org.danielli.xultimate.core.compression.Decompressor;
import org.danielli.xultimate.core.compression.support.NullCompressor;
import org.danielli.xultimate.core.io.support.RpcProtobufObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtobufObjectOutput;
import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;

/**
 * 通过{@code RpcProtobufSerializer}提供的功能完成序列化/解序列化支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@Sharable
public class ProtobufCodec extends ChannelHandlerAdapter {
	
	protected KryoGenerator kryoGenerator = ThreadLocalKryoGenerator.INSTANCE;
	
	protected Compressor<byte[], byte[]> compressor = NullCompressor.COMPRESSOR;
	
	protected Decompressor<byte[], byte[]> decompressor = NullCompressor.COMPRESSOR;
	
	protected int bufferSize = 256;
	
	public ProtobufCodec() {

	}
	
	public ProtobufCodec(KryoGenerator kryoGenerator, Compressor<byte[], byte[]> compressor, Decompressor<byte[], byte[]> decompressor) {
		this.kryoGenerator = kryoGenerator;
		this.compressor = compressor;
		this.decompressor = decompressor;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setCompressor(Compressor<byte[], byte[]> compressor) {
		this.compressor = compressor;
	}

	public void setDecompressor(Decompressor<byte[], byte[]> decompressor) {
		this.decompressor = decompressor;
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
    	RpcProtobufObjectOutput protobufObjectOutput = new RpcProtobufObjectOutput(bufferSize, LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize), kryoGenerator.generate()) ;
    	try {
    		protobufObjectOutput.writeObject(msg);
    		byte[] result = protobufObjectOutput.toBytes();
    		out.add(Unpooled.wrappedBuffer(compressor.compress(result)));
    	} finally {
    		protobufObjectOutput.close();
    	}
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//    	RpcProtobufObjectInput protobufObjectInput = new RpcProtobufObjectInput(decompressor.wrapper((new ByteBufInputStream(msg))), bufferSize, LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize), kryoGenerator.generate());
    	final byte[] array;
        if (msg.hasArray()) {
             array = msg.array();
        } else {
        	 int length = msg.readableBytes(); 
             array = new byte[length];
             msg.getBytes(msg.readerIndex(), array, 0, length);
        }
        RpcProtobufObjectInput protobufObjectInput = new RpcProtobufObjectInput(decompressor.decompress(array), LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize), kryoGenerator.generate());
    	try {
    		while (protobufObjectInput.available() > 0) {
    			out.add(protobufObjectInput.readObject());
        	}
    	} finally {
    		protobufObjectInput.close();
    	}
    }
}
