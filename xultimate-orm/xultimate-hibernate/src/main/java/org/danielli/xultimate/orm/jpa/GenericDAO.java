package org.danielli.xultimate.orm.jpa;

import java.io.Serializable;
import java.util.List;

import org.danielli.xultimate.orm.jpa.id.IDEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 通用数据访问接口。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 * @param <T> 操作对象类型。
 * @param <ID> 操作对象主键类型。
 */
public interface GenericDAO<T extends IDEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
	
	List<T> findAll(Specification<T> spec);
    
	List<T> findAll(Specification<T> spec, Sort sort);
    
	Page<T> findAll(Specification<T> spec, Pageable pageable);
}
