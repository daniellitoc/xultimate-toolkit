package org.danielli.xultimate.util.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.AnnotationUtils;

/**
 * <p>
 * Assists in implementing {@link Object#hashCode()} methods.
 * </p>
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see HashCodeBuilder
 */
public class HashCodeBuilderUtils {
	
	/**
	 * 本地缓存。
	 */
	private static ConcurrentHashMap<Class<?>, Field[]> fieldHashMap = new ConcurrentHashMap<>();
	
    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>    
     */
	public static int reflectionHashCode(Object object) {
        return reflectionHashCode(17, 37, object);
    }
	
    /**
     * <p>
     * This method uses reflection to build a valid hash code.
     * </p>
     *
     * @param initialNonZeroOddNumber
     *            a non-zero, odd number used as the initial value
     * @param multiplierNonZeroOddNumber
     *            a non-zero, odd number used as the multiplier
     * @param object
     *            the Object to create a <code>hashCode</code> for
     * @return int hash code
     * @throws IllegalArgumentException
     *             if the Object is <code>null</code>
     * @throws IllegalArgumentException
     *             if the number is zero or even
     */
	public static <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object) {
		if (object == null) {
            throw new IllegalArgumentException("The object to build a hash code for must not be null");
        }
		HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
		Class<?> clazz = object.getClass();
        reflectionAppend(object, clazz, builder);
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            reflectionAppend(object, clazz, builder);
        }
        return builder.toHashCode();
	}
	
	private static void reflectionAppend(Object object, Class<?> clazz, HashCodeBuilder builder) {
		Field[] classFields = fieldHashMap.get(clazz);
		if (classFields == null) {
			List<Field> classFieldList = new ArrayList<>();
			Field[] declaredFields = clazz.getDeclaredFields();
			AccessibleObject.setAccessible(declaredFields, true);
			boolean classHas = ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(AnnotationUtils.getAnnotation(clazz, Buildable.class)), BuildType.HASH_CODE);
			for (Field field : declaredFields) {
	    		Buildable fieldBuildable = AnnotationUtils.getAnnotation(field, Buildable.class);
	    		if (ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(fieldBuildable), BuildType.HASH_CODE) 
	    				|| classHas && fieldBuildable == null 
	    				&& (field.getName().indexOf('$') == -1) 
	    				&& (!Modifier.isTransient(field.getModifiers())) 
	    				&& (!Modifier.isStatic(field.getModifiers()))
	    				) {
	    			classFieldList.add(field);
	    		}
	    	}
			classFields = new Field[classFieldList.size()];
			fieldHashMap.put(clazz, classFieldList.toArray(classFields));
		}
		
		for (Field field : classFields) {
			try {
				 Object fieldValue = field.get(object);
                 builder.append(fieldValue);
            } catch (IllegalAccessException e) {
                throw new InternalError("Unexpected IllegalAccessException");
            }
    	}
	}
}
