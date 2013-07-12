package org.danielli.xultimate.context.chardet.icu4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.danielli.xultimate.context.chardet.CharsetDetector;
import org.danielli.xultimate.context.chardet.CharsetDetectorException;

import com.ibm.icu.text.CharsetMatch;

/**
 * 输入流字符集检测器。此实现是基于icu4j框架。
 * 
 * @author Danie Li
 * @since 18 Jun 2013
 * @see CharsetDetector
 */
public class InputStreamCharsetDetector implements CharsetDetector<InputStream> {

	@Override
	public Set<Charset> detect(InputStream source) throws CharsetDetectorException {
		Set<Charset> set = new HashSet<Charset>();
		com.ibm.icu.text.CharsetDetector charsetDetector = new com.ibm.icu.text.CharsetDetector();
		try {
			charsetDetector.setText(new BufferedInputStream(source));
			CharsetMatch[] charsetMatchs = charsetDetector.detectAll();
			for (CharsetMatch match : charsetMatchs) {
				set.add(Charset.forName(match.getName()));
			}
		} catch (IOException e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		}
		return set;
	}

}
