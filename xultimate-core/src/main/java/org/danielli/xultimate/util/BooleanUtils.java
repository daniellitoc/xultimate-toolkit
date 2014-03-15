package org.danielli.xultimate.util;

/**
 * <p>Operations on boolean primitives and Boolean objects.</p>
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null} input.
 * Each method documents its behaviour in more detail.</p>
 *
 * <p>#ThreadSafe#</p>
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.BooleanUtils
 */
public class BooleanUtils {
	
	/**
     * <p>Checks if a {@code Boolean} value is {@code true},
     * handling {@code null} by returning {@code false}.</p>
     *
     * <pre>
     *   BooleanUtils.isTrue(Boolean.TRUE)  = true
     *   BooleanUtils.isTrue(Boolean.FALSE) = false
     *   BooleanUtils.isTrue(null)          = false
     * </pre>
     *
     * @param bool  the boolean to check, null returns {@code false}
     * @return {@code true} only if the input is non-null and true
     */
	public static boolean isTrue(Boolean bool) {
		return org.apache.commons.lang3.BooleanUtils.isTrue(bool);
	}
	
    /**
     * <p>Checks if a {@code Boolean} value is <i>not</i> {@code true},
     * handling {@code null} by returning {@code true}.</p>
     *
     * <pre>
     *   BooleanUtils.isNotTrue(Boolean.TRUE)  = false
     *   BooleanUtils.isNotTrue(Boolean.FALSE) = true
     *   BooleanUtils.isNotTrue(null)          = true
     * </pre>
     *
     * @param bool  the boolean to check, null returns {@code true}
     * @return {@code true} if the input is null or false
     * @since 2.3
     */
    public static boolean isNotTrue(Boolean bool) {
        return org.apache.commons.lang3.BooleanUtils.isNotTrue(bool);
    }

    /**
     * <p>Checks if a {@code Boolean} value is {@code false},
     * handling {@code null} by returning {@code false}.</p>
     *
     * <pre>
     *   BooleanUtils.isFalse(Boolean.TRUE)  = false
     *   BooleanUtils.isFalse(Boolean.FALSE) = true
     *   BooleanUtils.isFalse(null)          = false
     * </pre>
     *
     * @param bool  the boolean to check, null returns {@code false}
     * @return {@code true} only if the input is non-null and false
     * @since 2.1
     */
    public static boolean isFalse(Boolean bool) {
    	 return org.apache.commons.lang3.BooleanUtils.isFalse(bool);
    }

    /**
     * <p>Checks if a {@code Boolean} value is <i>not</i> {@code false},
     * handling {@code null} by returning {@code true}.</p>
     *
     * <pre>
     *   BooleanUtils.isNotFalse(Boolean.TRUE)  = true
     *   BooleanUtils.isNotFalse(Boolean.FALSE) = false
     *   BooleanUtils.isNotFalse(null)          = true
     * </pre>
     *
     * @param bool  the boolean to check, null returns {@code true}
     * @return {@code true} if the input is null or true
     * @since 2.3
     */
    public static boolean isNotFalse(Boolean bool) {
    	return org.apache.commons.lang3.BooleanUtils.isNotFalse(bool);
    }
	
	/**
     * <p>Converts a String to a Boolean.</p>
     *
     * <p>{@code 'true'}, {@code 'on'} or {@code 'yes'}
     * (case insensitive) will return {@code true}.
     * {@code 'false'}, {@code 'off'} or {@code 'no'}
     * (case insensitive) will return {@code false}.
     * Otherwise, {@code null} is returned.</p>
     *
     * <p>NOTE: This returns null and will throw a NullPointerException if autoboxed to a boolean. </p>
     *
     * <pre>
     *   BooleanUtils.toBooleanObject(null)    = null
     *   BooleanUtils.toBooleanObject("true")  = Boolean.TRUE
     *   BooleanUtils.toBooleanObject("false") = Boolean.FALSE
     *   BooleanUtils.toBooleanObject("on")    = Boolean.TRUE
     *   BooleanUtils.toBooleanObject("ON")    = Boolean.TRUE
     *   BooleanUtils.toBooleanObject("off")   = Boolean.FALSE
     *   BooleanUtils.toBooleanObject("oFf")   = Boolean.FALSE
     *   BooleanUtils.toBooleanObject("blue")  = null
     * </pre>
     *
     * @param str  the String to check
     * @return the Boolean value of the string, {@code null} if no match or {@code null} input
     */
	public static Boolean toBooleanObject(String str) {
		return org.apache.commons.lang3.BooleanUtils.toBooleanObject(str);
	}
	
}
