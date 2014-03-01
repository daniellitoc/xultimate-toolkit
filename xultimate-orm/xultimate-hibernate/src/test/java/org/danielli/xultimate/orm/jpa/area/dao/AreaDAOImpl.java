package org.danielli.xultimate.orm.jpa.area.dao;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.area.domain.Area;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * 此注解在Spring Data JPA中，只能由jpa:repositories解析，并且通过定义为areaRepositoryImpl，也会被忽略，使用areaDAOImpl
 */
@Repository("areaDAOImpl") 
public class AreaDAOImpl implements AreaDAOExt {
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void execCustomMethod(Area area) {
		System.out.println("This is a custom Method");
	}
}
