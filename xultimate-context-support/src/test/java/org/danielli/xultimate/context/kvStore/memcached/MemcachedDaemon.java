package org.danielli.xultimate.context.kvStore.memcached;

import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;

public class MemcachedDaemon implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(MemcachedDaemon.class);

	private MemCacheDaemon<LocalCacheElement> daemon;

	private String serverUrl = "127.0.0.1:11211";

	private int maxItems = 1024 * 100;
	private long maxBytes = 1024 * 100 * 2048;
	
	private boolean binary = true;

	@Override
	public void afterPropertiesSet() throws Exception {

		logger.info("Initializing JMemcached Server");

		daemon = new MemCacheDaemon<LocalCacheElement>();
		CacheStorage<Key, LocalCacheElement> storage = ConcurrentLinkedHashMap.create(ConcurrentLinkedHashMap.EvictionPolicy.FIFO, maxItems, maxBytes);
		daemon.setCache(new CacheImpl(storage));
		daemon.setBinary(binary);
		daemon.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		daemon.setVerbose(true);
		
		daemon.start();
		logger.info("Initialized JMemcached Server");
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Shutdowning Jmemcached Server");
		daemon.stop();
		logger.info("Shutdowned Jmemcached Server");
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}

	public boolean isBinary() {
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}
}
