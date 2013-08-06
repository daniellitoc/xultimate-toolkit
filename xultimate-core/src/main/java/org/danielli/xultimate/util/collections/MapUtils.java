package org.danielli.xultimate.util.collections;

import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;

/** 
 * Provides utility methods and decorators for
 * {@link Map} and {@link SortedMap} instances.
 * <p>
 * It contains various type safe methods
 * as well as other useful features like deep copying.
 * <p>
 * It also provides the following decorators:
 *
 *  <ul>
 *  <li>{@link #fixedSizeMap(Map)}
 *  <li>{@link #fixedSizeSortedMap(SortedMap)}
 *  <li>{@link #lazyMap(Map,Factory)}
 *  <li>{@link #lazyMap(Map,Transformer)}
 *  <li>{@link #lazySortedMap(SortedMap,Factory)}
 *  <li>{@link #lazySortedMap(SortedMap,Transformer)}
 *  <li>{@link #predicatedMap(Map,Predicate,Predicate)}
 *  <li>{@link #predicatedSortedMap(SortedMap,Predicate,Predicate)}
 *  <li>{@link #transformedMap(Map, Transformer, Transformer)}
 *  <li>{@link #transformedSortedMap(SortedMap, Transformer, Transformer)}
 *  <li>{@link #typedMap(Map, Class, Class)}
 *  <li>{@link #typedSortedMap(SortedMap, Class, Class)}
 *  <li>{@link #multiValueMap( Map )}
 *  <li>{@link #multiValueMap( Map, Class )}
 *  <li>{@link #multiValueMap( Map, Factory )}
 *  </ul>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.collections.MapUtils
 */
public class MapUtils {

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     * 
     * @param map  the map to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    public static boolean isEmpty(Map<?, ?> map) {
    	return org.apache.commons.collections.MapUtils.isEmpty(map);
    }
	
    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     * 
     * @param map  the map to check, may be null
     * @return true if non-null and non-empty
     */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return org.apache.commons.collections.MapUtils.isNotEmpty(map);
	}
}
