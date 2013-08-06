package org.danielli.xultimate.web.util;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.danielli.xultimate.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

public class ResourceVersionExposer implements ServletContextAware {

	private ServletContext servletContext;
	
	private String resourceVersion;
	
	public static final String RESOURCE_VERSION_ATTRIBUTE_NAME = "resourceVersion";
	
	@PostConstruct
	public void init() {
		if (StringUtils.isNotBlank(resourceVersion)) {
			servletContext.setAttribute(RESOURCE_VERSION_ATTRIBUTE_NAME, resourceVersion);
		}
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

}
