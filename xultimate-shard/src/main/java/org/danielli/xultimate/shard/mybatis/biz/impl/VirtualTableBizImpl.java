package org.danielli.xultimate.shard.mybatis.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClientCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedClientTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualTableBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualTableDAO;
import org.danielli.xultimate.shard.po.VirtualTable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualTableBizImpl")
public class VirtualTableBizImpl implements VirtualTableBiz, InitializingBean {

	@Resource(name = "virtualTableDAO")
	private VirtualTableDAO virtualTableDAO;
	
	@Resource(name = "xMemcachedClientTemplate")
	private XMemcachedClientTemplate xMemcachedClientTemplate;
	
	@Resource(name = "memcachedLockFactory")
	private MemcachedLockFactory memcachedLockFactory;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;
	
	private MemcachedLock memcachedLock;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		memcachedLock = memcachedLockFactory.getLock(10);
	}
	
	@Override
	public VirtualTable findOneByVirtualDatabaseNameAndName(final String virtualDatabaseName, final String name) {
		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<VirtualTable>() {
			@Override
			public VirtualTable doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTable:virtualDatabaseName:{0}:name:{1}", virtualDatabaseName, name);
				VirtualTable virtualTable = xMemcachedClient.get(virtualTableIdKey);
				if (virtualTable == null) {
					String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
					if (memcachedLock.tryLock(virtualTableIdKeyLock)) {
						try {
							virtualTable = virtualTableDAO.findOneByVirtualDatabaseNameAndName(virtualDatabaseName, name);
							if (virtualTable != null) {
								xMemcachedClient.set(virtualTableIdKey, expireSeconds, virtualTable);
							}
						} finally {
							memcachedLock.unlock(virtualTableIdKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(xMemcachedClient);
					}
				}
				return virtualTable;
			}
		});
	}

}
