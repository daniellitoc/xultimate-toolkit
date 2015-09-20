package org.danielli.xultimate.context.kvStore.db.serializer;

/**
 * 序列化器。
 *
 * @author Daniel Li
 * @since 13 September 2015
 */
public interface Serializer<T> {

    byte[] serialize(T value);

    T deserializer(byte[] bytes);
}
