package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

import org.danielli.xultimate.util.Assert;

/**
 * 分区表的数据划分区间。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class PartitionedTableInterval implements Serializable {
	private static final long serialVersionUID = 3955513576617426427L;
	
	/** 标识 */
	private Long id;
	/** 分区表ID */
	private Long partitionedTableId;
	/** 开始区间 */
	private Long startInterval;
	/** 结束区间 */
	private Long endInterval;
	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPartitionedTableId() {
		return partitionedTableId;
	}

	public void setPartitionedTableId(Long partitionedTableId) {
		this.partitionedTableId = partitionedTableId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getStartInterval() {
		return startInterval;
	}

	public void setStartInterval(Long startInterval) {
		this.startInterval = startInterval;
	}

	public Long getEndInterval() {
		return endInterval;
	}

	public void setEndInterval(Long endInterval) {
		this.endInterval = endInterval;
	}
	
	public boolean betweenStartIntervalAndEndInterval(Long intervalValue) {
		Assert.notNull(intervalValue, "this argument 'intervalValue' is required; it must not be null");
		return intervalValue.compareTo(startInterval) >= 0 && intervalValue.compareTo(endInterval) < 0;
	}
}
