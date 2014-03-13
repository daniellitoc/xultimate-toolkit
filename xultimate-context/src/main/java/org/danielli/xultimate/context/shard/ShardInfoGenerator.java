package org.danielli.xultimate.context.shard;

import java.util.Collection;

import org.danielli.xultimate.context.shard.domain.ShardInfo;

/**
 * Shard信息生成器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface ShardInfoGenerator {

	/**
	 * 创建Shard信息。
	 * @param virtualDatabaseName 虚拟数据库的名称。
	 * @param virtualTableName 虚拟表的名称。
	 * @param intervalValue 区间值。
	 * @return Shard信息。
	 */
	ShardInfo createShardInfo(String virtualDatabaseName, String virtualTableName, Long intervalValue);
	
	/**
	 * 创建Shard信息集合。
	 * @param virtualDatabaseName 虚拟数据库的名称。
	 * @param virtualTableName 虚拟表的名称。
	 * @return Shard信息集合。
	 */
	Collection<ShardInfo> createShardInfos(String virtualDatabaseName, String virtualTableName);
	
	
}
