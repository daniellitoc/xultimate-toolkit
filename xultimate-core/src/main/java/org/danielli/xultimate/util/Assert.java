package org.danielli.xultimate.util;

/**
 * Assertion utility class that assists in validating arguments.
 * Useful for identifying programmer errors early and clearly at runtime.
 *
 * <p>For example, if the contract of a public method states it does not
 * allow {@code null} arguments, Assert can be used to validate that
 * contract. Doing this clearly indicates a contract violation when it
 * occurs and protects the class's invariants.
 *
 * <p>Typically used to validate method arguments rather than configuration
 * properties, to check for cases that are usually programmer errors rather than
 * configuration errors. In contrast to config initialization code, there is
 * usally no point in falling back to defaults in such methods.
 *
 * <p>This class is similar to JUnit's assertion library. If an argument value is
 * deemed invalid, an {@link IllegalArgumentException} is thrown (typically).
 * For example:
 *
 * <pre class="code">
 * Assert.notNull(clazz, "The class must not be null");
 * Assert.isTrue(i > 0, "The value must be greater than zero");</pre>
 *
 * Mainly for internal use within the framework; consider Jakarta's Commons Lang
 * >= 2.0 for a more comprehensive suite of assertion utilities.
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.springframework.util.Assert
 */
public class Assert {
	
	/**
	 * Assert that an object is not {@code null} .
	 * <pre class="code">Assert.notNull(clazz, new NullpointException());</pre>
	 * @param object the object to check
	 * @param exception the exception to use if the assertion fails
	 */
	public static void notNull(Object object, RuntimeException exception) {
		if (object == null) {
			throw exception;
		}
	}
	
	/**
	 * Assert that an object is not {@code null} .
	 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static void notNull(Object object, String message) {
		org.springframework.util.Assert.notNull(object, message);
	}
}
