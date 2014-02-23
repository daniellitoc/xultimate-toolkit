package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * XMemcached缓存封装类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see Cache
 */
public class XMemcachedCache implements Cache {
	/** 缓存Key前缀 */ 
	private final String name;  
	/** XMemcached缓存服务 */
    private final XMemcache memcache; 
    /** XMemcached模板 */
    private final XMemcachedTemplate memcachedTemplate;
      
    public XMemcachedCache(String name, int expire, XMemcachedTemplate memcachedTemplate) {  
        this.name = name;  
        this.memcachedTemplate = memcachedTemplate;   
        this.memcache = new XMemcache(name, expire, memcachedTemplate);  
    }  
  
    @Override  
    public void clear() {  
    	memcache.clear();  
    }  
  
    @Override  
    public void evict(Object key) {  
    	memcache.delete(key.toString());  
    }  
  
    @Override  
    public ValueWrapper get(Object key) {  
        ValueWrapper wrapper = null;  
        Object value = memcache.get(key.toString());  
        if (value != null) {  
            wrapper = new SimpleValueWrapper(value);  
        }  
        return wrapper;  
    }  
  
    @Override  
    public String getName() {  
        return this.name;  
    }  
  
    @Override  
    public XMemcachedTemplate getNativeCache() {  
        return this.memcachedTemplate;  
    }  
  
    @Override  
    public void put(Object key, Object value) {  
    	memcache.put(key.toString(), value);  
    }  
}
