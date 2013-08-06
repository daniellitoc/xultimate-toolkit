package org.danielli.xultimate.ui.stringtemplate.v3;

import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;

/**
 * StringTemplate V3模板处理工具。
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class StringTemplateUtils {
	
	/**
	 * 处理模板为字符串。
	 * 
	 * @param template 模板。
	 * @param model 数据模型。
	 * @return 字符串。
	 */
	public static String processTemplateIntoString(StringTemplate template, Map<String, ? extends Object> model) {
		template.setAttributes(model);
		return template.toString();
	}
}
