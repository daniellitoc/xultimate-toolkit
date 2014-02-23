package org.danielli.xultimate.orm.jpa.area.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.DefaultGenericBiz;
import org.danielli.xultimate.orm.jpa.GenericDAO;
import org.danielli.xultimate.orm.jpa.area.biz.AreaBiz;
import org.danielli.xultimate.orm.jpa.area.dao.AreaDAO;
import org.danielli.xultimate.orm.jpa.area.po.Area;
import org.springframework.stereotype.Service;

@Service("areaBizImpl")
public class AreaBizImpl extends DefaultGenericBiz<Area, Long> implements AreaBiz {

	@Resource(name = "areaDAO")
	private AreaDAO areaDAO;
	
	@Override
	@Resource(name = "areaDAO")
	public void setGenericDAO(GenericDAO<Area, Long> genericDAO) {
		super.setGenericDAO(genericDAO);
	}
}
