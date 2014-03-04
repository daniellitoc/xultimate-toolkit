package org.danielli.xultimate.orm.jpa.config;

import org.hibernate.Session;

/**
 * Session回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public interface SessionCallback {

	/**
	 * 回调实现。
	 * 
	 * @param session 会话。
	 */
	void doInSession(Session session);
}
