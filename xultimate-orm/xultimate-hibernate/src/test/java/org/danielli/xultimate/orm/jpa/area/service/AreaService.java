package org.danielli.xultimate.orm.jpa.area.service;

import java.util.List;

import org.danielli.xultimate.orm.jpa.area.domain.Area;
import org.danielli.xultimate.orm.jpa.ds.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface AreaService {
	
	void testRollback(Area area);
	
	void noSupportTransaction(Area area);
	
	List<Area> findByItems(List<Item<? extends Object>> items);
	
	Area save(Area entity);
	
	Iterable<Area> save(Iterable<Area> entities);
	
	Area findOne(Long id);
	
	boolean exists(Long id);
	
	List<Area> findAll();
	
	List<Area> findAll(Iterable<Long> ids);
	
	long count();
	
	void delete(Long id);
	
	void delete(Area entity);
	
	void delete(Iterable<Area> entities);
	
	void deleteAll();
	
	List<Area> findAll(Sort sort);
	
	Page<Area> findAll(Pageable pageable);
	
	List<Area> findAll(Specification<Area> spec);
    
	List<Area> findAll(Specification<Area> spec, Sort sort);
    
	Page<Area> findAll(Specification<Area> spec, Pageable pageable);
}
