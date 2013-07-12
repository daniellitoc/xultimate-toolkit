package org.danielli.xultimate.util.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.io.IOUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtilsTest.class);
	
	@Test
	public void testAES() {
		String source = "ultimate";
		PerformanceMonitor.start("testAES");
		Cipher cipher = SymmetricAlgorithms.AES.getCipher();
		Key key = SymmetricAlgorithms.AES.getKey("myKey");
		byte[] result = CipherUtils.encrypt(cipher, key, StringUtils.getBytesUtf8(source));
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(Base64.encodeBase64(result)) });
		PerformanceMonitor.mark("AES encrypt");
		
		source = "3L6N9w+NpT46MjzPfp7XWA==";
		cipher = SymmetricAlgorithms.AES.getCipher();
		key = SymmetricAlgorithms.AES.getKey("myKey");
		result = CipherUtils.decrypt(cipher, key, Base64.decodeBase64(StringUtils.getBytesUtf8(source)));
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(result) });
		PerformanceMonitor.mark("AES decrypt");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testAESFromStream() {
		try {
			PerformanceMonitor.start("testAESFromStream");
			FileInputStream input = new FileInputStream(new File(CipherUtilsTest.class.getResource("/source").toURI()));
			FileOutputStream output = new FileOutputStream(new File("/tmp/source"));
			Cipher cipher = SymmetricAlgorithms.AES.getCipher();
			Key key = SymmetricAlgorithms.AES.getKey("myKey");
			CipherUtils.encrypt(cipher, key, input, output);
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
			PerformanceMonitor.mark("AES encrypt");

			input = new FileInputStream("/tmp/source");
			output = new FileOutputStream(new File(CipherUtilsTest.class.getResource("/source").toURI()));
			cipher = SymmetricAlgorithms.AES.getCipher();
			key = SymmetricAlgorithms.AES.getKey("myKey");
			CipherUtils.decrypt(cipher, key, input, output);
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
			PerformanceMonitor.mark("AES decrypt");
			PerformanceMonitor.stop();
			PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
			PerformanceMonitor.remove();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
//	@Test
	public void testRSA() {
		String source = "ultimate";
		PerformanceMonitor.start("testRSA");
		Cipher cipher = AsymmetricAlgorithms.RSA.getCipher();
		KeyPair keyPair = AsymmetricAlgorithms.RSA.getKeyPair();
		byte[] result = CipherUtils.encrypt(cipher, keyPair.getPublic(), StringUtils.getBytesUtf8(source));
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(Base64.encodeBase64(result)) });
		PerformanceMonitor.mark("RSA encrypt");
		
		source = StringUtils.newStringUtf8(Base64.encodeBase64(result));
		cipher = AsymmetricAlgorithms.RSA.getCipher();
		result = CipherUtils.decrypt(cipher, keyPair.getPrivate(), Base64.decodeBase64(StringUtils.getBytesUtf8(source)));
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(result) });
		PerformanceMonitor.mark("RSA encrypt");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testRSAAndAESKey() {
		String source = "ultimate";
		PerformanceMonitor.start("testRSAAndAESKey");
		Key key = SymmetricAlgorithms.AES.getKey();
		KeyPair keyPair = AsymmetricAlgorithms.RSA.getKeyPair();
		Cipher cipher = AsymmetricAlgorithms.RSA.getCipher();
		byte[] bytes = CipherUtils.wrap(cipher, keyPair.getPublic(), key);
		PerformanceMonitor.mark("wrap key");
		cipher = SymmetricAlgorithms.AES.getCipher();
		byte[] result = CipherUtils.encrypt(cipher, key, StringUtils.getBytesUtf8(source));
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(Base64.encodeBase64(result)) });
		PerformanceMonitor.mark("AES encrypt");
		
		source = StringUtils.newStringUtf8(Base64.encodeBase64(result));
		cipher = AsymmetricAlgorithms.RSA.getCipher();
		key = CipherUtils.unwrap(cipher, keyPair.getPrivate(), bytes, SymmetricAlgorithms.AES);
		PerformanceMonitor.mark("unwrap key");
		cipher = SymmetricAlgorithms.AES.getCipher();
		result = CipherUtils.decrypt(cipher, key, result);
		LOGGER.info("{}: {}", new Object[] { source,  StringUtils.newStringUtf8(result) });
		PerformanceMonitor.mark("AES decrypt");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
}
