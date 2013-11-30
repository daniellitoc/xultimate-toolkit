package org.danielli.xultimate.context.dfs.fastdfs.support;

import org.csource.fastdfs.ClientGlobal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class ClientGlobalInitializer implements InitializingBean {

	private Resource configResource;

	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ClientGlobal.init(configResource.getFile().getAbsolutePath());
	}

}
