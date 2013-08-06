package org.danielli.xultimate.orm.mybatis.area.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.mybatis.area.biz.AreaBiz;
import org.danielli.xultimate.orm.mybatis.area.model.Area;
import org.danielli.xultimate.orm.mybatis.area.service.AreaService;
import org.danielli.xultimate.orm.mybatis.ds.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("areaServiceImpl")
public class AreaServiceImpl implements AreaService {
	
	@Resource(name = "areaBizImpl")
	private AreaBiz areaBiz;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Area entity) {
		areaBiz.save(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Area entity) {
		areaBiz.update(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Area entity) {
		areaBiz.delete(entity);
	}
	
	@Override
	public List<Area> findAll() {
		return areaBiz.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void testRollback(Area area) {
		areaBiz.save(area);
		throw new RuntimeException("abc");
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void noSupportTransaction(Area area) {
		areaBiz.save(area);
	}

	@Override
	public List<Area> findByItems(List<Item<? extends Object>> items) {
		return areaBiz.findByItems(items);
	}

	@Override
	public Area findOne(Long id) {
		return areaBiz.findOne(id);
	}
}
