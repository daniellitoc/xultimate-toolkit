package org.danielli.xultimate.context.util;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring中{@link ApplicationContext}工具类。
 * 
 * @author Daniel Li
 * @see ApplicationContextAware
 * @see ApplicationContext
 */
public class ApplicationContextUtils {

	public static <T> T getBean(ApplicationContext applicationContext, Class<T> requiredType) throws BeansException {
		try {
			return applicationContext.getBean(requiredType);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}
	
	public static <T> T getBean(ApplicationContext applicationContext, String name, Class<T> requiredType) throws BeansException {
		try {
			return applicationContext.getBean(name, requiredType);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(ApplicationContext applicationContext, Class<T> requiredType, String name, Object... args) throws BeansException {
		try {
			return (T) applicationContext.getBean(name, args);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}
	
	public static String[] getBeanNamesForType(ApplicationContext applicationContext, Class<?> requiredType) throws BeansException {
		return applicationContext.getBeanNamesForType(requiredType);
	}
	
	public static <T> Collection<T> getBeansOfType(ApplicationContext applicationContext, Class<T> requiredType) throws BeansException {
		return applicationContext.getBeansOfType(requiredType).values();
	}
	
	public static Collection<Object> getBeansWithAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) throws BeansException {
		return applicationContext.getBeansWithAnnotation(annotationType).values();
	}
	
	public static Class<?> getType(ApplicationContext applicationContext, String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}
	
	public static String getMessage(ApplicationContext applicationContext, String defaultMessage, Locale locale, String code, Object... args) {
		return applicationContext.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(ApplicationContext applicationContext, Locale locale, String code, Object... args) {
		return applicationContext.getMessage(code, args, null, locale);
	}
}