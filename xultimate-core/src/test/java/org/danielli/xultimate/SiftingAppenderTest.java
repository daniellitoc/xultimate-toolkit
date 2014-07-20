package org.danielli.xultimate;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SiftingAppenderTest {
	
	private final String LOGGER_HOST_NAME = "logger.host";
	private final String LOGGER_HOST_VALUE = "127.0.0.1";

	@Before
	public void beforeLogger() {
		MDC.put(LOGGER_HOST_NAME, LOGGER_HOST_VALUE);
	}
	
	@Test
	public void testLogger() {
		Logger logger = LoggerFactory.getLogger(SiftingAppenderTest.class);
		for (int i = 0; i < 1; i++) {
			logger.info("{}", i);
		}
	}
	
}
