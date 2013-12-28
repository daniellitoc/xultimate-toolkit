package org.danielli.xultimate.ui.httl;

import httl.Template;

import java.text.ParseException;
import java.util.Map;

/**
 * Utility class for working with a HTTL Engine.
 * Provides convenience methods to merge a HTTL template with a model.
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class HTTLEngineUtils {
	
	/**
	 * Merge the specified HTTL template with the given model into a String.
	 * @see org.springframework.mail.MailPreparationException
	 */
	public static String processTemplateIntoString(Template template, Map<String, ? extends Object> model) throws ParseException {
		return (String) template.evaluate(model);
	}
}
