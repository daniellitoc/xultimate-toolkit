package org.danielli.xultimate.testexample.junit.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ExampleTest {
	
	@Test
	public void testAssertThat() {
		Assert.assertThat(100, Matchers.greaterThan(50));
		
		Assert.assertThat(100, Matchers.allOf(Matchers.greaterThan(50), Matchers.lessThan(150)));
		
		Assert.assertThat(100, Matchers.anyOf(Matchers.greaterThan(50), Matchers.lessThan(150)));
		
		Assert.assertThat(100, Matchers.anything());
		
		String testString = "测试字符串";
		Assert.assertThat(testString, Matchers.containsString("测试"));
		
		List<String> testList = new ArrayList<>();
		testList.add("test1");
		testList.add("test2");
		Assert.assertThat(testList, Matchers.hasItem("test1"));
		
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("testKey1", "testValue1");
		testMap.put("testKey2", "testValue2");
		Assert.assertThat(testMap, Matchers.hasEntry("testKey1", "testValue1"));
		Assert.assertThat(testMap, Matchers.hasKey("testKey1"));
		Assert.assertThat(testMap, Matchers.hasValue("testValue1"));
	}
}
