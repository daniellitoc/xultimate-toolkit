package org.danielli.xultimate.jdbc.datasource.lookup;

import java.lang.reflect.Method;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import org.springframework.beans.factory.FactoryBean;


public class RoutingProxyDataSourceFactoryBean implements FactoryBean<DataSource> {
	
	private Map<String, DataSource> targetDataSources;

	public Map<String, DataSource> getTargetDataSources() {
		return targetDataSources;
	}

	public void setTargetDataSources(Map<String, DataSource> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

	class RoutingProxyDataSourceInvocationHandler implements InvocationHandler {
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.invoke(targetDataSources.get(RoutingDataSourceUtils.getRoutingDataSourceKey()), args);
		}
		
	}

	@Override
	public DataSource getObject() throws Exception {
		RoutingProxyDataSourceInvocationHandler invocationHandler = new RoutingProxyDataSourceInvocationHandler();
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(invocationHandler);
		enhancer.setInterfaces(new Class<?>[] { DataSource.class });
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
