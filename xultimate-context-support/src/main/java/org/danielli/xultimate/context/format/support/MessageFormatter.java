package org.danielli.xultimate.context.format.support;

import java.text.MessageFormat;

import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;

/**
 * 格式化器。是{@link MessageFormat}的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 * @see MessageFormat
 */
public class MessageFormatter implements Formatter<String, Object[], String> {

	@Override
	public String format(String source, Object[] parameter) throws FormatException {
		try {
			return MessageFormat.format(source, parameter);
		} catch (Exception e) {
			throw new FormatException(e.getMessage(), e);
		}
	}
}
