package org.danielli.xultimate.orm.jpa.area.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.area.biz.AreaBiz;
import org.danielli.xultimate.orm.jpa.area.po.Area;
import org.danielli.xultimate.orm.jpa.area.service.AreaService;
import org.danielli.xultimate.orm.jpa.ds.Item;
import org.danielli.xultimate.orm.jpa.ds.ItemsSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("areaServiceImpl")
@Transactional
public class AreaServiceImpl implements AreaService {
	
	@Resource(name = "areaBizImpl")
	private AreaBiz areaBiz;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll() {
		return areaBiz.findAll();
	}

	public void testRollback(Area area) {
		areaBiz.save(area);
		throw new RuntimeException("abc");
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void noSupportTransaction(Area area) {
		areaBiz.save(area);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findByItems(List<Item<? extends Object>> items) {
		return areaBiz.findAll(new ItemsSpecification<Area>(items));
	}

	@Override
	public Area save(Area entity) {
		return areaBiz.save(entity);
	}

	@Override
	public Iterable<Area> save(Iterable<Area> entities) {
		return areaBiz.save(entities);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Area findOne(Long id) {
		return areaBiz.findOne(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean exists(Long id) {
		return areaBiz.exists(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll(Iterable<Long> ids) {
		return areaBiz.findAll(ids);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public long count() {
		return areaBiz.count();
	}

	@Override
	public void delete(Long id) {
		areaBiz.delete(id);
	}

	@Override
	public void delete(Area entity) {
		areaBiz.delete(entity);
	}

	@Override
	public void delete(Iterable<Area> entities) {
		areaBiz.delete(entities);
	}

	@Override
	public void deleteAll() {
		areaBiz.deleteAll();
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll(Sort sort) {
		return areaBiz.findAll(sort);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Area> findAll(Pageable pageable) {
		return areaBiz.findAll(pageable);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll(Specification<Area> spec) {
		return areaBiz.findAll(spec);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll(Specification<Area> spec, Sort sort) {
		return areaBiz.findAll(spec, sort);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Area> findAll(Specification<Area> spec, Pageable pageable) {
		return areaBiz.findAll(spec, pageable);
	}
}
