package org.danielli.xultimate.util;

import java.lang.reflect.Array;

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
     * Creates a new array with the specified component type and
     * length.
     * Invoking this method is equivalent to creating an array
     * as follows:
     * <blockquote>
     * <pre>
     * int[] x = {length};
     * Array.newInstance(componentType, x);
     * </pre>
     * </blockquote>
     *
     * @param componentType the {@code Class} object representing the
     * component type of the new array
     * @param length the length of the new array
     * @return the new array
     * @exception NullPointerException if the specified
     * {@code componentType} parameter is null
     * @exception IllegalArgumentException if componentType is {@link Void#TYPE}
     * @exception NegativeArraySizeException if the specified {@code length}
     * is negative
     */
    @SuppressWarnings("unchecked")
	public static <T> T[] newInstance(Class<T> componentType, int length) {
		return (T[]) Array.newInstance(componentType, length);
	}
	
    /**
     * Creates a new array
     * with the specified component type and dimensions.
     * If {@code componentType}
     * represents a non-array class or interface, the new array
     * has {@code dimensions.length} dimensions and
     * {@code componentType} as its component type. If
     * {@code componentType} represents an array class, the
     * number of dimensions of the new array is equal to the sum
     * of {@code dimensions.length} and the number of
     * dimensions of {@code componentType}. In this case, the
     * component type of the new array is the component type of
     * {@code componentType}.
     *
     * <p>The number of dimensions of the new array must not
     * exceed the number of array dimensions supported by the
     * implementation (typically 255).
     *
     * @param componentType the {@code Class} object representing the component
     * type of the new array
     * @param dimensions an array of {@code int} representing the dimensions of
     * the new array
     * @return the new array
     * @exception NullPointerException if the specified
     * {@code componentType} argument is null
     * @exception IllegalArgumentException if the specified {@code dimensions}
     * argument is a zero-dimensional array, or if the number of
     * requested dimensions exceeds the limit on the number of array dimensions
     * supported by the implementation (typically 255), or if componentType
     * is {@link Void#TYPE}.
     * @exception NegativeArraySizeException if any of the components in
     * the specified {@code dimensions} argument is negative.
     */
    public static Object newInstance(Class<?> componentType, int... dimensions) throws IllegalArgumentException, NegativeArraySizeException {
    	return Array.newInstance(componentType, dimensions);
    }
    
    /**
     * <p>Checks if an array of Objects is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static <T> boolean isEmpty(T[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive longs is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(long[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive ints is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(int[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive shorts is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(short[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive chars is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(char[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive bytes is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(byte[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive doubles is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(double[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive floats is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(float[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
    
    /**
     * <p>Checks if an array of primitive booleans is empty or {@code null}.</p>
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     */
    public static boolean isEmpty(boolean[] array) {
    	return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }
	
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
      * <p>Checks if an array of primitive longs is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(long[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive ints is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(int[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive shorts is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(short[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
	
     /**
      * <p>Checks if an array of primitive chars is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(char[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive bytes is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(byte[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive doubles is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(double[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive floats is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(float[] array) {
    	 return org.apache.commons.lang3.ArrayUtils.isNotEmpty(array);
     }
     
     /**
      * <p>Checks if an array of primitive booleans is not empty or not {@code null}.</p>
      *
      * @param array  the array to test
      * @return {@code true} if the array is not empty or not {@code null}
      */
     public static boolean isNotEmpty(boolean[] array) {
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
	
	/**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll([null], [null]) = [null, null]
     * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array1  the first array whose elements are added to the new array, may be {@code null}
     * @param array2  the second array whose elements are added to the new array, may be {@code null}
     * @return The new array, {@code null} if both arrays are {@code null}.
     *      The type of the new array is the type of the first array,
     *      unless the first array is null, in which case the type is the same as the second array.
     * @since 2.1
     * @throws IllegalArgumentException if the array types are incompatible
     */
    public static <T> T[] addAll(T[] array1, @SuppressWarnings("unchecked") T... array2) {
    	 return org.apache.commons.lang3.ArrayUtils.addAll(array1, array2);
    }
	
    /**
     * <p>Copies the given array and adds the given element at the end of the new array.</p>
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.</p>
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element, unless the element itself is null,
     *  in which case the return type is Object[]</p>
     *
     * <pre>
     * ArrayUtils.add(null, null)      = [null]
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to "add" the element to, may be {@code null}
     * @param element  the object to add, may be {@code null}
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * If both are null, an IllegalArgumentException is thrown
     * @since 2.1
     * @throws IllegalArgumentException if both arguments are null
     */
    public static <T> T[] add(T[] array, T element) {
       return org.apache.commons.lang3.ArrayUtils.add(array, element);
    }
    
    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.</p>
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)      = [null]
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index > array.length).
     * @throws IllegalArgumentException if both array and element are null
     */
    public static <T> T[] add(T[] array, int index, T element) {
    	return org.apache.commons.lang3.ArrayUtils.add(array, index, element);
    }
    
    /**
     * <p>Adds all the elements of the given arrays into a new array.</p>
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.</p>
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new byte[] array.
     * @since 2.1
     */
    public static byte[] addAll(byte[] array1, byte... array2) {
    	return org.apache.commons.lang3.ArrayUtils.addAll(array1, array2);
    }
    
    /**
     * <p>Create a type-safe generic array.</p>
     *
     * <p>The Java language does not allow an array to be created from a generic type:</p>
     *
     * <pre>
    public static &lt;T&gt; T[] createAnArray(int size) {
        return new T[size]; // compiler error here
    }
    public static &lt;T&gt; T[] createAnArray(int size) {
        return (T[])new Object[size]; // ClassCastException at runtime
    }
     * </pre>
     *
     * <p>Therefore new arrays of generic types can be created with this method.
     * For example, an array of Strings can be created:</p>
     *
     * <pre>
    String[] array = ArrayUtils.toArray("1", "2");
    String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
     * </pre>
     *
     * <p>The method is typically used in scenarios, where the caller itself uses generic types
     * that have to be combined into an array.</p>
     *
     * <p>Note, this method makes only sense to provide arguments of the same type so that the
     * compiler can deduce the type of the array itself. While it is possible to select the
     * type explicitly like in
     * <code>Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))</code>,
     * there is no real advantage when compared to
     * <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.</p>
     *
     * @param  <T>   the array's element type
     * @param  items  the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     * @since  3.0
     */
    public static <T> T[] toArray(@SuppressWarnings("unchecked")final T... items) {
    	return org.apache.commons.lang3.ArrayUtils.toArray(items);
    }
    
    /**
     * <p>Returns the length of the specified array.
     * This method can deal with {@code Object} arrays and with primitive arrays.</p>
     *
     * <p>If the input array is {@code null}, {@code 0} is returned.</p>
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array  the array to retrieve the length from, may be null
     * @return The length of the array, or {@code 0} if the array is {@code null}
     * @throws IllegalArgumentException if the object arguement is not an array.
     * @since 2.1
     */
    public static int getLength(Object array) {
    	return org.apache.commons.lang3.ArrayUtils.getLength(array);
    }
    
    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.</p>
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static <T> T[] remove(T[] array, int index) {
    	return org.apache.commons.lang3.ArrayUtils.remove(array, index);
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.</p>
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.</p>
     *
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static <T> T[] removeElement(T[] array, Object element) {
    	return org.apache.commons.lang3.ArrayUtils.removeElement(array, element);
    }
}
