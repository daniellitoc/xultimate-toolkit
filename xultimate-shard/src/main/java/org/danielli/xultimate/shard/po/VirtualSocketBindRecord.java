package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟表的数据划分区间绑定记录。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualSocketBindRecord implements Serializable {
	private static final long serialVersionUID = -8122591072202388687L;
	
	/** 虚拟Socket ID */
	private Long virtualSocketId;
	/** 虚拟表区间ID */
	private Long virtualTableIntervalId;
	/** 创建时间 */
	private Date createTime;

	public Long getVirtualTableIntervalId() {
		return virtualTableIntervalId;
	}

	public void setVirtualTableIntervalId(Long virtualTableIntervalId) {
		this.virtualTableIntervalId = virtualTableIntervalId;
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
