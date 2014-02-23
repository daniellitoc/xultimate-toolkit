package org.danielli.xultimate.context.dfs.fastdfs.support;

import org.csource.fastdfs.ClientGlobal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * 客户端配置初始化器。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class ClientGlobalInitializer implements InitializingBean {

	private Resource configResource;

	/**
	 * 设置客户端配置资源。
	 * 
	 * @param configResource 客户端配置资源。
	 */
	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

	/**
	 * 初始化配置信息。
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		ClientGlobal.init(configResource.getFile().getAbsolutePath());
	}

}
