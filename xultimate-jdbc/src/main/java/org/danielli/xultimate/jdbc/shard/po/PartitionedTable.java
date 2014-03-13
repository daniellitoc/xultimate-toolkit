package org.danielli.xultimate.jdbc.shard.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 物理分区表。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class PartitionedTable implements Serializable {
	private static final long serialVersionUID = 5256793836881573L;
	
	/** 标识 */
	private Long id;
	/** 虚拟Socket ID */
	private Long virtualSocketId;
	/** 表分区ID */
	private Long shardId;
	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShardId() {
		return shardId;
	}

	public void setShardId(Long shardId) {
		this.shardId = shardId;
	}

	public Long getVirtualSocketId() {
		return virtualSocketId;
	}

	public void setVirtualSocketId(Long virtualSocketId) {
		this.virtualSocketId = virtualSocketId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
