package org.danielli.xultimate.orm.jpa.area.po;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;

public class AreaEntityListener {
	
	@PostPersist
	private void test2(Area area) {
		System.out.println("postPersist");
		area.setName("北京2");
		area.setDisplayName(area.getName());
	}
	
	@PreUpdate
	private void test3(Area area) {
		// 前置处理应用与改变属性。
		System.out.println("preUpdate");
		area.setName("北京3");
		area.setDisplayName(area.getName());
	}
	
	@PostUpdate
	private void test4(Area area) {
		// 后处理改变属性无法同步到数据库。
		System.out.println("postUpdate");
		area.setName("北京4");
		area.setDisplayName(area.getName());
	}
}
