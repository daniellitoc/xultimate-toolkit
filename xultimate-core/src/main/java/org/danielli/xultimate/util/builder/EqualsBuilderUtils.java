package org.danielli.xultimate.util.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.AnnotationUtils;

/**
 * <p>Assists in implementing {@link Object#equals(Object)} methods.</p>
 *
 * @author Daniel Li
 * @since 16 Jun 20
 * @see EqualsBuilder
 */
public class EqualsBuilderUtils {
	
	/**
	 * 本地缓存。
	 */
	private static ConcurrentHashMap<Class<?>, Field[]> fieldCache = new ConcurrentHashMap<>();
	
	/**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     * 
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @return <code>true</code> if the two Objects have tested equals.
     */
	public static boolean reflectionEqualsForLeftClass(Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        Class<?> lhsClazz = lhs.getClass();
        if (!lhsClazz.isInstance(rhs)) {
            return false;
        }
        return reflectionEquals(lhs, rhs, lhsClazz);
	}
	
	/**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     * 
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @return <code>true</code> if the two Objects have tested equals.
     */
	public static boolean reflectionEqualsForBothClass(Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        if (lhs.getClass() != rhs.getClass()) {
        	return false;
        }
        Class<?> testClass = lhs.getClass();
        return reflectionEquals(lhs, rhs, testClass);
	}
	
	/**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     * 
     * @param testClazz  the class to append details of
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @return <code>true</code> if the two Objects have tested equals.
     */
	public static boolean reflectionEquals(Object lhs, Object rhs, Class<?> testClass) {
        EqualsBuilder builder = new EqualsBuilder();
        try {
        	reflectionAppend(lhs, rhs, testClass, builder);
        	while (testClass.getSuperclass() != null) {
        		testClass = testClass.getSuperclass();
        		reflectionAppend(lhs, rhs, testClass, builder);
        	}
        } catch (Exception e) {
        	return false;
        }
		return builder.isEquals();
	}
	
	private static void reflectionAppend(Object lhs, Object rhs, Class<?> clazz, EqualsBuilder builder) {
		Field[] classFields = fieldCache.get(clazz);
		if (classFields == null) {
			List<Field> classFieldList = new ArrayList<>();
			Field[] declaredFields = clazz.getDeclaredFields();
			AccessibleObject.setAccessible(declaredFields, true);
			Buildable classBuildable = AnnotationUtils.getAnnotation(clazz, Buildable.class);
			boolean classHas = ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(classBuildable), BuildType.EQUALS);
			for (Field field : declaredFields) {
	    		Buildable fieldBuildable = AnnotationUtils.getAnnotation(field, Buildable.class);
	    		if ((classBuildable == null && fieldBuildable == null || classHas && fieldBuildable == null || ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(fieldBuildable), BuildType.EQUALS)) 
	    				&& (field.getName().indexOf('$') == -1) 
	    				&& (!Modifier.isTransient(field.getModifiers())) 
	    				&& (!Modifier.isStatic(field.getModifiers()))
	    				) {
	    			classFieldList.add(field);
	    		}
	    	}
			classFields = new Field[classFieldList.size()];
			fieldCache.put(clazz, classFieldList.toArray(classFields));
		}
		
		for (int i = 0; i < classFields.length && builder.isEquals(); i++) {
    		Field field = classFields[i];
			try {
                builder.append(field.get(lhs), field.get(rhs));
            } catch (IllegalAccessException e) {
                throw new InternalError("Unexpected IllegalAccessException");
            }
    	}
	}
}
