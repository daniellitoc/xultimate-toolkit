package org.danielli.xultimate.context.kvStore.db;

import java.util.Iterator;

/**
 * K/V迭代器。
 *
 * @author Daniel Li
 * @since 13 September 2015
 */
public interface KeyValueIterator<K, V> extends Iterator<KeyValueIterator.Entry<K, V>> {

    void close();

    /**
     * K/V元素。
     *
     * @author Daniel Li
     * @since 13 September 2015
     */
    public class Entry<K, V> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }
    }
}
