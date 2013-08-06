package org.danielli.xultimate.util.reflect;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.danielli.xultimate.util.ArrayUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

/**
 * Static convenience methods for JavaBeans: for instantiating beans,
 * checking bean property types, copying bean properties, etc.
 *
 * <p>Mainly for use within the framework, but to some degree also
 * useful for application classes.
 *
 * @author Daniel Li
 * @since 12 Jun 2013
 * @see org.springframework.beans.BeanUtils
 */
public class BeanUtils {
	
	/**
	 * Copy the property values of the given source bean into the target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}

	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param copyProperties array of property names to copy
	 * @param target the target bean
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, String[] copyProperties, Object target) throws BeansException {

		copyProperties(source, copyProperties, target, null);
	}
	
	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source map
	 * @param copyProperties array of property names to copy
	 * @param target the target bean
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Map<String, Object> source, String[] copyProperties, Object target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && ArrayUtils.contains(copyProperties, targetPd.getName())) {
				Object value = source.get(targetPd.getName());
				if (value != null) {
					try {
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param copyProperties array of property names to copy
	 * @param target the target map
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, String[] copyProperties, Map<String, Object> target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		
		Class<?> actualEditable = source.getClass();
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor sourcePd : sourcePds) {
			if (sourcePd.getReadMethod() != null && ArrayUtils.contains(copyProperties, sourcePd.getName())) {
				try {
					Method readMethod = sourcePd.getReadMethod();
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					target.put(sourcePd.getName(), value);
				}
				catch (Throwable ex) {
					throw new FatalBeanException("Could not copy properties from source to target", ex);
				}
			}
		}
	}
	

	/**
	 * Copy the property values of the given source bean into the given target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * @param source the source bean
	 * @param copyProperties array of property names to copy
	 * @param target the target bean
	 * @param editable the class (or interface) to restrict property setting to
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	private static void copyProperties(Object source, String[] copyProperties, Object target, Class<?> editable)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && ArrayUtils.contains(copyProperties, targetPd.getName())) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, Object target, String... ignoreProperties)
			throws BeansException {

		org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
	}
	
	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source map
	 * @param target the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Map<String, Object> source, Object target, String... ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && !ArrayUtils.contains(ignoreProperties, targetPd.getName())) {
				Object value = source.get(targetPd.getName());
				if (value != null) {
					try {
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target map
	 * @param ignoreProperties array of property names to ignore
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, Map<String, Object> target, String... ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = source.getClass();
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
		
		for (PropertyDescriptor sourcePd : sourcePds) {
			if (sourcePd.getReadMethod() != null && !ArrayUtils.contains(ignoreProperties, sourcePd.getName())) {
				try {
					Method readMethod = sourcePd.getReadMethod();
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					target.put(sourcePd.getName(), value);
				}
				catch (Throwable ex) {
					throw new FatalBeanException("Could not copy properties from source to target", ex);
				}
			}
		}
	}
	
	/**
	 * Find a method with the given method name and the given parameter types,
	 * declared on the given class or one of its superclasses. Will return a public,
	 * protected, package access, or private method.
	 * <p>Checks {@code Class.getDeclaredMethod}, cascading upwards to all superclasses.
	 * @param clazz the class to check
	 * @param methodName the name of the method to find
	 * @param paramTypes the parameter types of the method to find
	 * @return the Method object, or {@code null} if not found
	 * @see Class#getDeclaredMethod
	 */
	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return org.springframework.beans.BeanUtils.findDeclaredMethod(clazz, methodName, paramTypes);
	}
	
	/**
	 * Find a method with the given method name and the given parameter types,
	 * declared on the given class or one of its superclasses. Prefers public methods,
	 * but will return a protected, package access, or private method too.
	 * <p>Checks {@code Class.getMethod} first, falling back to
	 * {@code findDeclaredMethod}. This allows to find public methods
	 * without issues even in environments with restricted Java security settings.
	 * @param clazz the class to check
	 * @param methodName the name of the method to find
	 * @param paramTypes the parameter types of the method to find
	 * @return the Method object, or {@code null} if not found
	 * @see Class#getMethod
	 * @see #findDeclaredMethod
	 */
	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return org.springframework.beans.BeanUtils.findMethod(clazz, methodName, paramTypes);
	}
	
	/**
	 * Return a public static method of a class.
	 * @param methodName the static method name
	 * @param clazz    the class which defines the method
	 * @param paramTypes the parameter types to the method
	 * @return the static method, or {@code null} if no static method was found
	 * @throws IllegalArgumentException if the method name is blank or the clazz is null
	 */
	public static Method findStaticMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return org.springframework.util.ClassUtils.getStaticMethod(clazz, methodName, paramTypes);
	}
	
	/**
	 * Retrieve the JavaBeans {@code PropertyDescriptor}s of a given class.
	 * @param clazz the Class to retrieve the PropertyDescriptors for
	 * @return an array of {@code PropertyDescriptors} for the given class
	 * @throws BeansException if PropertyDescriptor look fails
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
		return org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz);
	}
	
	/**
	 * Retrieve the JavaBeans {@code PropertyDescriptors} for the given property.
	 * @param clazz the Class to retrieve the PropertyDescriptor for
	 * @param propertyName the name of the property
	 * @return the corresponding PropertyDescriptor, or {@code null} if none
	 * @throws BeansException if PropertyDescriptor lookup fails
	 */
	public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) throws BeansException {
		return org.springframework.beans.BeanUtils.getPropertyDescriptor(clazz, propertyName);
	}
	
	/**
	 * Find a JavaBeans {@code PropertyDescriptor} for the given method,
	 * with the method either being the read method or the write method for
	 * that bean property.
	 * @param method the method to find a corresponding PropertyDescriptor for
	 * @return the corresponding PropertyDescriptor, or {@code null} if none
	 * @throws BeansException if PropertyDescriptor lookup fails
	 */
	public static PropertyDescriptor findPropertyForMethod(Method method) throws BeansException {
		return org.springframework.beans.BeanUtils.findPropertyForMethod(method);
	}
	
	/**
	 * Determine the bean property type for the given property from the
	 * given classes/interfaces, if possible.
	 * @param propertyName the name of the bean property
	 * @param beanClasses the classes to check against
	 * @return the property type, or {@code Object.class} as fallback
	 */
	public static Class<?> findPropertyType(String propertyName, Class<?>[] beanClasses) {
		return org.springframework.beans.BeanUtils.findPropertyType(propertyName, beanClasses);
	}
	
	/**
	 * Obtain a new MethodParameter object for the write method of the
	 * specified property.
	 * @param pd the PropertyDescriptor for the property
	 * @return a corresponding MethodParameter object
	 */
	public static MethodParameter getWriteMethodParameter(PropertyDescriptor pd) {
		return org.springframework.beans.BeanUtils.getWriteMethodParameter(pd);
	}
	
	/**
	 * Find a JavaBeans PropertyEditor following the 'Editor' suffix convention
	 * (e.g. "mypackage.MyDomainClass" -> "mypackage.MyDomainClassEditor").
	 * <p>Compatible to the standard JavaBeans convention as implemented by
	 * {@link java.beans.PropertyEditorManager} but isolated from the latter's
	 * registered default editors for primitive types.
	 * @param targetType the type to find an editor for
	 * @return the corresponding editor, or {@code null} if none found
	 */
	public static PropertyEditor findEditorByConvention(Class<?> targetType) {
		return org.springframework.beans.BeanUtils.findEditorByConvention(targetType);
	}
	
	/**
	 * Convenience method to instantiate a class using its no-arg constructor.
	 * As this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * @param clazz class to instantiate
	 * @return the new instance
	 * @throws BeanInstantiationException if the bean cannot be instantiated
	 */
	public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
		return org.springframework.beans.BeanUtils.instantiate(clazz);
	}
	
	/**
	 * Instantiate a class using its no-arg constructor.
	 * As this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * <p>Note that this method tries to set the constructor accessible
	 * if given a non-accessible (that is, non-public) constructor.
	 * @param clazz class to instantiate
	 * @return the new instance
	 * @throws BeanInstantiationException if the bean cannot be instantiated
	 */
	public static <T> T instantiateClass(Class<T> clazz) throws BeanInstantiationException {
		return org.springframework.beans.BeanUtils.instantiateClass(clazz);
	}
	
	/**
	 * Instantiate a class using its no-arg constructor and return the new instance
	 * as the the specified assignable type.
	 * <p>Useful in cases where
	 * the type of the class to instantiate (clazz) is not available, but the type
	 * desired (assignableTo) is known.
	 * <p>As this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * <p>Note that this method tries to set the constructor accessible
	 * if given a non-accessible (that is, non-public) constructor.
	 * @param clazz class to instantiate
	 * @param assignableTo type that clazz must be assignableTo
	 * @return the new instance
	 * @throws BeanInstantiationException if the bean cannot be instantiated
	 */
	public static <T> T instantiateClass(Class<?> clazz, Class<T> assignableTo) throws BeanInstantiationException {
		return org.springframework.beans.BeanUtils.instantiateClass(clazz, assignableTo);
	}
	
	/**
	 * Convenience method to instantiate a class using the given constructor.
	 * As this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * <p>Note that this method tries to set the constructor accessible
	 * if given a non-accessible (that is, non-public) constructor.
	 * @param ctor the constructor to instantiate
	 * @param args the constructor arguments to apply
	 * @return the new instance
	 * @throws BeanInstantiationException if the bean cannot be instantiated
	 */
	public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
		return org.springframework.beans.BeanUtils.instantiateClass(ctor, args);
	}
}
