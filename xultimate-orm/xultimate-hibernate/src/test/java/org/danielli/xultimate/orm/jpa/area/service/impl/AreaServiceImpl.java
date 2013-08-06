package org.danielli.xultimate.orm.jpa.area.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.DefaultGenericService;
import org.danielli.xultimate.orm.jpa.GenericDAO;
import org.danielli.xultimate.orm.jpa.area.dao.AreaDAO;
import org.danielli.xultimate.orm.jpa.area.model.Area;
import org.danielli.xultimate.orm.jpa.area.service.AreaService;
import org.danielli.xultimate.orm.jpa.ds.Item;
import org.danielli.xultimate.orm.jpa.ds.ItemsSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("areaServiceImpl")
@Transactional
public class AreaServiceImpl extends DefaultGenericService<Area, String> implements AreaService {
	
	@Resource(name = "areaDAO")
	private AreaDAO areaDAO;
	
	@Override
	@Resource(name = "areaDAO")
	public void setGenericDAO(GenericDAO<Area, String> genericDAO) {
		super.setGenericDAO(genericDAO);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Area> findAll() {
		return super.findAll();
	}

	public void testRollback(Area area) {
		areaDAO.save(area);
		throw new RuntimeException("abc");
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void noSupportTransaction(Area area) {
		areaDAO.save(area);
	}

	@Override
	public List<Area> findByItems(List<Item<? extends Object>> items) {
		return areaDAO.findAll(new ItemsSpecification<Area>(items));
	}

}
