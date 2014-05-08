package org.danielli.xultimate.shard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.danielli.xultimate.util.collections.CollectionUtils;

/**
 * Shard分表、分库后的结果处理工具类。
 * 
 * @author Daniel Li
 * @since 08 May 2014
 */
public abstract class ShardUtils {

	/**
	 * 过滤。
	 * 
	 * @param elements 元素列表。
	 * @param filter 过滤器。
	 * @return 过滤后的元素列表。
	 */
	public static <S, T> List<T> filter(List<S> elements, Filter<S, T> filter) { 
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			List<T> result = new ArrayList<>();
			for (S element : elements) {
				result.add(filter.doFilter(element));
			}
			return result;
		}
	}
	
	/**
	 * 排序。
	 * 
	 * @param elements 元素列表。
	 * @param comparator 比较器。
	 * @return 比较后的元素列表。
	 */
	public static <E> List<E> sort(List<E> elements, Comparator<? super E> comparator) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			Collections.sort(elements, comparator);
			return elements;
		}
	}
	
	/**
	 * 排序。
	 * 
	 * @param elementMap 元素映射表。
	 * @param keyList 元素Key的排序规则。
	 * @return 按照keyList排序后的元素列表。
	 */
	public static <K, V> List<V> sort(Map<K, V> elementMap, List<K> keyList) {
		if (elementMap == null) {
			return new ArrayList<>(0);
		} else {
			List<V> result = new ArrayList<>();
			for (K key : keyList) {
				result.add(elementMap.get(key));
			}
			return result;
		}
	}
	
	/**
	 * 分页。
	 * 
	 * @param elements 元素列表。
	 * @param offset 起始位置。
	 * @param limit 长度。
	 * @return 分页后的元素列表。
	 */
	public static <E> List<E> limit(List<E> elements, int offset, int limit) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			return CollectionUtils.subList(elements, offset, offset + limit);
		}
	}
	
	/**
	 * 求最大值。
	 * 
	 * @param elements 元素列表。
	 * @param filter 规律生成器，用于生成唯一标识。
	 * @param comparator 比较器，会对唯一标识相同的对象进行比较，保留大的。
	 * @return 通过规则生成器和比较器筛选后的元素列表。
	 */
	public static <S, T> List<S> max(List<S> elements, Filter<S, T> filter, Comparator<? super S> comparator) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			Map<T, S> result = new HashMap<T, S>();
			for (S source : elements) {
				T target = filter.doFilter(source);
				S tmpSource = result.get(target);
				if (tmpSource == null || comparator.compare(source, tmpSource) > 0) {
					result.put(target, source);
				}			
			}
			return new ArrayList<>(result.values());
		}
	}
	
	/**
	 * 求最小值。
	 * 
	 * @param elements 元素列表。
	 * @param filter 规律生成器，用于生成唯一标识。
	 * @param comparator 比较器，会对唯一标识相同的对象进行比较，保留小的。
	 * @return 通过规则生成器和比较器筛选后的元素列表。
	 */
	public static <S, T> List<S> min(List<S> elements, Filter<S, T> filter, Comparator<? super S> comparator) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			Map<T, S> result = new HashMap<T, S>();
			for (S source : elements) {
				T target = filter.doFilter(source);
				S tmpSource = result.get(target);
				if (tmpSource == null || comparator.compare(source, tmpSource) < 0) {
					result.put(target, source);
				}			
			}
			return new ArrayList<>(result.values());
		}
	}
	
	/**
	 * 求汇总或个数或平均值。
	 * 
	 * @param elements 元素列表。
	 * @param filter 规律生成器，用于生成唯一标识。
	 * @param operator 操作器，会对唯一标识相同的对象进行操作，保留操作后的结果。
	 * @return 通过规则生成器和操作器筛选后的元素列表。
	 */
	public static <S, T> List<S> sumOrCountOrAvg(List<S> elements, Filter<S, T> filter, Operator<S> operator) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			Map<T, S> result = new HashMap<T, S>();
			for (S source : elements) {
				T target = filter.doFilter(source);
				S tmpSource = result.get(target);
				if (tmpSource == null) {
					result.put(target, source);
				} else {
					result.put(target, operator.operate(tmpSource, source)) ;
				}
			}
			return new ArrayList<>(result.values());
		}
	}
	
	/**
	 * 去重。
	 * 
	 * @param elements 元素列表。
	 * @param filter 规律生成器，用于生成唯一标识。
	 * @return 通过规则生成器去重后的元素列表。
	 */
	public static <S, T> List<S> distinct(List<S> elements, Filter<S, T> filter) {
		if (elements == null) {
			return new ArrayList<>(0);
		} else {
			Map<T, S> result = new HashMap<T, S>();
			for (S source : elements) {
				T target = filter.doFilter(source);
				S tmpSource = result.get(target);
				if (tmpSource == null) {
					result.put(target, source);
				}			
			}
			return new ArrayList<>(result.values());
		}
	}
}
