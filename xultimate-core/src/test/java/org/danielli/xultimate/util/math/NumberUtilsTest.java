package org.danielli.xultimate.util.math;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {
	
	@Test
	public void testIsPositiveNumber() {
		Assert.assertFalse(NumberUtils.isPositiveNumber(-1L));
		Assert.assertFalse(NumberUtils.isPositiveNumber(-10000000000000000L));
		Assert.assertFalse(NumberUtils.isPositiveNumber(0));
		Assert.assertFalse(NumberUtils.isPositiveNumber(1.6));
		Assert.assertTrue(NumberUtils.isPositiveNumber(1));
	}

}
