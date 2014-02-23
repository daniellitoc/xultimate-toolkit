package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;

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
    /** XMemcached模板 */
    private final XMemcachedTemplate memcachedTemplate;  
    /** 设置过缓存的Key集合 */
    private Set<String> keySet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    public XMemcache(String name, int expire, XMemcachedTemplate memcachedTemplate) {  
        this.name = name;  
        this.expire = expire;  
        this.memcachedTemplate = memcachedTemplate;
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
    	final String k = getKey(key);
        return memcachedTemplate.execute(new XMemcachedReturnedCallback<Object>() {

			@Override
			public Object doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				return memcachedClient.get(k);
			}
		});
    }  
  
    /**
     * 设置缓存。
     * 
     * @param key 健后缀。
     * @param value 值。
     */
    public void put(String key, Object value) {  
        if (value == null) return;  
        final String k = getKey(key);
        final Object v = value;
        memcachedTemplate.execute(new XMemcachedReturnedCallback<Void>() {
			
			@Override
			public Void doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				memcachedClient.setWithNoReply(k, expire, v);
				keySet.add(k);
				return null;
			}
		});
    }  
      
    /**
     * 清理所有缓存。
     */
    public void clear() {  
    	memcachedTemplate.execute(new XMemcachedReturnedCallback<Void>() {
    		@Override
    		public Void doInXMemcached(MemcachedClient memcachedClient) throws Exception {
    			synchronized (keySet) {
				    for (String key : keySet) {
				    	memcachedClient.deleteWithNoReply(key);
				    }
				    keySet.clear();
    			}
    			return null;
			}
		});
    }  
      
    /**
     * 删除缓存 
     * 
     * @param key 健后缀。
     */
    public void delete(String key) {
        final String k = getKey(key);
    	memcachedTemplate.execute(new XMemcachedReturnedCallback<Void>() {
    		@Override
    		public Void doInXMemcached(MemcachedClient memcachedClient) throws Exception {
    			synchronized (keySet) {
				    memcachedClient.deleteWithNoReply(k);
				    keySet.remove(k);
    			}
    			return null;
			}
		});
    }  
      
      

}
