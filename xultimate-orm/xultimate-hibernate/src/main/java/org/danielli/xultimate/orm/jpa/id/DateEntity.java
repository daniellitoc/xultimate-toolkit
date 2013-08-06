package org.danielli.xultimate.orm.jpa.id;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 日期实体，包含createDate和modifyDate属性，添加和更新实体时会自动处理时间问题。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@MappedSuperclass
@Cacheable(true)
public abstract class DateEntity extends VersionEntity {
	private static final long serialVersionUID = -790961194590976268L;
	
	protected Date createDate;  // 创建日期
	protected Date modifyDate;  // 修改日期
	
	@Column(updatable = false)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getModifyDate() {
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@PrePersist
	public void defaultPrePersist() {
		if (this.createDate == null) {
			createDate = new Date();
		}
		if (this.modifyDate == null) {
			modifyDate = createDate;
		}
	}
	
	@PreUpdate
	public void defaultPreUpdate() {
		modifyDate = new Date();
	}

}
