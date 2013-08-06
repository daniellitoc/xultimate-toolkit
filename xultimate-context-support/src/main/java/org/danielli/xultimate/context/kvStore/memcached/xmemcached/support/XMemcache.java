package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;

public class XMemcache {
	
    private final String name;  
    private final int expire;  
    private final XMemcachedTemplate memcachedTemplate;  
    
    private Set<String> keySet = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    public XMemcache(String name, int expire, XMemcachedTemplate memcachedTemplate) {  
        this.name = name;  
        this.expire = expire;  
        this.memcachedTemplate = memcachedTemplate;
    }  
    
    private String getKey(String key) {  
        return name + "_" + key;  
    } 
  
    public Object get(String key) {
    	final String k = getKey(key);
        return memcachedTemplate.execute(new XMemcachedReturnedCallback<Object>() {

			@Override
			public Object doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				return memcachedClient.get(k);
			}
		});
    }  
  
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
