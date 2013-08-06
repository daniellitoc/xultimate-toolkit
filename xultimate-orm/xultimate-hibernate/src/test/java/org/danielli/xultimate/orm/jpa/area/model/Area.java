package org.danielli.xultimate.orm.jpa.area.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;

import org.danielli.xultimate.orm.jpa.id.NormsEntity;
import org.danielli.xultimate.util.builder.Buildable;
import org.hibernate.annotations.ForeignKey;

@Entity
@EntityListeners(value = { AreaEntityListener.class})
public class Area extends NormsEntity {

	private static final long serialVersionUID = -2158109459123036967L;

	private String name;// 名称
	private String displayName;// 完整地区名称
	
	@Buildable()
	private Area parent;// 上级地区
	
	@Buildable()
	private Set<Area> children = new HashSet<Area>();// 下级地区

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, length = 3000)
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_area_parent")
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("boost asc")
	public Set<Area> getChildren() {
		return children;
	}

	public void setChildren(Set<Area> children) {
		this.children = children;
	}
	
	@PrePersist
	private void test1() {
		System.out.println("prePersist");
		this.setName("北京1");
		this.setDisplayName(this.getName());
	}
}