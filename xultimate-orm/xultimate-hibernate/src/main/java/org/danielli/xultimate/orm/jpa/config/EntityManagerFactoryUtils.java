package org.danielli.xultimate.orm.jpa.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;

/**
 * EntityManagerFactory工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class EntityManagerFactoryUtils {

	/**
	 * 获取SessionFactory实例。
	 * 
	 * @param entityManagerFactory entityManagerFactory实例。
	 * @return SessionFactory实例。
	 */
	public static SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
		return ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
	}
}
