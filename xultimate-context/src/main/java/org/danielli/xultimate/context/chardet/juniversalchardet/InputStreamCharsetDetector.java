package org.danielli.xultimate.context.chardet.juniversalchardet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

import org.danielli.xultimate.context.chardet.CharsetDetector;
import org.danielli.xultimate.context.chardet.CharsetDetectorException;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * 输入流字符集检测器。此实现是基于juniversalchardet框架。
 * 
 * @author Danie Li
 * @since 18 Jun 2013
 * @see CharsetDetector
 */
public class InputStreamCharsetDetector implements CharsetDetector<InputStream> {

	@Override
	public Set<Charset> detect(InputStream source) throws CharsetDetectorException {
		DefaultCharsetListener result = new DefaultCharsetListener();
		UniversalDetector detector = new UniversalDetector(result);
		try {
			byte[] buf = new byte[1024];
			int len;
		    while ((len = source.read(buf)) > 0 && !detector.isDone()) {
		      detector.handleData(buf, 0, len);
		    }
		} catch (IOException e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		}
	    detector.dataEnd();
	    return result.getCharsets();
	}

}
