package org.danielli.xultimate.orm.jpa.area.dao;

import java.util.List;

import org.danielli.xultimate.orm.jpa.GenericDAO;
import org.danielli.xultimate.orm.jpa.area.domain.Area;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AreaDAO extends GenericDAO<Area, Long>, AreaDAOExt {
    
    List<Area> findByName(String name);
    
    @Query("FROM Area WHERE name = :name")
    List<Area> findByQuery(@Param("name") String name, Pageable pageable);
    
}