package org.danielli.xultimate.jdbc.type;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态集工具类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class StateSetUtils {

	/**
	 * 计算byte范围内，包含了指定状态的值列表。
	 * @param stateSet 状态集。
	 * @return 包含了指定状态的值列表。
	 */
	public static List<Byte> getContainStates(StateSet stateSet) {
		byte currentStates = stateSet.getValue();
		List<Byte> result = new ArrayList<>();
		for (byte i = 0; i < Byte.MAX_VALUE; i++) {
			if ((i & currentStates) == currentStates) {
				result.add(i);
			}
		}
		return result;
	}
}
