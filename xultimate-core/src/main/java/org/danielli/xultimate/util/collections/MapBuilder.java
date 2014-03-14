package org.danielli.xultimate.util.collections;

import java.util.Map;

import org.apache.commons.lang3.builder.Builder;

/**
 * 创建Map的Builder设计模式。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 *
 * @param <K> Map键类型。
 * @param <V> Map值类型。
 */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

	private Map<K, V> map;
	
	public MapBuilder(Map<K, V> map) {
		this.map = map;
	}
	
	public MapBuilder<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}
	
	public MapBuilder<K, V> putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		return this;
	}
	
	public MapBuilder<K, V> clear() {
		map.clear();
		return this;
	}
	
	public MapBuilder<K, V> remove(K key) {
		map.remove(key);
		return this;
	}
	
	@Override
	public Map<K, V> build() {
		return map;
	}
	
}
