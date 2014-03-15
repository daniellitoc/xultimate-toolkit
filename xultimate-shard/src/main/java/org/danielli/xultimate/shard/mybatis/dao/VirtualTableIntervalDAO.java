package org.danielli.xultimate.shard.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.danielli.xultimate.orm.mybatis.MyBatisRepository;
import org.danielli.xultimate.shard.po.VirtualTableInterval;

/**
 * 虚拟表的数据划分区间DAO。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
@MyBatisRepository
public interface VirtualTableIntervalDAO {

	/**
	 * 获取虚拟表的数据划分区间列表。
	 * @param virtualTableId 虚拟表ID。
	 * @return 虚拟表的数据划分区间列表。
	 */
	List<VirtualTableInterval> findByVirtualTableId(Long virtualTableId);
	
	/**
	 * 更新是否可用。
	 * @param id 虚拟表的数据划分区间ID。
	 * @param available 是否可用。
	 * @param updateTime 更新时间。
	 */
	void updateAvailableById(Long id, boolean available, Date updateTime);
	
	/**
	 * 获取虚拟表的数据划分区间列表。
	 * @param virtualDatabaseName 虚拟数据库名称。
	 * @param virtualTableName 虚拟表的名称。
	 * @return 虚拟表的数据划分区间列表。
	 */
	List<VirtualTableInterval> findByVirtualDatabaseNameAndVirtualTableName(String virtualDatabaseName, String virtualTableName);
}
