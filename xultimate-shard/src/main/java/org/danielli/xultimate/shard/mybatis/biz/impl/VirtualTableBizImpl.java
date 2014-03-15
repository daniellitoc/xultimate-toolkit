package org.danielli.xultimate.shard.mybatis.biz.impl;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientMutex;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualTableBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualTableDAO;
import org.danielli.xultimate.shard.po.VirtualTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualTableBizImpl")
public class VirtualTableBizImpl implements VirtualTableBiz {

	@Resource(name = "virtualTableDAO")
	private VirtualTableDAO virtualTableDAO;
	
	@Resource(name = "xMemcachedTemplate")
	private XMemcachedTemplate xMemcachedTemplate;
	
	@Resource(name = "memcachedClientMutex")
	private MemcachedClientMutex memcachedClientMutex;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;
	
	@Override
	public VirtualTable findOneByVirtualDatabaseNameAndName(final String virtualDatabaseName, final String name) {
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<VirtualTable>() {
			@Override
			public VirtualTable doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTable:virtualDatabaseName:{0}:name:{1}", virtualDatabaseName, name);
				VirtualTable virtualTable = memcachedClient.get(virtualTableIdKey);
				if (virtualTable == null) {
					String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
					if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIdKeyLock)) {
						try {
							virtualTable = virtualTableDAO.findOneByVirtualDatabaseNameAndName(virtualDatabaseName, name);
							if (virtualTable != null) {
								memcachedClient.set(virtualTableIdKey, expireSeconds, virtualTable);
							}
						} finally {
							memcachedClientMutex.unlock(memcachedClient, virtualTableIdKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(memcachedClient);
					}
				}
				return virtualTable;
			}
		});
	}

}
