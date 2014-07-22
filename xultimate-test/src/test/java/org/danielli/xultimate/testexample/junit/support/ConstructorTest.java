package org.danielli.xultimate.testexample.junit.support;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConstructorTest {

	private String v1;
	
	private String v2;
	
	public ConstructorTest(String v1, String v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	@Parameters
	public static Collection<String[]> getParameters() {
		String[][] constructors = {
				{ "test1", "test1" },
				{ "test2", "test2" }
		};
		return Arrays.asList(constructors);
	}
	
	@Test(timeout = 10)		// 十秒内必须完成
	public void testEquals() {
		Assert.assertEquals(v1, v2);
	}
	
}
