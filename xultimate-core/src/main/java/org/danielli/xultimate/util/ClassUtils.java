package org.danielli.xultimate.util;

/**
 * <p>Operates on classes without using reflection.</p>
 *
 * <p>This class handles invalid {@code null} inputs as best it can.
 * Each method documents its behaviour in more detail.</p>
 *
 * <p>The notion of a {@code canonical name} includes the human
 * readable name for the type, for example {@code int[]}. The
 * non-canonical method variants work with the JVM names, such as
 * {@code [I}. </p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.ClassUtils
 */
public class ClassUtils {
	
    /**
     * <p>Checks if one {@code Class} can be assigned to a variable of
     * another {@code Class}.</p>
     *
     * <p>Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method,
     * this method takes into account widenings of primitive classes and
     * {@code null}s.</p>
     *
     * <p>Primitive widenings allow an int to be assigned to a long, float or
     * double. This method returns the correct result for these cases.</p>
     *
     * <p>{@code Null} may be assigned to any reference type. This method
     * will return {@code true} if {@code null} is passed in and the
     * toClass is non-primitive.</p>
     *
     * <p>Specifically, this method tests whether the type represented by the
     * specified {@code Class} parameter can be converted to the type
     * represented by this {@code Class} object via an identity conversion
     * widening primitive or widening reference conversion. See
     * <em><a href="http://java.sun.com/docs/books/jls/">The Java Language Specification</a></em>,
     * sections 5.1.1, 5.1.2 and 5.1.4 for details.</p>
     *
     * <p><strong>Since Lang 3.0,</strong> this method will default behavior for
     * calculating assignability between primitive and wrapper types <em>corresponding
     * to the running Java version</em>; i.e. autoboxing will be the default
     * behavior in VMs running Java versions >= 1.5.</p>
     *
     * @param cls  the Class to check, may be null
     * @param toClass  the Class to try to assign into, returns false if null
     * @return {@code true} if assignment possible
     */
	public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
		return org.apache.commons.lang3.ClassUtils.isAssignable(cls, toClass);
	}
}
