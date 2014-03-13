package org.danielli.xultimate.context.shard.domain;

import java.io.Serializable;

import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.builder.BuildType;
import org.danielli.xultimate.util.builder.Buildable;
import org.danielli.xultimate.util.builder.EqualsBuilderUtils;
import org.danielli.xultimate.util.builder.HashCodeBuilderUtils;

/**
 * Shard信息。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
@Buildable({ BuildType.EQUALS, BuildType.HASH_CODE })
public class ShardInfo implements Serializable {
	private static final long serialVersionUID = 7937016014755341278L;
	
	public static final String SOCKET_ADDRESS_SPLIT = ":";
	
	public static final String TABLE_SPLIT = "_";
	
	/** 虚拟Socket地址 */
	private String virtualSocketAddress;
	/** 分区表Shard ID*/
	private Long partitionedTableShardId;

	public String getVirtualSocketAddress() {
		return virtualSocketAddress;
	}

	public void setVirtualSocketAddress(String virtualSocketAddress) {
		this.virtualSocketAddress = virtualSocketAddress;
	}

	public Long getPartitionedTableShardId() {
		return partitionedTableShardId;
	}

	public void setPartitionedTableShardId(Long partitionedTableShardId) {
		this.partitionedTableShardId = partitionedTableShardId;
	}
	
	/**
	 * 获取分区表名称。
	 * 
	 * @param virtualTableName 虚拟表名称。
	 * @return 分区表名称。
	 */
	public String getPartitionedTableName(String virtualTableName) {
		Assert.hasLength(virtualTableName, "this String argument 'virtualTableName' must have length; it must not be null or empty");
		return virtualTableName + TABLE_SPLIT + partitionedTableShardId;
	}
	
	/**
	 * 获取虚拟路由数据源KEY。
	 * @param virtualDatabaseName 虚拟数据库名称。
	 * @return 虚拟路由数据源KEY。
	 */
	public String getVirtualRoutingDataSourceKey(String virtualDatabaseName) {
		Assert.hasLength(virtualDatabaseName, "this String argument 'virtualDatabaseName' must have length; it must not be null or empty");
		return virtualSocketAddress + SOCKET_ADDRESS_SPLIT + virtualDatabaseName;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilderUtils.reflectionEqualsForBothClass(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilderUtils.reflectionHashCode(this);
	}
}
