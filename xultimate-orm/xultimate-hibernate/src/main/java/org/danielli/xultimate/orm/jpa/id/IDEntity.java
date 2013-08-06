package org.danielli.xultimate.orm.jpa.id;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.danielli.xultimate.util.builder.BuildType;
import org.danielli.xultimate.util.builder.Buildable;
import org.danielli.xultimate.util.builder.EqualsBuilderUtils;
import org.danielli.xultimate.util.builder.HashCodeBuilderUtils;
import org.danielli.xultimate.util.builder.ToStringBuilderUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * ID实体，包含id属性。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@MappedSuperclass
@Cacheable(true)
@Buildable({BuildType.EQUALS, BuildType.HASH_CODE, BuildType.TO_STRING, BuildType.COMPARE_TO })
public abstract class IDEntity implements Serializable {

	private static final long serialVersionUID = -7827915318061596308L;

	@Buildable({BuildType.EQUALS, BuildType.HASH_CODE, BuildType.COMPARE_TO})
	protected String id;  // ID
	
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		// 通过使用注解确定对象同一性
		return EqualsBuilderUtils.reflectionEqualsForBothClass(this, obj);
	}
	
	@Override
	public int hashCode() {
		// 通过使用注解确定对象同一性
		return HashCodeBuilderUtils.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilderUtils.reflectionToString(this);
	}
}
