package org.danielli.xultimate.shard.mybatis.dao;

import org.danielli.xultimate.orm.mybatis.MyBatisRepository;
import org.danielli.xultimate.shard.po.VirtualTable;

/**
 * 虚拟表DAO。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
@MyBatisRepository
public interface VirtualTableDAO {
	
	/**
	 * 获取虚拟表。
	 * @param virtualDatabaseName 虚拟数据库名称。
	 * @param name 虚拟表的名称。
	 * @return 虚拟表。
	 */
	VirtualTable findOneByVirtualDatabaseNameAndName(String virtualDatabaseName, String name);
}
