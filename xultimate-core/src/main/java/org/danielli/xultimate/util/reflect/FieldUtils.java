package org.danielli.xultimate.util.reflect;

/**
 * Utilities for working with fields by reflection. Adapted and refactored
 * from the dormant [reflect] Commons sandbox component.
 * <p>
 * The ability is provided to break the scoping restrictions coded by the
 * programmer. This can allow fields to be changed that shouldn't be. This
 * facility should be used with care.
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.reflect.FieldUtils
 */
public class FieldUtils {
	
    /**
     * Reads the named public field. Only the class of the specified object will be considered.
     * @param target  the object to reflect, must not be null
     * @param fieldName  the field name to obtain
     * @return the value of the field
     * @throws IllegalArgumentException if the class or field name is null
     * @throws IllegalAccessException if the named field is not public
     */
	public static Object readDeclaredField(Object target, String fieldName) throws IllegalAccessException {
		return org.apache.commons.lang3.reflect.FieldUtils.readDeclaredField(target, fieldName);
	}
	
    /**
     * Writes a public field. Only the specified class will be considered.
     * @param target  the object to reflect, must not be null
     * @param fieldName  the field name to obtain
     * @param value to set
     * @throws IllegalArgumentException if <code>target</code> or <code>fieldName</code> is null
     * @throws IllegalAccessException if the field is not made accessible
     */
	public static void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
		org.apache.commons.lang3.reflect.FieldUtils.writeDeclaredField(target, fieldName, value);
	}
}
