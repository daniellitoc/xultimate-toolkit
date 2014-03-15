package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟Socket。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualSocket implements Serializable {
	private static final long serialVersionUID = -430452214732789530L;
	
	/** 标识 */
	private Long id;
	/** 虚拟Socket地址，包含了IP和端口 */
	private String address;
	/** 创建时间 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
