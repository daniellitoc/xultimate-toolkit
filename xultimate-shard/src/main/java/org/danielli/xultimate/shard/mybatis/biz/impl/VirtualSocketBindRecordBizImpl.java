package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.callback.XMemcachedClientCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedClientTemplate;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualSocketBindRecordBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualSocketBindRecordDAO;
import org.danielli.xultimate.shard.po.VirtualSocketBindRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualSocketBindRecordBizImpl")
public class VirtualSocketBindRecordBizImpl implements VirtualSocketBindRecordBiz, InitializingBean {

	@Resource(name = "virtualSocketBindRecordDAO")
	private VirtualSocketBindRecordDAO virtualSocketBindRecordDAO;
	
	@Resource(name = "xMemcachedClientTemplate")
	private XMemcachedClientTemplate xMemcachedClientTemplate;
	
	@Resource(name = "memcachedLockFactory")
	private MemcachedLockFactory memcachedLockFactory;
	
	@Resource(name = "jsonTemplate")
	private JSONTemplate jsonTemplate;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;	
	
	private MemcachedLock memcachedLock;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		memcachedLock = memcachedLockFactory.getLock(10);
	}
	
	@Override
	public List<VirtualSocketBindRecord> findByVirtualTableIntervalIdList(final List<Long> virtualTableIntervalIdList) {
		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<List<VirtualSocketBindRecord>>() {
			@Override
			public List<VirtualSocketBindRecord> doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String virtualTableIntervalIdListKey = FormatterUtils.format("VirtualSocketBindRecord:virtualTableIntervalIdList:{0}", jsonTemplate.writeValueAsString(virtualTableIntervalIdList));
				List<VirtualSocketBindRecord> virtualSocketBindRecordList = xMemcachedClient.get(virtualTableIntervalIdListKey);
				if (virtualSocketBindRecordList == null) {
					String virtualTableIntervalIdListKeyLock = FormatterUtils.format("{0}.lock", virtualTableIntervalIdListKey);
					if (memcachedLock.tryLock(virtualTableIntervalIdListKeyLock)) {
						try {
							virtualSocketBindRecordList = virtualSocketBindRecordDAO.findByVirtualTableIntervalIdList(virtualTableIntervalIdList);
							xMemcachedClient.set(virtualTableIntervalIdListKey, expireSeconds, virtualSocketBindRecordList);
						} finally {
							memcachedLock.unlock(virtualTableIntervalIdListKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(xMemcachedClient);
					}
				}
				return virtualSocketBindRecordList;
			}
		});
	}

}
