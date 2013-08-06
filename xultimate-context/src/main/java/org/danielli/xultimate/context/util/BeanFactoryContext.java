package org.danielli.xultimate.context.util;

import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Spring中{@link ApplicationContext}和{@link BeanFactory}上下文。
 * 
 * @author Daniel Li
 * @see ApplicationContextAware
 * @see ApplicationContext
 */
@Service("beanFactoryContext")
@Lazy(false)
public class BeanFactoryContext implements BeanFactoryAware, ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	
	private static BeanFactory beanFactory = null;
	
	public static ApplicationContext currentApplicationContext() {
		return BeanFactoryContext.applicationContext;
	}
	
	public static BeanFactory currentBeanFactory() {
		return BeanFactoryContext.beanFactory;
	}
	
	/**
	 * 当关闭应用上下文时，执行此方法。
	 */
	@PreDestroy
	public void destroy() {
		applicationContext = null;
		beanFactory = null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanFactoryContext.applicationContext = applicationContext;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		BeanFactoryContext.beanFactory = beanFactory;
	}

}
