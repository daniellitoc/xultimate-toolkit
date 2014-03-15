package org.danielli.xultimate.shard.mybatis.biz;

import org.danielli.xultimate.shard.po.VirtualTable;

/**
 * 虚拟表Biz。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface VirtualTableBiz {
	
	/**
	 * 获取虚拟表。
	 * @param virtualDatabaseName 虚拟数据库名称。
	 * @param name 虚拟表的名称。
	 * @return 虚拟表。
	 */
	VirtualTable findOneByVirtualDatabaseNameAndName(String virtualDatabaseName, String name);
}
