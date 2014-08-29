package org.danielli.xultimate.context.kvStore.memcached.xmemcached.spring;

import java.util.concurrent.CopyOnWriteArraySet;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;

/**
 * XMemcached缓存服务。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class XMemcache {
	/** 缓存Key前缀 */
    private final String name;  
    /** 失效时间 */
    private final int expire;  
    /** XMemcached客户端 */
    private final XMemcachedClient xMemcachedClient;  
    /** 设置过缓存的Key集合 */
    private CopyOnWriteArraySet<String> keySet = new CopyOnWriteArraySet<>();

    public XMemcache(String name, int expire, XMemcachedClient xMemcachedClient) {  
        this.name = name;  
        this.expire = expire;  
        this.xMemcachedClient = xMemcachedClient;
    }  
    
    /** 生成缓存Key */
    private String getKey(String key) {
        return name + "_" + key;  
    } 
  
    /**
     * 获取缓存。
     * 
     * @param key 健后缀。
     * @return 值。
     */
    public Object get(String key) {
    	String k = getKey(key);
    	return xMemcachedClient.get(k);
    }  
  
    /**
     * 设置缓存。
     * 
     * @param key 健后缀。
     * @param value 值。
     */
    public void put(String key, Object value) {  
        if (value == null) return;  
        String k = getKey(key);
        Object v = value;
        xMemcachedClient.setWithNoReply(k, expire, v);
        keySet.add(k);
    }  
      
    /**
     * 清理所有缓存。
     */
    public void clear() {  
    	for (String key : keySet) {
    		xMemcachedClient.deleteWithNoReply(key);
	    }
	    keySet.clear();
    }  
      
    /**
     * 删除缓存 
     * 
     * @param key 健后缀。
     */
    public void delete(String key) {
        String k = getKey(key);
        xMemcachedClient.deleteWithNoReply(k);
	    keySet.remove(k);
    }  
}
