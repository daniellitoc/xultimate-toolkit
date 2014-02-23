package org.danielli.xultimate.orm.jpa.config.support;

import org.danielli.xultimate.orm.jpa.config.ProxyInitializer;
import org.hibernate.Hibernate;

/**
 * 代理初始化器，Hibernate实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class HibernateProxyInitializer implements ProxyInitializer {

	@Override
	public void initialize(Object proxy) {
		Hibernate.initialize(proxy);
	}

}
