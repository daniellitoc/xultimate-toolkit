package org.danielli.xultimate.core.compression.support;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.compression.Compressor;
import org.danielli.xultimate.core.compression.CompressorException;
import org.danielli.xultimate.core.compression.Decompressor;
import org.danielli.xultimate.core.compression.DecompressorException;

/**
 * 空实现。
 * 
 * @author Daniel Li
 * @since 08 May 2014
 * @see Compressor
 * @see Decompressor
 */
public class NullCompressor implements Compressor<byte[], byte[]>, Decompressor<byte[], byte[]> {
	
	public static final NullCompressor COMPRESSOR = new NullCompressor();

	private NullCompressor() {
	}
	
	@Override
	public byte[] decompress(byte[] source) throws DecompressorException {
		return source;
	}

	@Override
	public InputStream wrapper(InputStream sourceInputStream) throws DecompressorException {
		return sourceInputStream;
	}

	@Override
	public byte[] compress(byte[] source) throws CompressorException {
		return source;
	}

	@Override
	public OutputStream wrapper(OutputStream sourceOutputStream) throws CompressorException {
		return sourceOutputStream;
	}

}
