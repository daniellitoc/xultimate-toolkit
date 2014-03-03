package org.danielli.xultimate.orm.mybatis.po;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;

public class Area implements Serializable {
	
	private static final long serialVersionUID = 4844782128242497302L;

	private Long id;
	
	private Long poVersion;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Float boost;
	
	private String displayName;// 完整地区名称
	
	private String name;// 名称

	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoVersion() {
		return poVersion;
	}

	public void setPoVersion(Long poVersion) {
		this.poVersion = poVersion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public void setCreateTimeToCurrent() {
		this.createTime = new DateTime().toDate();;
	}
	
	public void setUpdateTimeToCurrent() {
		this.updateTime = new DateTime().toDate();;
	}
	
	public void clearPoVersion() {
		this.poVersion = 0L;
	}
	
	public void clearBoost() {
		this.boost = 0f;
	}
	
	public Long getNextPoVersion() {
		return poVersion + 1;
	}
}
