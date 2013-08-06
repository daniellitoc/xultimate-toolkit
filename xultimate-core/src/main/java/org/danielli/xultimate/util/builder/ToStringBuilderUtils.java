package org.danielli.xultimate.util.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.AnnotationUtils;

/**
 * <p>
 * Assists in implementing {@link Object#toString()} methods using reflection.
 * </p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see ToStringBuilder
 */
public class ToStringBuilderUtils {
	
	/**
	 * 本地缓存。
	 */
	private static ConcurrentHashMap<Class<?>, Field[]> fieldCache = new ConcurrentHashMap<>();
	
	/**
     * <p>
     * Builds a <code>toString</code> value using the default <code>ToStringStyle</code> through reflection.
     * </p>
     * 
     * @param object
     *            the Object to be output
     * @return the String result
     * @throws IllegalArgumentException
     *             if the Object is <code>null</code>
     */
	public static String reflectionToString(Object object) {
		ToStringBuilder builder = new ToStringBuilder(object);
		Class<?> clazz = object.getClass();
        reflectionAppend(object, clazz, builder);
        while (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            reflectionAppend(object, clazz, builder);
        }
        return builder.toString();
	}
	
	private static void reflectionAppend(Object object, Class<?> clazz, ToStringBuilder builder) {
		// 先从缓存中取，如果缓存中不存在。计算并加入缓存中。
		Field[] classFields = fieldCache.get(clazz);
		if (classFields == null) {
			List<Field> classFieldList = new ArrayList<>();
			Field[] declaredFields = clazz.getDeclaredFields();
			AccessibleObject.setAccessible(declaredFields, true);
			Buildable classBuildable = AnnotationUtils.getAnnotation(clazz, Buildable.class);
			boolean classHas = ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(classBuildable), BuildType.TO_STRING);
			for (Field field : declaredFields) {
	    		Buildable fieldBuildable = AnnotationUtils.getAnnotation(field, Buildable.class);
	    		if ((classBuildable == null && fieldBuildable == null || classHas && fieldBuildable == null || ArrayUtils.contains((BuildType[]) AnnotationUtils.getValue(fieldBuildable), BuildType.TO_STRING)) 
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
		
		for (Field field : classFields) {
			try {
				builder.append(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                throw new InternalError("Unexpected IllegalAccessException");
            }
    	}
	}
}
