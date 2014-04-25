package org.danielli.xultimate.core.compression.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.danielli.xultimate.core.compression.Compressor;
import org.danielli.xultimate.core.compression.CompressorException;
import org.danielli.xultimate.core.compression.Decompressor;
import org.danielli.xultimate.core.compression.DecompressorException;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * GZip压缩和解压缩器。处理byte[]到byte[]类型。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Compressor
 * @see Decompressor
 */
public class GZipCompressor implements Compressor<byte[], byte[]>, Decompressor<byte[], byte[]> {

	@Override
	public byte[] decompress(byte[] source) throws DecompressorException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(source);
		GZIPInputStream gzipInputStream = null;
		try {
			gzipInputStream = new GZIPInputStream(inputStream);
			return IOUtils.toByteArray(gzipInputStream);
		} catch (Exception e) {
			throw new CompressorException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(gzipInputStream);
		}
	}

	@Override
	public InputStream wrapper(InputStream sourceInputStream) throws DecompressorException {
		try {
			GZIPInputStream gzipInputStream = new GZIPInputStream(sourceInputStream);
			return gzipInputStream;
		} catch (Exception e) {
			throw new DecompressorException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] compress(byte[] source) throws CompressorException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = null;
		try {
			gzipOutputStream = new GZIPOutputStream(outputStream);
			gzipOutputStream.write(source);
		} catch (Exception e) {
			throw new CompressorException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(gzipOutputStream);
		}
		return outputStream.toByteArray();
	}

	@Override
	public OutputStream wrapper(OutputStream sourceOutputStream) throws CompressorException {
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(sourceOutputStream);
			return gzipOutputStream;
		} catch (Exception e) {
			throw new CompressorException(e.getMessage(), e);
		}
	}
}
