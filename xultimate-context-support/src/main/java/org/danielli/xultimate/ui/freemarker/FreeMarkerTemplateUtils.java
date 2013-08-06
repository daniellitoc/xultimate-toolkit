package org.danielli.xultimate.ui.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.danielli.xultimate.util.io.IOUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Utility class for working with FreeMarker.
 * Provides convenience methods to process a FreeMarker template with a model.
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see org.springframework.ui.freemarker.FreeMarkerTemplateUtils
 */
public class FreeMarkerTemplateUtils {
	
	/**
	 * Process the specified FreeMarker template with the given model and write
	 * the result to the given Writer.
	 * <p>When using this method to prepare a text for a mail to be sent with Spring's
	 * mail support, consider wrapping IO/TemplateException in MailPreparationException.
	 * @param model the model object, typically a Map that contains model names
	 * as keys and model objects as values
	 * @return the result as String
	 * @throws IOException if the template wasn't found or couldn't be read
	 * @throws freemarker.template.TemplateException if rendering failed
	 * @see org.springframework.mail.MailPreparationException
	 */
	public static String processTemplateIntoString(Template template, Map<String, ? extends Object> model) throws IOException, TemplateException {
		StringWriter writer = new StringWriter();
		StringBuffer result;
		try {
			template.process(model, writer);
	    	result = writer.getBuffer();
			return result.toString();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
}
