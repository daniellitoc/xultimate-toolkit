package org.danielli.xultimate.util.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Provides utility methods and decorators for {@link Collection} instances.
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.collections.CollectionUtils
 */
public class CollectionUtils {
	
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     * 
     * @param collection  the collection to check, may be null
     * @return true if empty or null
     */
	public static boolean isEmpty(Collection<?> collection) {
		return org.apache.commons.collections.CollectionUtils.isEmpty(collection);
	}
	
    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     * 
     * @param collection  the collection to check, may be null
     * @return true if non-null and non-empty
     * @since Commons Collections 3.2
     */
    public static boolean isNotEmpty(Collection<?> collection) {
    	return org.apache.commons.collections.CollectionUtils.isNotEmpty(collection);
    }
    
    /**
     * Returns a set backed by the specified map.  The resulting set displays
     * the same ordering, concurrency, and performance characteristics as the
     * backing map.  In essence, this factory method provides a {@link Set}
     * implementation corresponding to any {@link Map} implementation.  There
     * is no need to use this method on a {@link Map} implementation that
     * already has a corresponding {@link Set} implementation (such as {@link
     * HashMap} or {@link TreeMap}).
     *
     * <p>Each method invocation on the set returned by this method results in
     * exactly one method invocation on the backing map or its <tt>keySet</tt>
     * view, with one exception.  The <tt>addAll</tt> method is implemented
     * as a sequence of <tt>put</tt> invocations on the backing map.
     *
     * <p>The specified map must be empty at the time this method is invoked,
     * and should not be accessed directly after this method returns.  These
     * conditions are ensured if the map is created empty, passed directly
     * to this method, and no reference to the map is retained, as illustrated
     * in the following code fragment:
     * <pre>
     *    Set&lt;Object&gt; weakHashSet = Collections.newSetFromMap(
     *        new WeakHashMap&lt;Object, Boolean&gt;());
     * </pre>
     *
     * @param map the backing map
     * @return the set backed by the map
     * @throws IllegalArgumentException if <tt>map</tt> is not empty
     */
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
    	return Collections.newSetFromMap(map);
    }
    
    /**
     * Returns a new list containing the second list appended to the
     * first list.  The {@link List#addAll(Collection)} operation is
     * used to append the two given lists into a new list.
     *
     * @param list1  the first list 
     * @param list2  the second list
     * @return  a new list containing the union of those lists
     * @throws NullPointerException if either list is null
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> union(final List<T> list1, final List<T> list2) {
        return org.apache.commons.collections.ListUtils.union(list1, list2);
    }
    
    /**
     * 并集
     */
	public static <T> Set<T> union(final Set<T> set1, final Set<T> set2) {
		Set<T> tmp = new LinkedHashSet<T>(set1);
	    tmp.addAll(set2);
	    return tmp;
    }
    
    /**
     * Returns a new list containing all elements that are contained in
     * both given lists.
     *
     * @param list1  the first list
     * @param list2  the second list
     * @return  the intersection of those two lists
     * @throws NullPointerException if either list is null
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> intersection(final List<T> list1, final List<T> list2) {
        return org.apache.commons.collections.ListUtils.intersection(list1, list2);
    }
    
    /**
     * 交集
     */
	public static <T> Set<T> intersection(final Set<T> set1, final Set<T> set2) {
		Set<T> tmp = new LinkedHashSet<T>(set1);
	    tmp.retainAll(set2);
	    return tmp;
    }
    
    /**
     * Subtracts all elements in the second list from the first list,
     * placing the results in a new list.
     * <p>
     * This differs from {@link List#removeAll(Collection)} in that
     * cardinality is respected; if <Code>list1</Code> contains two
     * occurrences of <Code>null</Code> and <Code>list2</Code> only
     * contains one occurrence, then the returned list will still contain
     * one occurrence.
     *
     * @param list1  the list to subtract from
     * @param list2  the list to subtract
     * @return  a new list containing the results
     * @throws NullPointerException if either list is null
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> subtract(final List<T> list1, final List<T> list2) {
    	return org.apache.commons.collections.ListUtils.subtract(list1, list2);
    }
    
    /**
     * 差集
     */
	public static <T> Set<T> subtract(final Set<T> set1, final Set<T> set2) {
		Set<T> tmp = new LinkedHashSet<T>(set1);
	    tmp.removeAll(set2);
	    return tmp;
    }
    
    /**
     * 补集
     */
	public static <T> List<T> disjunction(final List<T> list1, final List<T> list2) {
    	return subtract(union(list1, list2), intersection(list1, list2));
    }
	
    /**
     * 补集
     */
	public static <T> Set<T> disjunction(final Set<T> set1, final Set<T> set2) {
		return subtract(union(set1, set2), intersection(set1, set2));
    }
	
	public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
		if (list == null)
			return null;
		return list.subList(fromIndex, Math.min(list.size(), toIndex));
	}
}
