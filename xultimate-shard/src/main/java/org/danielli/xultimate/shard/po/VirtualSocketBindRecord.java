package org.danielli.xultimate.shard.po;

import java.io.Serializable;
import java.util.Date;

import org.danielli.xultimate.core.json.JsonUtils;
import org.danielli.xultimate.core.json.ValueType;

/**
 * 虚拟表的数据划分区间绑定记录。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VirtualSocketBindRecord implements Serializable {
	private static final long serialVersionUID = -8122591072202388687L;
	
	/** 标识 */
	private Long id;
	/** 虚拟Socket ID */
	private Long virtualSocketId;
	/** 虚拟表区间ID */
	private Long virtualTableIntervalId;
	/** 创建时间 */
	private Date createTime;
	/** HASH值的JSON字符串 */
	private String hashValuesJson;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
