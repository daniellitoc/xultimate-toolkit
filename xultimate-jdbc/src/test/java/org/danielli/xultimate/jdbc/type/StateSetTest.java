package org.danielli.xultimate.jdbc.type;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateSetTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StateSetTest.class);
	
	byte v1 = 1 << 0;
	byte v2 = 1 << 1;
	byte v3 = 1 << 2;
	byte v4 = 1 << 3;
	byte v5 = 1 << 4;
	byte v6 = 1 << 5;
	byte v7 = 1 << 6;
	byte v8 = (byte) ((int) 1 << 7);
	
	@Test
	public void test1() {
		LOGGER.info("{}", v8);
		StateSet set = new StateSet(v8);
		set.add(v7);
//		set.remove(v7);
		LOGGER.info("{}", set.getValue());
		LOGGER.info("{}", v7);
		LOGGER.info("{}", v8);
		LOGGER.info("{}", v5);
		LOGGER.info("{}", v4);
		LOGGER.info("{}", v3);
		LOGGER.info("{}", v2);
		LOGGER.info("{}", v1);
	}
	
	@Test
	public void test2() {
		List<Byte> result = StateSetUtils.getContainStates(new StateSet(v8));
		int i = 0;
		for (Byte b : result) {
			LOGGER.info("{}, {}", ++i, b);
		}
	}
}
