package org.danielli.xultimate.ui.stringtemplate.v4;

import java.util.Map;

import org.stringtemplate.v4.ST;

/**
 * StringTemplate V4模板处理工具。
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
	public static String processTemplateIntoString(ST template, Map<String, ? extends Object> model) {
		for (Map.Entry<String, ? extends Object> entry : model.entrySet()) {
			template.add(entry.getKey(), entry.getValue());
		}
		return template.render();
	}
}
