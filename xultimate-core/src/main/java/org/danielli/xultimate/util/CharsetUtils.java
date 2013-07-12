package org.danielli.xultimate.util;

import java.nio.charset.Charset;

/**
 * Charsets required of every implementation of the Java platform.
 *
 * From the Java documentation <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard
 * charsets</a>:
 * <p>
 * <cite>Every implementation of the Java platform is required to support the following character encodings. Consult the
 * release documentation for your implementation to see if any other encodings are supported. Consult the release
 * documentation for your implementation to see if any other encodings are supported. </cite>
 * </p>
 *
 * <ul>
 * <li><code>US-ASCII</code><br/>
 * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set.</li>
 * <li><code>ISO-8859-1</code><br/>
 * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.</li>
 * <li><code>UTF-8</code><br/>
 * Eight-bit Unicode Transformation Format.</li>
 * <li><code>UTF-16BE</code><br/>
 * Sixteen-bit Unicode Transformation Format, big-endian byte order.</li>
 * <li><code>UTF-16LE</code><br/>
 * Sixteen-bit Unicode Transformation Format, little-endian byte order.</li>
 * <li><code>UTF-16</code><br/>
 * Sixteen-bit Unicode Transformation Format, byte order specified by a mandatory initial byte-order mark (either order
 * accepted on input, big-endian used on output.)</li>
 * </ul>
 *
 * This perhaps would best belong in the Commons Lang project. Even if a similar class is defined in Commons Lang, it is
 * not foreseen that Commons Codec would be made to depend on Commons Lang.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see CharEncoding
 * @see org.apache.commons.lang3.CharEncoding
 * @see org.apache.commons.codec.CharEncoding
 * @see org.apache.commons.codec.Charsets
 */
public class CharsetUtils {
	
	/**
	 * Character encoding names required of every implementation of the Java platform.
	 *
	 * From the Java documentation <a
	 * href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>:
	 * <p>
	 * <cite>Every implementation of the Java platform is required to support the following character encodings. Consult the
	 * release documentation for your implementation to see if any other encodings are supported. Consult the release
	 * documentation for your implementation to see if any other encodings are supported.</cite>
	 * </p>
	 *
	 * <ul>
	 * <li><code>US-ASCII</code><br/>
	 * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set.</li>
	 * <li><code>ISO-8859-1</code><br/>
	 * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.</li>
	 * <li><code>UTF-8</code><br/>
	 * Eight-bit Unicode Transformation Format.</li>
	 * <li><code>UTF-16BE</code><br/>
	 * Sixteen-bit Unicode Transformation Format, big-endian byte order.</li>
	 * <li><code>UTF-16LE</code><br/>
	 * Sixteen-bit Unicode Transformation Format, little-endian byte order.</li>
	 * <li><code>UTF-16</code><br/>
	 * Sixteen-bit Unicode Transformation Format, byte order specified by a mandatory initial byte-order mark (either order
	 * accepted on input, big-endian used on output.)</li>
	 * </ul>
	 *
	 * This perhaps would best belong in the [lang] project. Even if a similar interface is defined in [lang], it is not
	 * foreseen that [codec] would be made to depend on [lang].
	 *
	 * <p>
	 * This class is immutable and thread-safe.
	 * </p>
	 *
	 * @author Daniel Li
	 * @since 16 Jun 2013
	 * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	 * @see org.apache.commons.codec.CharEncoding
	 */
	public static class CharEncoding {
		 /**
	     * CharEncodingISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String ISO_8859_1 = "ISO-8859-1";

	    /**
	     * Seven-bit ASCII, also known as ISO646-US, also known as the Basic Latin block of the Unicode character set.
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String US_ASCII = "US-ASCII";

	    /**
	     * Sixteen-bit Unicode Transformation Format, The byte order specified by a mandatory initial byte-order mark
	     * (either order accepted on input, big-endian used on output)
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String UTF_16 = "UTF-16";

	    /**
	     * Sixteen-bit Unicode Transformation Format, big-endian byte order.
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String UTF_16BE = "UTF-16BE";

	    /**
	     * Sixteen-bit Unicode Transformation Format, little-endian byte order.
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String UTF_16LE = "UTF-16LE";

	    /**
	     * Eight-bit Unicode Transformation Format.
	     * <p>
	     * Every implementation of the Java platform is required to support this character encoding.
	     *
	     * @see <a href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
	     */
	    public static final String UTF_8 = "UTF-8";
	}
	
	/**
     * CharEncodingISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset ISO_8859_1 = Charset.forName(CharEncoding.ISO_8859_1);

    /**
     * Seven-bit ASCII, also known as ISO646-US, also known as the Basic Latin block of the Unicode character set.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset US_ASCII = Charset.forName(CharEncoding.US_ASCII);

    /**
     * Sixteen-bit Unicode Transformation Format, The byte order specified by a mandatory initial byte-order mark
     * (either order accepted on input, big-endian used on output)
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset UTF_16 = Charset.forName(CharEncoding.UTF_16);

    /**
     * Sixteen-bit Unicode Transformation Format, big-endian byte order.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset UTF_16BE = Charset.forName(CharEncoding.UTF_16BE);

    /**
     * Sixteen-bit Unicode Transformation Format, little-endian byte order.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset UTF_16LE = Charset.forName(CharEncoding.UTF_16LE);

    /**
     * Eight-bit Unicode Transformation Format.
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     *
     * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     */
    public static final Charset UTF_8 = Charset.forName(CharEncoding.UTF_8);
    
    /**
     * Returns a Charset for the named charset. If the name is null, return the default Charset.
     *
     * @param charset
     *            The name of the requested charset, may be null.
     * @return a Charset for the named charset
     * @throws java.nio.charset.UnsupportedCharsetException
     *             If the named charset is unavailable
     */
    public static Charset toCharset(String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }
    
    /**
     * Returns the given Charset or the default Charset if the given Charset is null.
     *
     * @param charset
     *            A charset or null.
     * @return the given Charset or the default Charset if the given Charset is null
     */
    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }
    
    /**
     * <p>Returns whether the named charset is supported.</p>
     *
     * <p>This is similar to <a
     * href="http://download.oracle.com/javase/1.4.2/docs/api/java/nio/charset/Charset.html#isSupported%28java.lang.String%29">
     * java.nio.charset.Charset.isSupported(String)</a> but handles more formats</p>
     *
     * @param name  the name of the requested charset; may be either a canonical name or an alias, null returns false
     * @return {@code true} if the charset is available in the current Java virtual machine
     */
    public static boolean isSupported(String name) {
        return org.apache.commons.lang3.CharEncoding.isSupported(name);
    }
}
