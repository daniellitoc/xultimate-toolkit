package org.danielli.xultimate.shard.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.danielli.xultimate.orm.mybatis.MyBatisRepository;


/**
 * 分区表的数据划分区间DAO。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
@MyBatisRepository
public interface PartitionedTableIntervalDAO {
	
	/**
	 * 获取分区表的数据划分区间信息列表。
	 * @param virtualTableIntervalIdList 虚拟表区间ID列表。
	 * @return 分区表的数据划分区间信息列表。
	 */
	List<Map<String, Object>> findPartitionedTableIntervalInfosByvirtualTableIntervalIdList(List<Long> virtualTableIntervalIdList);
}
