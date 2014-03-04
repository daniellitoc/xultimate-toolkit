package org.danielli.xultimate.orm.jpa.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;

/**
 * SessionFactory工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class SessionFactoryUtils {

	/**
	 * 获取SessionFactory实例。
	 * 
	 * @param entityManagerFactory entityManagerFactory实例。
	 * @return SessionFactory实例。
	 */
	public static SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
		return ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
	}
	
	/**
	 * 使用拦截器执行会话回调。
	 * 
	 * @param sessionFactory 会话工厂。
	 * @param interceptor 拦截器。
	 * @param sessionCallback 回话回调。
	 */
	public static void invokeSession(SessionFactory sessionFactory, Interceptor interceptor, SessionCallback sessionCallback) {
		Session session = null;
		try {
			session = sessionFactory.withOptions().interceptor(interceptor).openSession();
			sessionCallback.doInSession(session);
		} finally {
			closeSession(session);
		}
	}
	
	/**
	 * 关闭会话。
	 * 
	 * @param session 会话。
	 */
	private static void closeSession(Session session) {
		if (session != null && session.isConnected()) {
			session.close();
		}
	}
	
	/**
	 * 执行会话回调。
	 * 
	 * @param sessionFactory 会话工厂。
	 * @param sessionCallback 回话回调。
	 */
	public static void invokeSession(SessionFactory sessionFactory, SessionCallback sessionCallback) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			sessionCallback.doInSession(session);
		} finally {
			closeSession(session);
		}
	}
}
