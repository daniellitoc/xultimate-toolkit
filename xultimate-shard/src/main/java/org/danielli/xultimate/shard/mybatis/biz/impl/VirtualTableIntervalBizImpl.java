package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientMutex;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualTableIntervalBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualTableIntervalDAO;
import org.danielli.xultimate.shard.po.VirtualTableInterval;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualTableIntervalBizImpl")
public class VirtualTableIntervalBizImpl implements VirtualTableIntervalBiz {

	@Resource(name = "virtualTableIntervalDAO")
	private VirtualTableIntervalDAO virtualTableIntervalDAO;
	
	@Resource(name = "xMemcachedTemplate")
	private XMemcachedTemplate xMemcachedTemplate;
	
	@Resource(name = "memcachedClientMutex")
	private MemcachedClientMutex memcachedClientMutex;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;
	
//	@Override
//	public List<VirtualTableInterval> findByVirtualTableId(final Long virtualTableId) {
//		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<VirtualTableInterval>>() {
//			@Override
//			public List<VirtualTableInterval> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
//				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualTableId:{0}", virtualTableId);
//				List<Long> virtualTableIntervalIdKeyList = memcachedClient.get(virtualTableIdKey);
//				if (virtualTableIntervalIdKeyList == null) {
//					String virtualTableIdKeyLock = FormatterUtils.format("{0}.lock", virtualTableIdKey);
//					if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIdKeyLock)) {
//						try {
//							List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualTableId(virtualTableId);
//							virtualTableIntervalIdKeyList = new ArrayList<>();
//							for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
//								virtualTableIntervalIdKeyList.add(virtualTableInterval.getId());
//								String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
//								memcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
//							}
//							memcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
//							return virtualTableIntervals;
//						} finally {
//							memcachedClientMutex.unlock(memcachedClient, virtualTableIdKeyLock);;
//						}
//					} else {
//						Thread.sleep(500);
//						return doInXMemcached(memcachedClient);
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
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<VirtualTableInterval>>() {
			@Override
			public List<VirtualTableInterval> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualTableId:{0}", virtualTableId);
				List<String> virtualTableIntervalIdKeyList = memcachedClient.get(virtualTableIdKey);
				if (virtualTableIntervalIdKeyList != null) {
					List<VirtualTableInterval> virtualTableIntervalList = new ArrayList<>();
					for (String virtualTableIntervalIdKey : virtualTableIntervalIdKeyList) {
						VirtualTableInterval virtualTableInterval = memcachedClient.get(virtualTableIntervalIdKey);
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
				if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIdKeyLock)) {
					try {
						List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualTableId(virtualTableId);
						virtualTableIntervalIdKeyList = new ArrayList<>();
						for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
							String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
							virtualTableIntervalIdKeyList.add(virtualTableIntervalIdKey);
							memcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
						}
						memcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
						return virtualTableIntervals;
					} finally {
						memcachedClientMutex.unlock(memcachedClient, virtualTableIdKeyLock);;
					}
				} else {
					Thread.sleep(500);
					return doInXMemcached(memcachedClient);
				}
			}
		});
	}

	@Override
	public void updateAvailableById(final Long id, final boolean available) {
		final DateTime currentTime = new DateTime();
		virtualTableIntervalDAO.updateAvailableById(id, available, currentTime.toDate());
		
		xMemcachedTemplate.execute(new XMemcachedCallback() {
			@Override
			public void doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", id);
				VirtualTableInterval virtualTableInterval = memcachedClient.get(virtualTableIntervalIdKey);
				if (virtualTableInterval != null) {
					virtualTableInterval.setAvailable(available);;
					virtualTableInterval.setUpdateTime(currentTime.toDate());
					memcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
				}
			}
		});
	}
	
	@Override
	public List<VirtualTableInterval> findByVirtualDatabaseNameAndVirtualTableName(final String virtualDatabaseName, final String virtualTableName) {
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<VirtualTableInterval>>() {
			@Override
			public List<VirtualTableInterval> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIdKey = FormatterUtils.format("VirtualTableInterval:virtualDatabaseName:{0}:virtualTableName:{1}", virtualDatabaseName, virtualTableName);
				List<String> virtualTableIntervalIdKeyList = memcachedClient.get(virtualTableIdKey);
				if (virtualTableIntervalIdKeyList != null) {
					List<VirtualTableInterval> virtualTableIntervalList = new ArrayList<>();
					for (String virtualTableIntervalIdKey : virtualTableIntervalIdKeyList) {
						VirtualTableInterval virtualTableInterval = memcachedClient.get(virtualTableIntervalIdKey);
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
				if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIdKeyLock)) {
					try {
						List<VirtualTableInterval> virtualTableIntervals = virtualTableIntervalDAO.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
						virtualTableIntervalIdKeyList = new ArrayList<>();
						for (VirtualTableInterval virtualTableInterval : virtualTableIntervals) {
							String virtualTableIntervalIdKey = FormatterUtils.format("VirtualTableInterval:id:{0}", virtualTableInterval.getId());
							virtualTableIntervalIdKeyList.add(virtualTableIntervalIdKey);
							memcachedClient.set(virtualTableIntervalIdKey, expireSeconds, virtualTableInterval);
						}
						memcachedClient.set(virtualTableIdKey, expireSeconds, virtualTableIntervalIdKeyList);
						return virtualTableIntervals;
					} finally {
						memcachedClientMutex.unlock(memcachedClient, virtualTableIdKeyLock);;
					}
				} else {
					Thread.sleep(500);
					return doInXMemcached(memcachedClient);
				}
			}
		});
	}

}
