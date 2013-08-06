package org.danielli.xultimate.web.util;

import javax.servlet.ServletRequest;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * Parameter extraction methods, for an approach distinct from data binding,
 * in which parameters of specific types are required.
 *
 * <p>This approach is very useful for simple submissions, where binding
 * request parameters to a command object would be overkill.
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class ServletRequestUtils {
	/**
	 * Get an Integer parameter, or {@code null} if not present.
	 * Throws an exception if it the parameter value isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the Integer value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static Integer getIntParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {
		return org.springframework.web.bind.ServletRequestUtils.getIntParameter(request, name);
	}

	/**
	 * Get an int parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static int getIntParameter(ServletRequest request, String name, int defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getIntParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of int parameters, return an empty array if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static int[] getIntParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getIntParameters(request, name);
	}

	/**
	 * Get an int parameter, throwing an exception if it isn't found or isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static int getRequiredIntParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredIntParameter(request, name);
	}

	/**
	 * Get an array of int parameters, throwing an exception if not found or one is not a number..
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static int[] getRequiredIntParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredIntParameters(request, name);
	}


	/**
	 * Get a Long parameter, or {@code null} if not present.
	 * Throws an exception if it the parameter value isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the Long value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static Long getLongParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getLongParameter(request, name);
	}

	/**
	 * Get a long parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static long getLongParameter(ServletRequest request, String name, long defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getLongParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of long parameters, return an empty array if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static long[] getLongParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getLongParameters(request, name);
	}

	/**
	 * Get a long parameter, throwing an exception if it isn't found or isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static long getRequiredLongParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredLongParameter(request, name);
	}

	/**
	 * Get an array of long parameters, throwing an exception if not found or one is not a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static long[] getRequiredLongParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredLongParameters(request, name);
	}


	/**
	 * Get a Float parameter, or {@code null} if not present.
	 * Throws an exception if it the parameter value isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the Float value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static Float getFloatParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {
		return org.springframework.web.bind.ServletRequestUtils.getFloatParameter(request, name);
	}

	/**
	 * Get a float parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static float getFloatParameter(ServletRequest request, String name, float defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getFloatParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of float parameters, return an empty array if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static float[] getFloatParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getFloatParameters(request, name);
	}

	/**
	 * Get a float parameter, throwing an exception if it isn't found or isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static float getRequiredFloatParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredFloatParameter(request, name);
	}

	/**
	 * Get an array of float parameters, throwing an exception if not found or one is not a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static float[] getRequiredFloatParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredFloatParameters(request, name);
	}


	/**
	 * Get a Double parameter, or {@code null} if not present.
	 * Throws an exception if it the parameter value isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the Double value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static Double getDoubleParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getDoubleParameter(request, name);
	}

	/**
	 * Get a double parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static double getDoubleParameter(ServletRequest request, String name, double defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getDoubleParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of double parameters, return an empty array if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static double[] getDoubleParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getDoubleParameters(request, name);
	}

	/**
	 * Get a double parameter, throwing an exception if it isn't found or isn't a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static double getRequiredDoubleParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredDoubleParameter(request, name);
	}

	/**
	 * Get an array of double parameters, throwing an exception if not found or one is not a number.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static double[] getRequiredDoubleParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredDoubleParameters(request, name);
	}


	/**
	 * Get a Boolean parameter, or {@code null} if not present.
	 * Throws an exception if it the parameter value isn't a boolean.
	 * <p>Accepts "true", "on", "yes" (any case) and "1" as values for true;
	 * treats every other non-empty value as false (i.e. parses leniently).
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the Boolean value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static Boolean getBooleanParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getBooleanParameter(request, name);
	}

	/**
	 * Get a boolean parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value as default to enable checks of whether it was supplied.
	 * <p>Accepts "true", "on", "yes" (any case) and "1" as values for true;
	 * treats every other non-empty value as false (i.e. parses leniently).
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static boolean getBooleanParameter(ServletRequest request, String name, boolean defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getBooleanParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of boolean parameters, return an empty array if not found.
	 * <p>Accepts "true", "on", "yes" (any case) and "1" as values for true;
	 * treats every other non-empty value as false (i.e. parses leniently).
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static boolean[] getBooleanParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getBooleanParameters(request, name);
	}

	/**
	 * Get a boolean parameter, throwing an exception if it isn't found
	 * or isn't a boolean.
	 * <p>Accepts "true", "on", "yes" (any case) and "1" as values for true;
	 * treats every other non-empty value as false (i.e. parses leniently).
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static boolean getRequiredBooleanParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredBooleanParameter(request, name);
	}

	/**
	 * Get an array of boolean parameters, throwing an exception if not found
	 * or one isn't a boolean.
	 * <p>Accepts "true", "on", "yes" (any case) and "1" as values for true;
	 * treats every other non-empty value as false (i.e. parses leniently).
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static boolean[] getRequiredBooleanParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredBooleanParameters(request, name);
	}


	/**
	 * Get a String parameter, or {@code null} if not present.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @return the String value, or {@code null} if not present
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static String getStringParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getStringParameter(request, name);
	}

	/**
	 * Get a String parameter, with a fallback value. Never throws an exception.
	 * Can pass a distinguished value to default to enable checks of whether it was supplied.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @param defaultVal the default value to use as fallback
	 */
	public static String getStringParameter(ServletRequest request, String name, String defaultVal) {
		return org.springframework.web.bind.ServletRequestUtils.getStringParameter(request, name, defaultVal);
	}

	/**
	 * Get an array of String parameters, return an empty array if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter with multiple possible values
	 */
	public static String[] getStringParameters(ServletRequest request, String name) {
		return org.springframework.web.bind.ServletRequestUtils.getStringParameters(request, name);
	}

	/**
	 * Get a String parameter, throwing an exception if it isn't found.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static String getRequiredStringParameter(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredStringParameter(request, name);
	}

	/**
	 * Get an array of String parameters, throwing an exception if not found.
	 * @param request current HTTP request
	 * @param name the name of the parameter
	 * @throws ServletRequestBindingException a subclass of ServletException,
	 * so it doesn't need to be caught
	 */
	public static String[] getRequiredStringParameters(ServletRequest request, String name)
			throws ServletRequestBindingException {

		return org.springframework.web.bind.ServletRequestUtils.getRequiredStringParameters(request, name);
	}
}
