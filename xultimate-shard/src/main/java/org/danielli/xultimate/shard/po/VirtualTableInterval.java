package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.Range;

/**
 * 虚拟表的数据划分区间。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualTableInterval implements Serializable {
	private static final long serialVersionUID = 3483662801839739228L;
	
	/** 标识 */
	private Long id;
	/** 虚拟表ID */
	private Long virtualTableId;
	/** 开始区间 */
	private Long startInterval;
	/** 结束区间 */
	private Long endInterval;
	/** 是否可用 */
	private Boolean available;
	/** HASH值的个数 */
	private Integer hashValuesCount;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getVirtualTableId() {
		return virtualTableId;
	}
	
	public void setVirtualTableId(Long virtualTableId) {
		this.virtualTableId = virtualTableId;
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
	
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public boolean betweenStartIntervalAndEndInterval(Long intervalValue) {
//		Assert.notNull(intervalValue, "this argument 'intervalValue' is required; it must not be null");
//		return intervalValue.compareTo(startInterval) >= 0 && intervalValue.compareTo(endInterval) < 0;
		Range<Long> range = Range.between(startInterval, endInterval);
		return range.contains(intervalValue);
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getHashValuesCount() {
		return hashValuesCount;
	}

	public void setHashValuesCount(Integer hashValuesCount) {
		this.hashValuesCount = hashValuesCount;
	}
}
