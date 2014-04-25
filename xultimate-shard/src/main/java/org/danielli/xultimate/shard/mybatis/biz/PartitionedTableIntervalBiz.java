package org.danielli.xultimate.shard.mybatis.biz;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 分区表的数据划分区间Biz。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface PartitionedTableIntervalBiz {

	/**
	 * 获取分区表的数据划分区间信息列表。
	 * @param virtualTableIntervalIdList 虚拟表区间ID列表。
	 * @return 分区表的数据划分区间信息列表。
	 */
	List<Map<String, Object>> findInfosByVirtualTableIdAndVirtualSocketIdSet(Long virtualTableId, Set<Long> virtualSocketIdSet);
}
