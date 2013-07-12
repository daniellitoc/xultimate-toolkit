package org.danielli.xultimate.context.chardet.cpdetector;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.danielli.xultimate.context.chardet.CharsetDetector;
import org.danielli.xultimate.context.chardet.CharsetDetectorException;

/**
 * 输入流字符集检测器。此实现是基于cpdetector框架。
 * 
 * @author Danie Li
 * @since 18 Jun 2013
 * @see CharsetDetector
 */
public class InputStreamCharsetDetector implements CharsetDetector<InputStream> {

	@Override
	public Set<Charset> detect(InputStream source) throws CharsetDetectorException {
		Set<Charset> set = new HashSet<Charset>();
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ByteOrderMarkDetector());
		detector.add(new ParsingDetector(false)); 
		detector.add(JChardetFacade.getInstance()); 
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		try {
			source = new BufferedInputStream(source);
			Charset charset = detector.detectCodepage(source, source.available());
			if (charset != null) {
				set.add(charset);
			}
		} catch (Exception e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		}
		return set;
	}

}
