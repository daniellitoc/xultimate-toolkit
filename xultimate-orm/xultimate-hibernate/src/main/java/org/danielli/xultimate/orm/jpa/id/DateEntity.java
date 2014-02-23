package org.danielli.xultimate.orm.jpa.id;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 日期实体，包含createDate和updateTime属性，添加和更新实体时会自动处理时间问题。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@MappedSuperclass
@Cacheable(true)
public abstract class DateEntity<T> extends VersionEntity<T> {
	private static final long serialVersionUID = -790961194590976268L;
	
	protected Date createDate;  // 创建日期
	protected Date updateTime;  // 修改日期
	
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@PrePersist
	public void defaultPrePersist() {
		if (this.createDate == null) {
			createDate = new Date();
		}
		if (this.updateTime == null) {
			updateTime = createDate;
		}
	}
	
	@PreUpdate
	public void defaultPreUpdate() {
		updateTime = new Date();
	}
}
