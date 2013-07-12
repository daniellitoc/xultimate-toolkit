package org.danielli.xultimate.util;

/**
 * <p>Operations on arrays, primitive arrays (like {@code int[]}) and
 * primitive wrapper arrays (like {@code Integer[]}).</p>
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null}
 * array input. However, an Object array that contains a {@code null}
 * element may throw an exception. Each method documents its behaviour.</p>
 *
 * <p>#ThreadSafe#</p>
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.ArrayUtils
 */
public class ArrayUtils {

	/**
     * <p>Checks if an array of Objects is not empty or not {@code null}.</p>
     *
     * @param <T> the component type of the array
     * @param array  the array to test
     * @return {@code true} if the array is not empty or not {@code null}
     */
	public static <T> boolean isNotEmpty(T[] array) {
		return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
	}
	
	/**
     * <p>Checks if the object is in the given array.</p>
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.</p>
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return {@code true} if the array contains the object
     */
	public static boolean contains(Object[] array, Object objectToFind) {
		return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
	}
}
