package org.danielli.xultimate.orm.jpa.config;

/**
 * 代理初始化器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ProxyInitializer {
	
	void initialize(Object proxy);

}
