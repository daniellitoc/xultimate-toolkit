package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.danielli.xultimate.util.Assert;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

public class XMemcachedCacheManager extends AbstractTransactionSupportingCacheManager {
	
    private ConcurrentHashMap<String, Integer> expireMap = new ConcurrentHashMap<String, Integer>();  
  
    private XMemcachedTemplate memcachedTemplate;  
  
    public XMemcachedCacheManager() { }  
  
    @Override  
    protected Collection<? extends Cache> loadCaches() {  
    	Assert.notNull(this.memcachedTemplate, "A backing memcachedTemplate is required");
		return new LinkedHashSet<Cache>(0);
    }  
  
    @Override  
    public Cache getCache(String name) {    
        Cache cache = super.getCache(name);
		if (cache == null) {
			Integer expire = expireMap.get(name);  
            if (expire == null) {  
                expire = 0;  
                expireMap.put(name, expire);  
            }  
            cache = new XMemcachedCache(name, expire.intValue(), memcachedTemplate);  
            addCache(cache);
		}
		return cache;
    }  
  
    public void setConfigMap(Map<String, Integer> configMap) {  
        this.expireMap.putAll(configMap); 
    }

	public void setMemcachedTemplate(XMemcachedTemplate memcachedTemplate) {
		this.memcachedTemplate = memcachedTemplate;
	}  

}
