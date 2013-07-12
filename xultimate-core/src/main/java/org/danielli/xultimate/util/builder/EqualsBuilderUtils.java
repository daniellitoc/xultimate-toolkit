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
	private static ConcurrentHashMap<Class<?>, Field[]> fieldHashMap = new ConcurrentHashMap<>();
	
	/**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     * 
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @return <code>true</code> if the two Objects have tested equals.
     */
	public static boolean reflectionEqualsByInstance(Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        Class<?> lhsClass = lhs.getClass();
        Class<?> rhsClass = rhs.getClass();
        Class<?> testClass;
        if (lhsClass.isInstance(rhs)) {
            testClass = lhsClass;
            if (!rhsClass.isInstance(lhs)) {
                // rhsClass is a subclass of lhsClass
                testClass = rhsClass;
            }
        } else if (rhsClass.isInstance(lhs)) {
            testClass = rhsClass;
            if (!lhsClass.isInstance(rhs)) {
                // lhsClass is a subclass of rhsClass
                testClass = lhsClass;
            }
        } else {
            return false;
        }
        return reflectionEquals(lhs, rhs, testClass);
	}
	
	/**
     * <p>This method uses reflection to determine if the two <code>Object</code>s
     * are equal.</p>
     * 
     * @param lhs  <code>this</code> object
     * @param rhs  the other object
     * @return <code>true</code> if the two Objects have tested equals.
     */
	public static boolean reflectionEqualsByClass(Object lhs, Object rhs) {
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
		Field[] classFields = fieldHashMap.get(clazz);
		if (classFields == null) {
			List<Field> classFieldList = new ArrayList<>();
			Field[] declaredFields = clazz.getDeclaredFields();
			AccessibleObject.setAccessible(declaredFields, true);
			boolean classHas = ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(AnnotationUtils.getAnnotation(clazz, Buildable.class)), BuildType.EQUALS);
			for (Field field : declaredFields) {
	    		Buildable fieldBuildable = AnnotationUtils.getAnnotation(field, Buildable.class);
	    		if (ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(fieldBuildable), BuildType.EQUALS) 
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
