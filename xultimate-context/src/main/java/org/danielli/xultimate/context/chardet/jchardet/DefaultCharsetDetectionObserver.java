package org.danielli.xultimate.context.chardet.jchardet;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 * 字符集检测器观察者，通过将此实例加入观察者列表，当检测出字符集后，此观察者会收到字符集。
 * 
 * @author Daniel Li
 * @see 18 Jun 2013
 * @see nsICharsetDetectionObserver
 */
public class DefaultCharsetDetectionObserver implements nsICharsetDetectionObserver {
	
	/** 是否检测到了字符集 */
	private boolean found;
	/** 检测到的字符集列表 */
	private Set<Charset> charsets = new HashSet<Charset>();
	
	/**
	 * 通过字符集名称，添加指定字符集到字符集列表。
	 * 
	 * @param charsetName 字符集名称。
	 */
	public void addCharset(String charsetName) {
		charsets.add(Charset.forName(charsetName));
	}
	
	/**
	 * 添加指定字符集到字符集列表。
	 * 
	 * @param charset 字符集。
	 */
	public void addCharset(Charset charset) {
		charsets.add(charset);
	}
	
	/**
	 * 获取字符集列表。
	 * 
	 * @return 字符集列表。
	 */
	public Set<Charset> getCharsets() {
		return Collections.unmodifiableSet(this.charsets);
	}
	
	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
	
	@Override
	public void Notify(String arg0) {
		this.found = true;
		addCharset(arg0);
	}

}
