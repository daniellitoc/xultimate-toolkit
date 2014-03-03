package org.danielli.xultimate.orm.mybatis.area.service;

import java.util.List;

import org.danielli.xultimate.orm.mybatis.ds.Item;
import org.danielli.xultimate.orm.mybatis.po.Area;

public interface AreaService {
	
	void save(Area entity);
	
	void update(Area entity);
	
	void delete(Area entity);
	
	Area findOne(Long id);
	
	List<Area> findAll();
	
	public void testRollback(Area area);
	
	void noSupportTransaction(Area area);
	
	List<Area> findByItems(List<Item<? extends Object>> items);
	
	
	List<Area> findAllByReadOnly();
	List<Area> findAllByNotSupport();
	List<Area> findAllByTransaction();
	
}
