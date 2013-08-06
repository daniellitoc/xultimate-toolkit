package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class XMemcachedCache implements Cache {
	
	private final String name;  
    private final XMemcache memcache;  
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
