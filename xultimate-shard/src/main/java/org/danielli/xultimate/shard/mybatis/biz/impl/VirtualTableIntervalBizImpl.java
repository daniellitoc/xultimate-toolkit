package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClientCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedClientTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualTableIntervalBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualTableIntervalDAO;
import org.danielli.xultimate.shard.po.VirtualTableInterval;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualTableIntervalBizImpl")
public class VirtualTableIntervalBizImpl implements VirtualTableIntervalBiz, InitializingBean {

	@Resource(name = "virtualTableIntervalDAO")
	private VirtualTableIntervalDAO virtualTableIntervalDAO;
	
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
	
//	@Override
//	public List<VirtualTableInterval> findByVirtualTableId(final Long virtualTableId) {
//		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<List<VirtualTableInterval>>() {
//			@Override
//			public List<VirtualTableInterval> doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
//				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualTableId:{0}", virtualTableId);
//				List<Long> virtualTableIntervalIdKeyList = xMemcachedClient.get(virtualTableIdKey);
//				if (virtualTableIntervalIdKeyList == null) {
//					String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
//					if (memcachedLock.tryLock(virtualTableIdKeyLock)) {
//						try {
//							List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualTableId(virtualTableId);
//							virtualTableIntervalIdKeyList = new ArrayList<>();
//							for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
//								virtualTableIntervalIdKeyList.add(virtualTableInterval.getId());
//								String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
//								xMemcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
//							}
//							xMemcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
//							return virtualTableIntervals;
//						} finally {
//							memcachedLock.unlock(virtualTableIdKeyLock);;
//						}
//					} else {
//						Thread.sleep(500);
//						return doInXMemcached(xMemcachedClient);
//					}
//				} else {
//					List<VirtualTableInterval> virtualTableIntervalList = new ArrayList<>();
//					for (Long virtualTableIntervalIdKey : virtualTableIntervalIdKeyList) {
//						virtualTableIntervalList.add(findOneById(virtualTableIntervalIdKey))
//					}
//					return virtualTableIntervalList;
//				}
//			}
//		});
//	}
	
	@Override
	public List<VirtualTableInterval> findByVirtualTableId(final Long virtualTableId) {
		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<List<VirtualTableInterval>>() {
			@Override
			public List<VirtualTableInterval> doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualTableId:{0}", virtualTableId);
				List<String> virtualTableIntervalIdKeyList = xMemcachedClient.get(virtualTableIdKey);
				if (virtualTableIntervalIdKeyList != null) {
					List<VirtualTableInterval> virtualTableIntervalList = new ArrayList<>();
					for (String virtualTableIntervalIdKey : virtualTableIntervalIdKeyList) {
						VirtualTableInterval virtualTableInterval = xMemcachedClient.get(virtualTableIntervalIdKey);
						if (virtualTableInterval != null)
							virtualTableIntervalList.add(virtualTableInterval);
						else
							break;
						
					}
					if (virtualTableIntervalList.size() == virtualTableIntervalIdKeyList.size()) {
						return virtualTableIntervalList; 
					}
				}
				String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
				if (memcachedLock.tryLock(virtualTableIdKeyLock)) {
					try {
						List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualTableId(virtualTableId);
						virtualTableIntervalIdKeyList = new ArrayList<>();
						for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
							String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
							virtualTableIntervalIdKeyList.add(virtualTableIntervalIdKey);
							xMemcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
						}
						xMemcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
						return virtualTableIntervals;
					} finally {
						memcachedLock.unlock(virtualTableIdKeyLock);;
					}
				} else {
					Thread.sleep(500);
					return doInXMemcached(xMemcachedClient);
				}
			}
		});
	}

	@Override
	public void updateAvailableById(final Long id, final boolean available) {
		final DateTime currentTime = new DateTime();
		virtualTableIntervalDAO.updateAvailableById(id, available, currentTime.toDate());
		
		xMemcachedClientTemplate.execute(new XMemcachedClientCallback<Void>() {
			@Override
			public Void doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", id);
				VirtualTableInterval virtualTableInterval = xMemcachedClient.get(virtualTableIntervalIdKey);
				if (virtualTableInterval != null) {
					virtualTableInterval.setAvailable(available);;
					virtualTableInterval.setUpdateTime(currentTime.toDate());
					xMemcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
				}
				return null;
			}
		});
	}
	
	@Override
	public List<VirtualTableInterval> findByVirtualDatabaseNameAndVirtualTableName(final String virtualDatabaseName, final String virtualTableName) {
		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<List<VirtualTableInterval>>() {
			@Override
			public List<VirtualTableInterval> doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualDatabaseName:{0}:virtualTableName:{1}", virtualDatabaseName, virtualTableName);
				List<String> virtualTableIntervalIdKeyList = xMemcachedClient.get(virtualTableIdKey);
				if (virtualTableIntervalIdKeyList != null) {
					List<VirtualTableInterval> virtualTableIntervalList = new ArrayList<>();
					for (String virtualTableIntervalIdKey : virtualTableIntervalIdKeyList) {
						VirtualTableInterval virtualTableInterval = xMemcachedClient.get(virtualTableIntervalIdKey);
						if (virtualTableInterval != null)
							virtualTableIntervalList.add(virtualTableInterval);
						else
							break;
						
					}
					if (virtualTableIntervalList.size() == virtualTableIntervalIdKeyList.size()) {
						return virtualTableIntervalList; 
					}
				}
				String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
				if (memcachedLock.tryLock(virtualTableIdKeyLock)) {
					try {
						List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
						virtualTableIntervalIdKeyList = new ArrayList<>();
						for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
							String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
							virtualTableIntervalIdKeyList.add(virtualTableIntervalIdKey);
							xMemcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
						}
						xMemcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
						return virtualTableIntervals;
					} finally {
						memcachedLock.unlock(virtualTableIdKeyLock);;
					}
				} else {
					Thread.sleep(500);
					return doInXMemcached(xMemcachedClient);
				}
			}
		});
	}

}
