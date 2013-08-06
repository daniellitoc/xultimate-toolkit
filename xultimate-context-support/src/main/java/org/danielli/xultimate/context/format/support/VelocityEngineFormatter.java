package org.danielli.xultimate.context.format.support;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.danielli.xultimate.context.format.FormatException;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * 格式化器。是Velocity的实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Formatter
 * @see Velocity
 * @see VelocityContext
 */
public class VelocityEngineFormatter implements Formatter<String, Map<String, ? extends Object>, String> {

	@Override
	public String format(String source, Map<String, ? extends Object> parameter) throws FormatException {
		StringWriter writer = new StringWriter();
		StringBuffer result;
		VelocityContext velocityContext = new VelocityContext(parameter);
		try {
			Velocity.evaluate(velocityContext, writer, "TMP_TEMPLATE", source);
			result = writer.getBuffer();
			return result.toString();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

}
