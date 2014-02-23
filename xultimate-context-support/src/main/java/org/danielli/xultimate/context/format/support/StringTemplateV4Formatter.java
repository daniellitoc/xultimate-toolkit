package org.danielli.xultimate.context.format.support;

import java.util.Map;

import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.ui.stringtemplate.v4.StringTemplateUtils;
import org.stringtemplate.v4.ST;

/**
 * 格式化器。是StringTemplate 4.x版本的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 */
public class StringTemplateV4Formatter implements Formatter<String, Map<String, ? extends Object>, String> {

	@Override
	public String format(String source, Map<String, ? extends Object> parameter) throws FormatException {
		ST template = new ST(source);
		return StringTemplateUtils.processTemplateIntoString(template, parameter);
	}

}
