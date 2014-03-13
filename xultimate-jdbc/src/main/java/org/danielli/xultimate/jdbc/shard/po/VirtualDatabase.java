package org.danielli.xultimate.jdbc.shard.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟数据库。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualDatabase implements Serializable {
	private static final long serialVersionUID = 2222557352100341183L;
	/** 标识 */
	private Long id;				
	/** 虚拟数据库的名称 */
	private String name;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
