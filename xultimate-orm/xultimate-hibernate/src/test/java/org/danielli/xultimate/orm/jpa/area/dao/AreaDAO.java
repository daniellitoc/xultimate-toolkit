package org.danielli.xultimate.orm.jpa.area.dao;

import java.util.List;

import org.danielli.xultimate.orm.jpa.GenericDAO;
import org.danielli.xultimate.orm.jpa.area.domain.Area;

public interface AreaDAO extends GenericDAO<Area, Long>, AreaDAOExt {
    
    List<Area> findByName(String name);
    
}