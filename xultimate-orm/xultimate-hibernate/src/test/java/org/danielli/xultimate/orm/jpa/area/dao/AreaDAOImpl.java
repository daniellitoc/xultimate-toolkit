package org.danielli.xultimate.orm.jpa.area.dao;

import org.danielli.xultimate.orm.jpa.area.model.Area;
import org.springframework.stereotype.Repository;

/**
 * 此注解在Spring Data JPA中，只能由jpa:repositories解析，并且通过定义为areaRepositoryImpl，也会被忽略，使用areaDAOImpl
 */
@Repository("areaDAOImpl") 
public class AreaDAOImpl implements AreaDAOExt {
	
	@Override
	public void execCustomMethod(Area area) {
		System.out.println("This is a custom Method");
	}
}
