package org.danielli.xultimate.jdbc.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.EnumUtils;


/**
 * 替换MySQL当中的BIT或SET数据类型。
 * 
 * <pre>
 * 	计划：采用-128-127，支持4个类型，可表示为15种不同状态
 * 	这样查询时完整匹配使用SELECT C_XX FROM T_XX WHERE C_XX = ?形式;
 *  需要进行部分查询，如查询某一类型的本可以使用SELECT C_XX FROM T_XX WHERE C_XX & ?，但这种方式无法应用索引。
 *  所以想通过SELECT C_XX FROM T_XX WHERE C_XX IN (?, ?..)这种形式来完成。
 *  如3(0011)对应(3, 7(0111), 11(1011), 15(1111)...)
 * </pre> 
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class StateSet<E extends Enum<E>> implements Serializable {
	private static final long serialVersionUID = 2728631987057993625L;
	
	private final Class<E> elementType;
	
	private byte elements;
	
	private StateSet(Class<E> elementType) {
		this.elementType = elementType;
		clear();
	}
	
	/**
	 * 创建实例。
	 * @param elementType 枚举类型。
	 * @return StateSet实例。
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E extends Enum<E>> StateSet<E> of(Class<E> elementType) {
		List<E> elements = EnumUtils.getEnumList(elementType);
		Assert.isTrue(elements.size() <= 4, "this elementType is required; it must be enum, it's size must <= 4");
	    return new StateSet(elementType);
	}
	
	/**
	 * 计算byte范围内，包含了指定状态的值列表。
	 * @param stateSet 状态集。
	 * @return 包含了指定状态的值列表。
	 */
	public static <E extends Enum<E>> List<Byte> getContainStates(StateSet<E> stateSet) {
		byte currentStates = stateSet.getValue();
		List<Byte> result = new ArrayList<>();
		for (short i = 0; i <= 15; i++) {
			if ((i & currentStates) == currentStates) {
				result.add((byte) i);
			}
		}
		return result;
	}
	
	public byte getValue() {
		return elements;
	}
	
	public void setValue(byte value) {
		this.elements = value;
	}
	
    public int size() {
        return Integer.bitCount(elements);
    }
	
    public boolean isEmpty() {
        return elements == 0;
    }
	
    public boolean add(E state) {
        typeCheck(state);

        long oldElements = elements;
        elements |= (1L << state.ordinal());
        return elements != oldElements;
    }
	
	public boolean remove(E state) {
        if (state == null)
            return false;
        Class<?> eClass = state.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        long oldElements = elements;
        elements &= ~(1L << state.ordinal());
        return elements != oldElements;
    }
	
	public boolean contain(E state) {
        if (state == null)
            return false;
        Class<?> eClass = state.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        return (elements & (1L << state.ordinal())) != 0;
    }
	
	public void clear() {
		elements = 0;
	}
	
    final void typeCheck(E e) {
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            throw new ClassCastException(eClass + " != " + elementType);
    }
}
