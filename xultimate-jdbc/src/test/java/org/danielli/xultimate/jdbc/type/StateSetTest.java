package org.danielli.xultimate.jdbc.type;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateSetTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StateSetTest.class);
	
	public enum TestEnum {
		v1, v2, v3, v4
	}
	
	@Test
	public void test1() {
		LOGGER.info("{}", TestEnum.v4);
		StateSet<TestEnum> set = StateSet.of(TestEnum.class);
		set.add(TestEnum.v4);
		set.add(TestEnum.v3);
		set.remove(TestEnum.v3);
		LOGGER.info("{}", set.getValue());
		LOGGER.info("{}", TestEnum.v2);
		LOGGER.info("{}", TestEnum.v1);
	}
	
	@Test
	public void test2() {
		StateSet<TestEnum> set = StateSet.of(TestEnum.class);
		set.add(TestEnum.v4);
		List<Byte> result = StateSet.getContainStates(set);
		int i = 0;
		for (Byte b : result) {
			LOGGER.info("{}, {}", ++i, b);
		}
	}
}
