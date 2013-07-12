package org.danielli.xultimate.util.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.AnnotationUtils;


/** 
 * Assists in implementing {@link java.lang.Comparable#compareTo(Object)} methods.
 *
 * @author Daniel Li
 * @since 16 Jun 20
 * @see CompareToBuilder
 */
public class CompareToBuilderUtils {
	
	/**
	 * 本地缓存。
	 */
	private static ConcurrentHashMap<Class<?>, Field[]> fieldHashMap = new ConcurrentHashMap<>();
	
	/** 
     * <p>Compares two <code>Object</code>s via reflection.</p>
     * 
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either (but not both) parameters are
     *  <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */	
	public static int reflectionCompareByClass(Object lhs, Object rhs) {
		if (lhs == rhs) {
            return 0;
        }
        if (lhs == null || rhs == null) {
            throw new NullPointerException();
        }
        if (lhs.getClass() != rhs.getClass()) {
            throw new ClassCastException();
        }
       return reflectionCompare(lhs, rhs, lhs.getClass());
    }

	/** 
     * <p>Compares two <code>Object</code>s via reflection.</p>
     * 
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either (but not both) parameters are
     *  <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
	public static int reflectionCompareByInstance(Object lhs, Object rhs) {
		if (lhs == rhs) {
            return 0;
        }
        if (lhs == null || rhs == null) {
            throw new NullPointerException();
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
        	throw new ClassCastException();
        }
        return reflectionCompare(lhs, rhs, testClass);
    }
	
    /** 
     * <p>Compares two <code>Object</code>s via reflection.</p>
     * 
     * @param lhs  left-hand object
     * @param rhs  right-hand object
     * @param testClazz  the class to append details of
     * @return a negative integer, zero, or a positive integer as <code>lhs</code>
     *  is less than, equal to, or greater than <code>rhs</code>
     * @throws NullPointerException  if either (but not both) parameters are
     *  <code>null</code>
     * @throws ClassCastException  if <code>rhs</code> is not assignment-compatible
     *  with <code>lhs</code>
     */
	public static int reflectionCompare(Object lhs, Object rhs, Class<?> testClazz) {
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        reflectionAppend(lhs, rhs, testClazz, compareToBuilder);
        while (testClazz.getSuperclass() != null) {
        	testClazz = testClazz.getSuperclass();
            reflectionAppend(lhs, rhs, testClazz, compareToBuilder);
        }
        return compareToBuilder.toComparison();
    }
	 
	 private static void reflectionAppend(Object lhs, Object rhs, Class<?> clazz, CompareToBuilder builder) {
		 	Field[] classFields = fieldHashMap.get(clazz);
			if (classFields == null) {
				List<Field> classFieldList = new ArrayList<>();
				Field[] declaredFields = clazz.getDeclaredFields();
				AccessibleObject.setAccessible(declaredFields, true);
				boolean classHas = ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(AnnotationUtils.getAnnotation(clazz, Buildable.class)), BuildType.COMPARE_TO);
				for (Field field : declaredFields) {
		    		Buildable fieldBuildable = AnnotationUtils.getAnnotation(field, Buildable.class);
		    		if (ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(fieldBuildable), BuildType.COMPARE_TO) 
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
		 
			for (int i = 0; i < classFields.length && builder.toComparison() == 0; i++) {
	    		Field field = classFields[i];
    			try {
                    builder.append(field.get(lhs), field.get(rhs));
                } catch (IllegalAccessException e) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
	    	}
		}

}
