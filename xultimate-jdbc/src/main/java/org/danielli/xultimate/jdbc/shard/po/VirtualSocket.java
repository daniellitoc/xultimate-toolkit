package org.danielli.xultimate.jdbc.shard.po;

import java.io.Serializable;
import java.util.Date;

import org.danielli.xultimate.core.json.JsonUtils;
import org.danielli.xultimate.core.json.ValueType;

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
	/** HASH值的JSON字符串 */
	private String hashValuesJson;
	/** 虚拟表区间ID */
	private Long virtualTableIntervalId;
	/** 创建时间 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getHashValuesJson() {
		return hashValuesJson;
	}
	
	public void setHashValuesJson(String hashValuesJson) {
		this.hashValuesJson = hashValuesJson;
	}
	
	public Integer[] getHashValues() {
		return JsonUtils.readValue(hashValuesJson, new ValueType<Integer[]>() {});
	}
	
	public void setHashValues(Integer[] hashValues) {
		this.hashValuesJson = JsonUtils.writeValueAsString(hashValues);
	}

	public Long getVirtualTableIntervalId() {
		return virtualTableIntervalId;
	}

	public void setVirtualTableIntervalId(Long virtualTableIntervalId) {
		this.virtualTableIntervalId = virtualTableIntervalId;
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
