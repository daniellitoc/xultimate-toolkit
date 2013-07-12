package org.danielli.xultimate.context.chardet.jchardet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

import org.danielli.xultimate.context.chardet.CharsetDetector;
import org.danielli.xultimate.context.chardet.CharsetDetectorException;
import org.danielli.xultimate.util.CharsetUtils;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsPSMDetector;

/**
 * 输入流字符集检测器。此实现是基于jchardet框架。
 * 
 * @author Danie Li
 * @since 18 Jun 2013
 * @see CharsetDetector
 */
public class InputStreamCharsetDetector implements CharsetDetector<InputStream> {

	@Override
	public Set<Charset> detect(InputStream source) throws CharsetDetectorException {
		// Initalize the nsDetector() ;
		nsDetector det = new nsDetector(nsPSMDetector.ALL);
		
		// Set an observer...
		// The Notify() will be called when a matching charset is found.
		DefaultCharsetDetectionObserver result = new DefaultCharsetDetectionObserver();
		det.Init(result);

		boolean isAscii = true;
		boolean isNotEmpty = false;
		try {
			byte[] buf = new byte[1024];
			int len;
			boolean done = false;
			source = new BufferedInputStream(source);
			while ((len = source.read(buf)) != -1) {
				isNotEmpty = true;
				// Check if the stream is only ascii.
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// DoIt if non-ascii and not done yet.
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}
		} catch (IOException e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		}
		
		det.DataEnd();
		if (isNotEmpty) {
			if (isAscii) {
				result.addCharset(CharsetUtils.US_ASCII);
				result.setFound(true);
				
			}

			if (!result.isFound()) {
				String prob[] = det.getProbableCharsets();
				for(int i=0; i<prob.length; i++) {
					result.addCharset(prob[i]);
				}
			}
		}		
		return result.getCharsets();
	}

}
