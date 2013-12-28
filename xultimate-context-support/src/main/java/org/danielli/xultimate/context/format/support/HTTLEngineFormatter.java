package org.danielli.xultimate.context.format.support;

import httl.Engine;
import httl.Template;

import java.util.Map;

import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.ui.httl.HTTLEngineUtils;

/**
 * 格式化器。是HTTL的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 * @see Engine
 */
public class HTTLEngineFormatter implements Formatter<String, Map<String, ? extends Object>, String> {

	/** HTTL引擎 */
	private Engine engine;
	
	@Override
	public String format(String source, Map<String, ? extends Object> parameter) throws FormatException {
		try {
			Template template = engine.parseTemplate(source);
	    	return HTTLEngineUtils.processTemplateIntoString(template, parameter);
		} catch (Exception e) {
			throw new FormatException(e.getMessage(), e);
		}
	}
	
	/**
	 * 设置HTTL引擎
	 * @param engine HTTL引擎
	 */
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

}
