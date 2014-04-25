package org.danielli.xultimate.shard.mybatis.biz;

import java.util.List;

import org.danielli.xultimate.shard.po.VirtualSocketBindRecord;

/**
 * 虚拟表的数据划分区间绑定记录Biz。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface VirtualSocketBindRecordBiz {
	
	/**
	 * 获取虚拟表的数据划分区间绑定记录列表。
	 * @param virtualTableIntervalIdList 虚拟表区间ID列表。
	 * @return 虚拟表的数据划分区间绑定记录列表。
	 */
	List<VirtualSocketBindRecord> findByVirtualTableIntervalIdList(List<Long> virtualTableIntervalIdList);
}
