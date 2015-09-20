package org.danielli.xultimate.context.kvStore.db.support;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.danielli.xultimate.context.kvStore.db.KeyValueIterator;
import org.danielli.xultimate.context.kvStore.db.KeyValueIterator.Entry;
import org.danielli.xultimate.context.kvStore.db.KeyValueStore;
import org.danielli.xultimate.context.kvStore.db.KeyValueStoreException;
import org.danielli.xultimate.context.kvStore.db.serializer.Serializer;

import java.util.Comparator;
import java.util.List;

/**
 * 可序列化K/V存储。
 *
 * @author Daniel Li
 * @since 13 September 2015
 */
public class SerializableKeyValueStore<K, V> implements KeyValueStore<K, V> {

    private final KeyValueStore<byte[], byte[]> store;
    private final Serializer<K> keySerializer;
    private final Serializer<V> valueSerializer;

    public SerializableKeyValueStore(KeyValueStore<byte[], byte[]> store, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        this.store = store;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    private byte[] serializeKey(K source) {
        if (source == null) {
            return null;
        }
        return keySerializer.serialize(source);
    }

    private byte[] serializeValue(V source) {
        if (source == null) {
            return null;
        }
        return valueSerializer.serialize(source);
    }

    private K deserializeKey(byte[] target) {
        if (target == null) {
            return null;
        }
        return keySerializer.deserializer(target);
    }

    private V deserializeValue(byte[] target) {
        if (target == null) {
            return null;
        }
        return valueSerializer.deserializer(target);
    }

    @Override
    public V get(K key) throws KeyValueStoreException {
        byte[] targetKey = serializeKey(key);
        byte[] targetValue = store.get(targetKey);
        return deserializeValue(targetValue);
    }

    @Override
    public void put(K key, V value) throws KeyValueStoreException {
        byte[] targetKey = serializeKey(key);
        byte[] targetValue = serializeValue(value);
        store.put(targetKey, targetValue);
    }

    @Override
    public void putAll(List<Entry<K, V>> entries) throws KeyValueStoreException {
        this.store.putAll(ListUtils.transformedList(entries, new Transformer() {
            @Override
            public Entry<byte[], byte[]> transform(Object o) {
                Entry<K, V> entry = (Entry<K, V>) o;
                byte[] targetKey = serializeKey(entry.getKey());
                byte[] targetValue = serializeValue(entry.getValue());
                return new Entry<>(targetKey, targetValue);
            }
        }));
    }


    @Override
    public void remove(K key) throws KeyValueStoreException {
        byte[] targetKey = serializeKey(key);
        store.remove(targetKey);
    }

    @Override
    public KeyValueIterator<K, V> range(final K from, final K to, final Comparator<K> comparator) {
        byte[] targetFrom = serializeKey(from);
        byte[] targetTo = serializeKey(to);
        return new SerializableIterator(store.range(targetFrom, targetTo, new Comparator<byte[]>() {
            @Override
            public int compare(byte[] o1, byte[] o2) {
                K k1 = deserializeKey(o1);
                K k2 = deserializeKey(o2);
                return comparator.compare(k1, k2);
            }
        }));
    }

    @Override
    public KeyValueIterator<K, V> all() {
        return new SerializableIterator(store.all());
    }

    @Override
    public void close() {
        store.close();
    }

    @Override
    public void flush() throws KeyValueStoreException {
        store.flush();
    }

    /**
     * 可序列化K/V迭代器。
     *
     * @author Daniel Li
     * @since 13 September 2015
     */
    public class SerializableIterator implements KeyValueIterator<K, V> {
        private final KeyValueIterator<byte[], byte[]> iter;

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public void remove() {
            this.iter.remove();
        }

        public void close() {
            this.iter.close();
        }

        public Entry<K, V> next() {
            Entry<byte[], byte[]> next = this.iter.next();
            K key = deserializeKey(next.getKey());
            V value = deserializeValue(next.getValue());
            return new Entry<>(key, value);
        }

        public SerializableIterator(KeyValueIterator<byte[], byte[]> iter) {
            this.iter = iter;
        }
    }
}
