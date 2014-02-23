package org.danielli.xultimate.orm.jpa.id;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 版本实体，包含version属性。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@MappedSuperclass
@Cacheable(true)
public abstract class VersionEntity<T> extends IDEntity<T> {
	private static final long serialVersionUID = 479107963083635365L;

	protected Long version;  // 版本号
	
	@Version
	@Column(name = "entity_version")
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
}
