package org.danielli.xultimate.context.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.danielli.xultimate.util.ArrayUtils;
import org.springframework.beans.BeansException;
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
		String[] beanNames = applicationContext.getBeanNamesForType(requiredType);
		if (ArrayUtils.isNotEmpty(beanNames)) {
			return  requiredType.cast(applicationContext.getBean(beanNames[0]));
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(ApplicationContext applicationContext, String name) throws BeansException {
		if (applicationContext.containsBean(name)) 
			return (T) applicationContext.getBean(name);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(ApplicationContext applicationContext, String name, Object... args) throws BeansException {
		if (applicationContext.containsBean(name)) 
			return (T) applicationContext.getBean(name, args);
		return null;
	}
	
	public static String[] getBeanNamesForType(ApplicationContext applicationContext, Class<?> requiredType) throws BeansException {
		return applicationContext.getBeanNamesForType(requiredType);
	}
	
	public static <T> List<T> getBeansOfType(ApplicationContext applicationContext, Class<T> requiredType) throws BeansException {
		return new ArrayList<>(applicationContext.getBeansOfType(requiredType).values());
	}
	
	public static List<Object> getBeansWithAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) throws BeansException {
		return new ArrayList<>(applicationContext.getBeansWithAnnotation(annotationType).values());
	}
	
	public static String getMessage(ApplicationContext applicationContext, String defaultMessage, Locale locale, String code, Object... args) {
		return applicationContext.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(ApplicationContext applicationContext, Locale locale, String code, Object... args) {
		return applicationContext.getMessage(code, args, null, locale);
	}
	
//	public static <T> T createBean(ApplicationContext applicationContext, Class<T> beanClass) {
//		return applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
//	}

}