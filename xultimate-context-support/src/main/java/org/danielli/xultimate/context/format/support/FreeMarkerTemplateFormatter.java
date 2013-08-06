package org.danielli.xultimate.context.format.support;

import java.io.StringReader;
import java.util.Map;

import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.ui.freemarker.FreeMarkerTemplateUtils;
import org.danielli.xultimate.util.io.IOUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 格式化器。是FreeMarker的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 * @see Configuration
 * @see Template
 */
public class FreeMarkerTemplateFormatter implements Formatter<String, Map<String, ? extends Object>, String>  {
	
	/** FreeMarker配置器 */
	private Configuration configuration;
	/** 模板名称 */
	private static final String TMP_TEMPLATE_NAME = "tmp_template";
	
	@Override
	public String format(String source, Map<String, ? extends Object> parameter) throws FormatException {
		StringReader reader = new StringReader(source);
		try {
			Template template = new Template(TMP_TEMPLATE_NAME, reader, configuration);
	    	return FreeMarkerTemplateUtils.processTemplateIntoString(template, parameter);
		} catch (Exception e) {
			throw new FormatException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * 设置FreeMarker配置器
	 * 
	 * @param configuration FreeMarker配置器
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
