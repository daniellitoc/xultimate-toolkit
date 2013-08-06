package org.danielli.xultimate.context.kvStore.memcached.xmemcached;


import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import net.rubyeye.xmemcached.transcoders.CompressionMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for any transcoders that may want to work with serialized or
 * compressed data.
 */
public abstract class BaseSerializingTranscoder {

	/**
	 * Default compression threshold value.
	 */
	public static final int DEFAULT_COMPRESSION_THRESHOLD = 16384;

	public static final String DEFAULT_CHARSET = "UTF-8";

	protected int compressionThreshold = DEFAULT_COMPRESSION_THRESHOLD;
	protected CompressionMode compressMode = CompressionMode.GZIP;
	protected static final Logger log = LoggerFactory.getLogger(BaseSerializingTranscoder.class);

	/**
	 * Set the compression threshold to the given number of bytes. This
	 * transcoder will attempt to compress any data being stored that's larger
	 * than this.
	 * 
	 * @param to
	 *            the number of bytes
	 */
	public void setCompressionThreshold(int to) {
		this.compressionThreshold = to;
	}

	public CompressionMode getCompressMode() {
		return compressMode;
	}

	public void setCompressionMode(CompressionMode compressMode) {
		this.compressMode = compressMode;
	}

	/**
	 * Compress the given array of bytes.
	 */
	public final byte[] compress(byte[] in) {
		switch (this.compressMode) {
		case GZIP:
			return gzipCompress(in);
		case ZIP:
			return zipCompress(in);
		default:
			return gzipCompress(in);
		}

	}

	private byte[] zipCompress(byte[] in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(in.length);
		DeflaterOutputStream os = new DeflaterOutputStream(baos);
		try {
			os.write(in);
			os.finish();
			try {
				os.close();
			} catch (IOException e) {
				log.error("Close DeflaterOutputStream error", e);
			}
		} catch (IOException e) {
			throw new RuntimeException("IO exception compressing data", e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				log.error("Close ByteArrayOutputStream error", e);
			}
		}
		return baos.toByteArray();
	}

	private static byte[] gzipCompress(byte[] in) {
		if (in == null) {
			throw new NullPointerException("Can't compress null");
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gz = null;
		try {
			gz = new GZIPOutputStream(bos);
			gz.write(in);
		} catch (IOException e) {
			throw new RuntimeException("IO exception compressing data", e);
		} finally {
			if (gz != null) {
				try {
					gz.close();
				} catch (IOException e) {
					log.error("Close GZIPOutputStream error", e);
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					log.error("Close ByteArrayOutputStream error", e);
				}
			}
		}
		byte[] rv = bos.toByteArray();
		// log.debug("Compressed %d bytes to %d", in.length, rv.length);
		return rv;
	}

	static int COMPRESS_RATIO = 8;

	/**
	 * Decompress the given array of bytes.
	 * 
	 * @return null if the bytes cannot be decompressed
	 */
	protected byte[] decompress(byte[] in) {
		switch (this.compressMode) {
		case GZIP:
			return gzipDecompress(in);
		case ZIP:
			return zipDecompress(in);
		default:
			return gzipDecompress(in);
		}
	}

	@SuppressWarnings("resource")
	private byte[] zipDecompress(byte[] in) {
		int size = in.length * COMPRESS_RATIO;
		ByteArrayInputStream bais = new ByteArrayInputStream(in);
		InflaterInputStream is = new InflaterInputStream(bais);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
		try {
			byte[] uncompressMessage = new byte[size];
			while (true) {
				int len = is.read(uncompressMessage);
				if (len <= 0) {
					break;
				}
				baos.write(uncompressMessage, 0, len);
			}
			baos.flush();
			return baos.toByteArray();

		} catch (IOException e) {
			log.error("Failed to decompress data", e);
			baos = null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error("failed to close InflaterInputStream");
			}
			try {
				bais.close();
			} catch (IOException e) {
				log.error("failed to close ByteArrayInputStream");
			}
			try {
				baos.close();
			} catch (IOException e) {
				log.error("failed to close ByteArrayOutputStream");
			}
		}
		return baos == null ? null : baos.toByteArray();
	}

	private byte[] gzipDecompress(byte[] in) {
		ByteArrayOutputStream bos = null;
		if (in != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(in);
			bos = new ByteArrayOutputStream();
			GZIPInputStream gis = null;
			try {
				gis = new GZIPInputStream(bis);

				byte[] buf = new byte[16 * 1024];
				int r = -1;
				while ((r = gis.read(buf)) > 0) {
					bos.write(buf, 0, r);
				}
			} catch (IOException e) {
				log.error("Failed to decompress data", e);
				bos = null;
			} finally {
				if (gis != null) {
					try {
						gis.close();
					} catch (IOException e) {
						log.error("Close GZIPInputStream error", e);
					}
				}
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						log.error("Close ByteArrayInputStream error", e);
					}
				}
			}
		}
		return bos == null ? null : bos.toByteArray();
	}
}
