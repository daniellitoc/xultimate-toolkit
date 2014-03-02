package org.danielli.xultimate.jdbc.type;

import java.util.List;

import org.junit.Test;

public class StateSetTest {

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
		System.out.println(v8);
		StateSet set = new StateSet(v8);
		set.add(v7);
//		set.remove(v7);
		System.out.println(set.getValue());
		System.out.println(set.contain(v7));
		System.out.println(set.contain(v6));
		System.out.println(set.contain(v8));
	}
	
	@Test
	public void test2() {
		List<Byte> result = StateSetUtils.getContainStates(new StateSet(v8));
		int i = 0;
		for (Byte b : result) {
			System.out.println(++i + ", " + b);
		}
	}
}
