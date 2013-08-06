package org.danielli.xultimate.orm.mybatis.area.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;
import org.danielli.xultimate.orm.mybatis.area.model.Area;
import org.danielli.xultimate.orm.mybatis.ds.Item;

@MyBatisRepository
public interface AreaDAO {
	
	int save(Area entity);
	
	int update(Area entity);
	
	int delete(Area entity);
	
	Long findIdByName(String name);
	
	@MapKey("id")
	Map<Long, Area> findIdAndNameMap();
	
	Map<String, Object> findOneMap(Long id);
	
	Area findOne(Long id);
	
	List<Area> findAll();

    @Select("select count(id) from ULTIMATE_AREA")
    Long count();
    
    List<Area> findByItems(List<Item<? extends Object>> items);
}
