package org.danielli.xultimate.orm.mybatis.area.biz;

import java.util.List;
import java.util.Map;

import org.danielli.xultimate.orm.mybatis.area.model.Area;
import org.danielli.xultimate.orm.mybatis.ds.Item;

public interface AreaBiz {
	
	void save(Area entity);
	
	void update(Area entity);
	
	void delete(Area entity);
	
	Long findIdByName(String name);
	
	Map<Long, Area> findIdAndNameMap();
	
	Map<String, Object> findOneMap(Long id);
	
	Area findOne(Long id);
	
	List<Area> findAll();

    Long count();
    
    List<Area> findByItems(List<Item<? extends Object>> items);
}
