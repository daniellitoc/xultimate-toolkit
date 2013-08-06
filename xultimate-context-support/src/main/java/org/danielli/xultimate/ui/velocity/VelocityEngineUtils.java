package org.danielli.xultimate.ui.velocity;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.VelocityException;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * Utility class for working with a VelocityEngine.
 * Provides convenience methods to merge a Velocity template with a model.
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class VelocityEngineUtils {
	
	/**
	 * Merge the specified Velocity template with the given model into a String.
	 * <p>When using this method to prepare a text for a mail to be sent with Spring's
	 * mail support, consider wrapping VelocityException in MailPreparationException.
	 * @param model the Map that contains model names as keys and model objects as values
	 * @return the result as String
	 * @throws VelocityException if the template wasn't found or rendering failed
	 * @see org.springframework.mail.MailPreparationException
	 */
	public static String processTemplateIntoString(Template template, Map<String, ? extends Object> model) throws VelocityException {
		StringWriter writer = new StringWriter();
		StringBuffer result;
		VelocityContext velocityContext = new VelocityContext(model);
		try {
			template.merge(velocityContext, writer);
			result = writer.getBuffer();
			return result.toString();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
}
