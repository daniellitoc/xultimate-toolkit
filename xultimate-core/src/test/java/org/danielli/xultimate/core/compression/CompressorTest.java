package org.danielli.xultimate.core.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.core.compression.support.GZipCompressor;
import org.danielli.xultimate.core.compression.support.SnappyJavaCompressor;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.io.IOUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-compression.xml" })
public class CompressorTest {

	@Resource(name = "gZipCompressor")
	private GZipCompressor gZipCompressor;
	
	@Resource(name = "snappyJavaCompressor")
	private SnappyJavaCompressor snappyJavaCompressor;
	
//	@Test
	public void testBase() throws IOException {
		String value = "I'am Daniel Li";
		Assert.assertEquals(value, StringUtils.newStringUtf8(gZipCompressor.decompress(gZipCompressor.compress(StringUtils.getBytesUtf8(value)))));
		Assert.assertEquals(value, StringUtils.newStringUtf8(snappyJavaCompressor.decompress(snappyJavaCompressor.compress(StringUtils.getBytesUtf8(value)))));
		
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			OutputStream gzipOutputStream = gZipCompressor.wrapper(outputStream);
			gzipOutputStream.write(StringUtils.getBytesUtf8("I'am "));
			gzipOutputStream.write(StringUtils.getBytesUtf8("Daniel Li"));
			gzipOutputStream.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			InputStream gzipInputStream = gZipCompressor.wrapper(inputStream);
			Assert.assertEquals(value, StringUtils.newStringUtf8(IOUtils.toByteArray(gzipInputStream)));
			gzipInputStream.close();
		}
		
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			OutputStream snappyOutputStream = snappyJavaCompressor.wrapper(outputStream);
			snappyOutputStream.write(StringUtils.getBytesUtf8("I'am "));
			snappyOutputStream.write(StringUtils.getBytesUtf8("Daniel Li"));
			snappyOutputStream.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			InputStream snappyInputStream = snappyJavaCompressor.wrapper(inputStream);
			Assert.assertEquals(value, StringUtils.newStringUtf8(IOUtils.toByteArray(snappyInputStream)));
			snappyInputStream.close();
		}
	}
	
	@Test
	public void test() throws IOException {
		String value = "I'am Daniel Li";
		PerformanceMonitor.start("CompressorTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				StringUtils.newStringUtf8(gZipCompressor.decompress(gZipCompressor.compress(StringUtils.getBytesUtf8(value))));
			}
			PerformanceMonitor.mark("gZipCompressor" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				StringUtils.newStringUtf8(snappyJavaCompressor.decompress(snappyJavaCompressor.compress(StringUtils.getBytesUtf8(value))));
			}
			PerformanceMonitor.mark("snappyJavaCompressor" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				OutputStream gzipOutputStream = gZipCompressor.wrapper(outputStream);
				gzipOutputStream.write(StringUtils.getBytesUtf8("I'am "));
				gzipOutputStream.write(StringUtils.getBytesUtf8("Daniel Li"));
				gzipOutputStream.close();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				InputStream gzipInputStream = gZipCompressor.wrapper(inputStream);
				StringUtils.newStringUtf8(IOUtils.toByteArray(gzipInputStream));
				gzipInputStream.close();
			}
			PerformanceMonitor.mark("gZipCompressor IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				OutputStream snappyOutputStream = snappyJavaCompressor.wrapper(outputStream);
				snappyOutputStream.write(StringUtils.getBytesUtf8("I'am "));
				snappyOutputStream.write(StringUtils.getBytesUtf8("Daniel Li"));
				snappyOutputStream.close();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				InputStream snappyInputStream = snappyJavaCompressor.wrapper(inputStream);
				StringUtils.newStringUtf8(IOUtils.toByteArray(snappyInputStream));
				snappyInputStream.close();
			}
			PerformanceMonitor.mark("snappyJavaCompressor IO" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
