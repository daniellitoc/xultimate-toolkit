package org.danielli.xultimate.context.kvStore.db;

import org.danielli.xultimate.context.kvStore.KeyValueStoreException;

import java.util.Comparator;
import java.util.List;

/**
 * K/V存储。
 *
 * @author Daniel Li
 * @since 13 September 2015
 */
public interface KeyValueStore<K, V> {

    V get(K key) throws KeyValueStoreException;

    void put(K key, V value) throws KeyValueStoreException;

    void putAll(List<KeyValueIterator.Entry<K, V>> entries) throws KeyValueStoreException;

    void remove(K key) throws KeyValueStoreException;

    KeyValueIterator<K, V> range(K from, K to, Comparator<K> comparator);

    KeyValueIterator<K, V> all();

    void close();

    void flush() throws KeyValueStoreException;

}
