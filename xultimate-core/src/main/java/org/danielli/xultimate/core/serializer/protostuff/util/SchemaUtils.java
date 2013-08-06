package org.danielli.xultimate.core.serializer.protostuff.util;

import java.util.concurrent.ConcurrentHashMap;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Schema工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class SchemaUtils {
	
	/** Schema的缓存 */
	private static ConcurrentHashMap<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();
	
	/**
	 * 获取指定类型的运行时Schema。
	 * @param clazz 类型。
	 * @return 运行时Schema。
	 */
	public static <T> Schema<T> getSchema(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			cachedSchema.put(clazz, schema);
		}
		return schema;
	}
}
