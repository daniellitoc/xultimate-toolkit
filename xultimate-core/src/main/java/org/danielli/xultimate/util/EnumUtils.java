package org.danielli.xultimate.util;

import java.util.List;

/**
 * <p>Utility library to provide helper methods for Java enums.</p>
 *
 * <p>#ThreadSafe#</p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.EnumUtils
 */
public class EnumUtils {
	
    /**
     * <p>Gets the enum for the class, returning {@code null} if not found.</p>
     *
     * <p>This method differs from {@link Enum#valueOf} in that it does not throw an exception
     * for an invalid enum name.</p>
     *
     * @param <E> the type of the enumeration
     * @param enumClass  the class of the enum to query, not null
     * @param enumName   the enum name, null returns null
     * @return the enum, null if not found
     */
	public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName) {
		return org.apache.commons.lang3.EnumUtils.getEnum(enumClass, enumName);
	}
	
    /**
     * <p>Gets the enum for the class, returning {@code null} if not found.</p>
     *
     * @param <E> the type of the enumeration
     * @param enumClass  the class of the enum to query, not null
     * @param ordinal   the enum index
     * @return the enum, null if not found
     */
	public static <E extends Enum<E>> E getEnum(Class<E> enumClass, int ordinal) {
		List<E> enumList = org.apache.commons.lang3.EnumUtils.getEnumList(enumClass);
		if (ordinal < 0 || ordinal >= enumList.size()) {
		      return null;
		}
		return enumList.get(ordinal);
	}
	
    /**
     * <p>Gets the {@code List} of enums.</p>
     *
     * <p>This method is useful when you need a list of enums rather than an array.</p>
     *
     * @param <E> the type of the enumeration
     * @param enumClass  the class of the enum to query, not null
     * @return the modifiable list of enums, never null
     */
    public static <E extends Enum<E>> List<E> getEnumList(Class<E> enumClass) {
        return org.apache.commons.lang3.EnumUtils.getEnumList(enumClass);
    }
	
}
