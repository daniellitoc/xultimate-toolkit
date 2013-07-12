package org.danielli.xultimate.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * General IO stream manipulation utilities.
 * <p>
 * This class provides static utility methods for input/output operations.
 * <ul>
 * <li>closeQuietly - these methods close a stream ignoring nulls and exceptions
 * <li>toXxx/read - these methods read data from a stream
 * <li>write - these methods write data to a stream
 * <li>copy - these methods copy all the data from one stream to another
 * <li>contentEquals - these methods compare the content of two streams
 * </ul>
 * <p>
 * The byte-to-char methods and char-to-byte methods involve a conversion step.
 * Two methods are provided in each case, one that uses the platform default
 * encoding and the other which allows you to specify an encoding. You are
 * encouraged to always specify an encoding because relying on the platform
 * default can lead to unexpected results, for example when moving from
 * development to production.
 * <p>
 * All the methods in this class that read a stream are buffered internally.
 * This means that there is no cause to use a <code>BufferedInputStream</code>
 * or <code>BufferedReader</code>. The default buffer size of 4K has been shown
 * to be efficient in tests.
 * <p>
 * Wherever possible, the methods in this class do <em>not</em> flush or close
 * the stream. This is to avoid making non-portable assumptions about the
 * streams' origin and further use. Thus the caller is still responsible for
 * closing streams after use.
 * <p>
 * Origin of code: Excalibur.
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.io.IOUtils
 */
public class IOUtils {
	
//	private static final int EOF = -1;
//	
//    /**
//     * The default buffer size ({@value}) to use for 
//     * {@link #copyLarge(InputStream, OutputStream)}
//     * and
//     * {@link #copyLarge(Reader, Writer)}
//     */
//    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
    /**
     * Read bytes from an input stream.
     * This implementation guarantees that it will read as many bytes
     * as possible before giving up; this may not always be the case for
     * subclasses of {@link InputStream}.
     * 
     * @param input where to read input from
     * @param buffer destination
     * @return actual length read; may be less than requested if EOF was reached
     * @throws IOException if a read error occurs
     */
	public static int read(InputStream input, byte[] buffer) throws IOException {
		return org.apache.commons.io.IOUtils.read(input, buffer);
	}
	
//    /**
//     * Read bytes from an input stream.
//     * This implementation guarantees that it will read as many bytes
//     * as possible before giving up; this may not always be the case for
//     * subclasses of {@link InputStream}.
//     * 
//     * @param input where to read input from
//     * @param buffer destination
//     * @return actual length read; may be less than requested if EOF was reached
//     * @throws IOException if a read error occurs
//     * @since 2.2
//     */
//    public static int read(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
//        return input.read(buffer);
//    }
//    
//    /**
//     * Read bytes from an input stream.
//     * This implementation guarantees that it will read as many bytes
//     * as possible before giving up; this may not always be the case for
//     * subclasses of {@link InputStream}.
//     * 
//     * @param input where to read input from
//     * @param buffer destination
//     * @return actual length read; may be less than requested if EOF was reached
//     * @throws IOException if a read error occurs
//     * @since 2.2
//     */
//    public static long read(ScatteringByteChannel input, ByteBuffer[] buffer) throws IOException {
//        return input.read(buffer);
//    }
//	
//    /**
//     * Read bytes from an input stream.
//     * This implementation guarantees that it will read as many bytes
//     * as possible before giving up; this may not always be the case for
//     * subclasses of {@link InputStream}.
//     * 
//     * @param input where to read input from
//     * @param buffer destination
//     * @param offset inital offset into buffer
//     * @param length length to read, must be >= 0
//     * @return actual length read; may be less than requested if EOF was reached
//     * @throws IOException if a read error occurs
//     * @since 2.2
//     */
//    public static long read(ScatteringByteChannel input, ByteBuffer[] buffers, int offset, int length) throws IOException {
//        if (length < 0) {
//            throw new IllegalArgumentException("Length must not be negative: " + length);
//        }
//        return input.read(buffers, offset, length);
//    }
	
    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        return org.apache.commons.io.IOUtils.toByteArray(input);
    }
    
//    /**
//     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
//     * <p>
//     * This method buffers the input internally, so there is no need to use a
//     * <code>BufferedInputStream</code>.
//     * 
//     * @param input  the <code>InputStream</code> to read from
//     * @return the requested byte array
//     * @throws NullPointerException if the input is null
//     * @throws IOException if an I/O error occurs
//     */
//    public static byte[] toByteArray(ReadableByteChannel input) throws IOException {
//        return org.apache.commons.io.IOUtils.toByteArray(input);
//    }
	
    /**
     * Writes bytes from a <code>byte[]</code> to an <code>OutputStream</code>.
     * 
     * @param data  the byte array to write, do not modify during output,
     * null ignored
     * @param output  the <code>OutputStream</code> to write to
     * @throws NullPointerException if output is null
     * @throws IOException if an I/O error occurs
     */
	public static void write(byte[] data, OutputStream output) throws IOException {
		org.apache.commons.io.IOUtils.write(data, output);
	}
	
    /**
     * Fetches entire contents of an <code>InputStream</code> and represent
     * same data as result InputStream.
     * <p>
     * This method is useful where,
     * <ul>
     * <li>Source InputStream is slow.</li>
     * <li>It has network resources associated, so we cannot keep it open for
     * long time.</li>
     * <li>It has network timeout associated.</li>
     * </ul>
     * It can be used in favor of {@link #toByteArray(InputStream)}, since it
     * avoids unnecessary allocation and copy of byte[].<br>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input Stream to be fully buffered.
     * @return A fully buffered stream.
     * @throws IOException if an I/O error occurs
     */
	public static InputStream toBufferedInputStream(InputStream input) throws IOException {
		return org.apache.commons.io.IOUtils.toBufferedInputStream(input);
	}
	
    /**
     * Unconditionally close an <code>InputStream</code>.
     * <p>
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   byte[] data = new byte[1024];
     *   InputStream in = null;
     *   try {
     *       in = new FileInputStream("foo.txt");
     *       in.read(data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     *
     * @param input  the InputStream to close, may be null or already closed
     */
	public static void closeQuietly(InputStream input) {
		org.apache.commons.io.IOUtils.closeQuietly(input);
	}
	
    /**
     * Unconditionally close an <code>OutputStream</code>.
     * <p>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     * byte[] data = "Hello, World".getBytes();
     *
     * OutputStream out = null;
     * try {
     *     out = new FileOutputStream("foo.txt");
     *     out.write(data);
     *     out.close(); //close errors are handled
     * } catch (IOException e) {
     *     // error handling
     * } finally {
     *     IOUtils.closeQuietly(out);
     * }
     * </pre>
     *
     * @param output  the OutputStream to close, may be null or already closed
     */
	public static void closeQuietly(OutputStream output) {
		org.apache.commons.io.IOUtils.closeQuietly(output);
	}
	
    /**
     * Unconditionally close a <code>Closeable</code>.
     * <p>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Closeable closeable = null;
     *   try {
     *       closeable = new FileReader("foo.txt");
     *       // process closeable
     *       closeable.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(closeable);
     *   }
     * </pre>
     *
     * @param closeable the object to close, may be null or already closed
     */
	public static void closeQuietly(Closeable closeable) {
		org.apache.commons.io.IOUtils.closeQuietly(closeable);
	}
	
    /**
     * Unconditionally close an <code>Reader</code>.
     * <p>
     * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   char[] data = new char[1024];
     *   Reader in = null;
     *   try {
     *       in = new FileReader("foo.txt");
     *       in.read(data);
     *       in.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(in);
     *   }
     * </pre>
     * 
     * @param input  the Reader to close, may be null or already closed
     */
	public static void closeQuietly(Reader input) {
		org.apache.commons.io.IOUtils.closeQuietly(input);
	}
	
    /**
     * Unconditionally close a <code>Writer</code>.
     * <p>
     * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Writer out = null;
     *   try {
     *       out = new StringWriter();
     *       out.write("Hello World");
     *       out.close(); //close errors are handled
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(out);
     *   }
     * </pre>
     *
     * @param output  the Writer to close, may be null or already closed
     */
	public static void closeQuietly(Writer output) {
		org.apache.commons.io.IOUtils.closeQuietly(output);
	}
}
