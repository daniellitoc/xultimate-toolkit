package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import org.danielli.xultimate.core.compression.Compressor;
import org.danielli.xultimate.core.compression.Decompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for any transcoders that may want to work with serialized or
 * compressed data.
 */
public abstract class BaseSerializingTranscoder {

	protected static final Logger log = LoggerFactory.getLogger(BaseSerializingTranscoder.class);

	protected int compressionThreshold = 128;
	
	protected Compressor<byte[], byte[]> compressor;
	
	protected Decompressor<byte[], byte[]> decompressor;
	
	public void setCompressionThreshold(int to) {
		this.compressionThreshold = to;
	}
	
	public void setCompressor(Compressor<byte[], byte[]> compressor) {
		this.compressor = compressor;
	}

	public void setDecompressor(Decompressor<byte[], byte[]> decompressor) {
		this.decompressor = decompressor;
	}

	/**
	 * 压缩。
	 * 
	 * @param in 原始字节流。
	 * @return 压缩后的字节流。
	 */
	public final byte[] compress(byte[] in) {
		return compressor.compress(in);
	}

	/**
	 * 解压缩。
	 * 
	 * @param in 原始字节流。
	 * @return 解压缩后的字节流。
	 */
	protected byte[] decompress(byte[] in) {
		return decompressor.decompress(in);
	}
}
