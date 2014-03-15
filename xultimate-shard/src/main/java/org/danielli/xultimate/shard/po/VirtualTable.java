package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟表。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualTable implements Serializable {

	private static final long serialVersionUID = 8833323683324133706L;
	/** 标识 */
	private Long id;
	/** 虚拟表的名称 */
	private String name;
	/** 虚拟数据库ID */
	private Long virtualDatabaseId;
	/** 创建时间 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Long getVirtualDatabaseId() {
		return virtualDatabaseId;
	}

	public void setVirtualDatabaseId(Long virtualDatabaseId) {
		this.virtualDatabaseId = virtualDatabaseId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
