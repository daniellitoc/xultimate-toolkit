package org.danielli.xultimate.context.chardet.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.danielli.xultimate.context.chardet.CharsetDetectorException;

/**
 * URL类字符集检测器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see AbstractCharsetDetector
 */
public class URLCharsetDetector extends AbstractCharsetDetector<URL> {

	@Override
	public InputStream createInputStream(URL source) throws CharsetDetectorException {
		try {
			return source.openStream();
		} catch (IOException e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		}
	}
}
