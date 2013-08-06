package org.danielli.xultimate.orm.jpa.area.service;

import java.util.List;

import org.danielli.xultimate.orm.jpa.GenericService;
import org.danielli.xultimate.orm.jpa.area.model.Area;
import org.danielli.xultimate.orm.jpa.ds.Item;

public interface AreaService extends GenericService<Area, String> {
	
	void testRollback(Area area);
	
	void noSupportTransaction(Area area);
	
	List<Area> findByItems(List<Item<? extends Object>> items);
	
}
