package org.danielli.xultimate.test.core;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.danielli.xultimate.util.CharsetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterTest.class);
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		// "你好"使用的UTF16；getBytes()是使用默认字符集编码。
		LOGGER.info("你好: (平台默认字符集编码){}", Arrays.toString("你好".getBytes()));
		// "你好"使用的UTF16；getBytes()是使用UTF-8编码。
		LOGGER.info("你好: (UTF-8){}", Arrays.toString("你好".getBytes(CharsetUtils.UTF_8)));
		// "你好"使用的UTF16；getBytes()是使用UTF-16编码。
		LOGGER.info("你好: (UTF-16){}", Arrays.toString("你好".getBytes(CharsetUtils.UTF_16)));
		// "你好"使用的UTF16；getBytes()是使用GBK编码。
		LOGGER.info("你好: (GBK){}", Arrays.toString("你好".getBytes("GBK")));
		// "你是"使用的UTF16；getBytes()使用的是GBK字符集编码；new String(byte[], "GBK")是使用GBK字符集解码bytes；解密后还是平台默认字符集；getBytes()使用的也是默认字符集解码。
		LOGGER.info("你好: {}", Arrays.toString(new String("你好".getBytes("GBK"), "GBK").getBytes()));
	}
}
