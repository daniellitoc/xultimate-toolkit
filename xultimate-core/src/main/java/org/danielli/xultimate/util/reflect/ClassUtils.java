package org.danielli.xultimate.util.reflect;

import java.util.List;

import org.springframework.util.TypeUtils;

/**
 * <p>Operates on classes without using reflection.</p>
 *
 * <p>This class handles invalid {@code null} inputs as best it can.
 * Each method documents its behaviour in more detail.</p>
 *
 * <p>The notion of a {@code canonical name} includes the human
 * readable name for the type, for example {@code int[]}. The
 * non-canonical method variants work with the JVM names, such as
 * {@code [I}. </p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.ClassUtils
 */
public class ClassUtils {
	
	/**
	 * Replacement for {@code Class.forName()} that also returns Class instances
	 * for primitives (e.g."int") and array class names (e.g. "String[]").
	 * Furthermore, it is also capable of resolving inner class names in Java source
	 * style (e.g. "java.lang.Thread.State" instead of "java.lang.Thread$State").
	 * @param name the name of the Class
	 * @param classLoader the class loader to use
	 * (may be {@code null}, which indicates the default class loader)
	 * @return Class instance for the supplied name
	 * @throws ClassNotFoundException if the class was not found
	 * @throws LinkageError if the class file could not be loaded
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		return org.springframework.util.ClassUtils.forName(name, classLoader);
	}
	
	/**
	 * Return all interfaces that the given instance implements as array,
	 * including ones implemented by superclasses.
	 * @param instance the instance to analyze for interfaces
	 * @return all interfaces that the given instance implements as array
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getAllInterfaces(Object instance) {
		return org.springframework.util.ClassUtils.getAllInterfaces(instance);
	}
	
	/**
	 * Return all interfaces that the given class implements as array,
	 * including ones implemented by superclasses.
	 * <p>If the class itself is an interface, it gets returned as sole interface.
	 * @param clazz the class to analyze for interfaces
	 * @return all interfaces that the given object implements as array
	 */
	public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
		return org.springframework.util.ClassUtils.getAllInterfacesForClass(clazz);
	}
	
	/**
	 * Return all interfaces that the given class implements as array,
	 * including ones implemented by superclasses.
	 * <p>If the class itself is an interface, it gets returned as sole interface.
	 * @param clazz the class to analyze for interfaces
	 * @param classLoader the ClassLoader that the interfaces need to be visible in
	 * (may be {@code null} when accepting all declared interfaces)
	 * @return all interfaces that the given object implements as array
	 */
	public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
		return org.springframework.util.ClassUtils.getAllInterfacesForClass(clazz, classLoader);
	}
	
	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>Call this method if you intend to use the thread context ClassLoader
	 * in a scenario where you absolutely need a non-null ClassLoader reference:
	 * for example, for class path resource loading (but not necessarily for
	 * {@code Class.forName}, which accepts a {@code null} ClassLoader
	 * reference as well).
	 * @return the default ClassLoader (never {@code null})
	 * @see Thread#getContextClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		return org.springframework.util.ClassUtils.getDefaultClassLoader();
	}
	
	/**
	 * Override the thread context ClassLoader with the environment's bean ClassLoader
	 * if necessary, i.e. if the bean ClassLoader is not equivalent to the thread
	 * context ClassLoader already.
	 * @param classLoaderToUse the actual ClassLoader to use for the thread context
	 * @return the original thread context ClassLoader, or {@code null} if not overridden
	 */
	public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
		return org.springframework.util.ClassUtils.overrideThreadContextClassLoader(classLoaderToUse);
	}
	
	/**
	 * Check if the right-hand side type may be assigned to the left-hand side
	 * type, assuming setting by reflection. Considers primitive wrapper
	 * classes as assignable to the corresponding primitive types.
	 * @param lhsType the target type
	 * @param rhsType the value type that should be assigned to the target type
	 * @return if the target type is assignable from the value type
	 * @see TypeUtils#isAssignable
	 */
	public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
		return org.springframework.util.ClassUtils.isAssignable(lhsType, rhsType);
	}
	
	/**
	 * Determine if the given type is assignable from the given value,
	 * assuming setting by reflection. Considers primitive wrapper classes
	 * as assignable to the corresponding primitive types.
	 * @param type the target type
	 * @param value the value that should be assigned to the type
	 * @return if the type is assignable from the value
	 */
	public static boolean isAssignableValue(Class<?> type, Object value) {
		return org.springframework.util.ClassUtils.isAssignableValue(type, value);
	}
	
	/**
	 * Check if the given class represents an array of primitives,
	 * i.e. boolean, byte, char, short, int, long, float, or double.
	 * @param clazz the class to check
	 * @return whether the given class is a primitive array class
	 */
	public static boolean isPrimitiveArray(Class<?> clazz) {
		return org.springframework.util.ClassUtils.isPrimitiveArray(clazz);
	}
	
	/**
	 * Check if the given class represents a primitive wrapper,
	 * i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 * @param clazz the class to check
	 * @return whether the given class is a primitive wrapper class
	 */
	public static boolean isPrimitiveWrapper(Class<?> clazz) {
		return org.springframework.util.ClassUtils.isPrimitiveWrapper(clazz);
	}
	
	/**
	 * Check if the given class represents an array of primitive wrappers,
	 * i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 * @param clazz the class to check
	 * @return whether the given class is a primitive wrapper array class
	 */
	public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
		return org.springframework.util.ClassUtils.isPrimitiveWrapperArray(clazz);
	}
	
	/**
	 * Check if the given class represents a primitive (i.e. boolean, byte,
	 * char, short, int, long, float, or double) or a primitive wrapper
	 * (i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double).
	 * @param clazz the class to check
	 * @return whether the given class is a primitive or primitive wrapper class
	 */
	public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
		return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz);
	}
	
	/**
     * <p>Gets a {@code List} of superclasses for the given class.</p>
     *
     * @param cls  the class to look up, may be {@code null}
     * @return the {@code List} of superclasses in order going up from this one
     *  {@code null} if null input
     */
    public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
    	return org.apache.commons.lang3.ClassUtils.getAllSuperclasses(cls);
    }
}
