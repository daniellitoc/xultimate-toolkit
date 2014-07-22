package org.danielli.xultimate.testexample.junit.support;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExceptionTest {

	private static String v1;
	
	private String v2;
	
	@BeforeClass				// 只执行一次
	public static void init() {
		v1 = null;
	}
	
	@Before						// 可以出现多个，测试方法运行前会执行一次。
	public void v2Init() {
		v2 = "v2";
	}
	
	@AfterClass					// 只执行一次，且一定会执行
	public static void destory() {
		v1 = null;
	}
	
	@After						// 可以出现多个，测试方法运行后会执行一次，且一定会执行。
	public void v2Destory() {
		v2 = null;
	}
	
	@Test(expected = NullPointerException.class)
	public void testException() {
		Assert.assertEquals(v1.concat("v1"), "v1");
	}
	
	@Test
	public void testV2() {
		Assert.assertEquals(v2, "v2");
	}
}
