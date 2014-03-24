package org.danielli.xultimate.jdbc.datasource.lookup;

import java.lang.reflect.Method;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;


public class RoutingProxyDataSourceFactoryBean implements FactoryBean<DataSource>, InvocationHandler, BeanClassLoaderAware {
	
	private Map<String, DataSource> targetDataSources;
	
	private ClassLoader classLoader;

	public Map<String, DataSource> getTargetDataSources() {
		return targetDataSources;
	}

	public void setTargetDataSources(Map<String, DataSource> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(targetDataSources.get(RoutingDataSourceUtils.getRoutingDataSourceKey()), args);
	}

	@Override
	public DataSource getObject() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(classLoader);
		enhancer.setUseCache(true);
		enhancer.setInterfaces(new Class<?>[] { DataSource.class });
		enhancer.setInterceptDuringConstruction(true);
		enhancer.setCallback(this);
		return (DataSource) enhancer.create();
	}

	@Override
	public Class<?> getObjectType() {
		return DataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}




	
	
	
}
