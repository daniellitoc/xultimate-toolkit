package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.danielli.xultimate.util.Assert;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

/**
 * XMemcached缓存管理器。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see AbstractTransactionSupportingCacheManager
 */
public class XMemcachedCacheManager extends AbstractTransactionSupportingCacheManager {
	/** 配置信息 */
    private ConcurrentHashMap<String, Integer> expireMap = new ConcurrentHashMap<String, Integer>();  
  
    /** XMemcached模板 */
    private XMemcachedTemplate memcachedTemplate;  
    
    private int defaultExpire;
  
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
                expire = defaultExpire;  
                expireMap.put(name, expire);  
            }  
            cache = new XMemcachedCache(name, expire.intValue(), memcachedTemplate);  
            addCache(cache);
		}
		return cache;
    }  
  
    /**
     * 设置配置信息。
     * 
     * @param configMap 配置信息。
     */
    public void setConfigMap(Map<String, Integer> configMap) {  
        this.expireMap.putAll(configMap); 
    }

    /**
     * 设置XMemcached模板。
     * 
     * @param memcachedTemplate XMemcached模板。
     */
	public void setMemcachedTemplate(XMemcachedTemplate memcachedTemplate) {
		this.memcachedTemplate = memcachedTemplate;
	}  
	
	/**
	 * 设置默认失效时间。
	 * 
	 * @param defaultExpire 默认失效时间。
	 */
	public void setDefaultExpire(int defaultExpire) {
		this.defaultExpire = defaultExpire;
	}

}
