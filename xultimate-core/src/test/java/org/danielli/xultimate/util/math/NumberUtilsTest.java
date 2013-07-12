package org.danielli.xultimate.util.math;

import org.danielli.xultimate.util.BeanUtilsTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtilsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilsTest.class);
	
	@Test
	public void testIsPositiveNumber() {
		LOGGER.info("{}", NumberUtils.isPositiveNumber(-1L));
		LOGGER.info("{}", NumberUtils.isPositiveNumber(-10000000000000000L));
		LOGGER.info("{}", NumberUtils.isPositiveNumber(0));
		LOGGER.info("{}", NumberUtils.isPositiveNumber(1.6));
	}

}
