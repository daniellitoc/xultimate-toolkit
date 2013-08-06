package org.danielli.xultimate.orm.jpa.config.support;

import org.danielli.xultimate.orm.jpa.config.ProxyInitializer;
import org.hibernate.Hibernate;

public class HibernateProxyInitializer implements ProxyInitializer {

	@Override
	public void initialize(Object proxy) {
		Hibernate.initialize(proxy);
	}

}
