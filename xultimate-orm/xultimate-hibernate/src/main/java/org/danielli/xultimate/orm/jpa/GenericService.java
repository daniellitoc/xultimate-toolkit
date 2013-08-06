package org.danielli.xultimate.orm.jpa;

import java.io.Serializable;
import java.util.List;

import org.danielli.xultimate.orm.jpa.id.IDEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface GenericService<T extends IDEntity, ID extends Serializable> {
	
	<S extends T> S save(S entity);
	
	<S extends T> Iterable<S> save(Iterable<S> entities);
	
	T findOne(ID id);
	
//	T getOne(ID id);
	
	boolean exists(ID id);
	
	List<T> findAll();
	
	List<T> findAll(Iterable<ID> ids);
	
	long count();
	
	void delete(ID id);
	
	void delete(T entity);
	
	void delete(Iterable<? extends T> entities);
	
	void deleteAll();
	
	List<T> findAll(Sort sort);
	
	Page<T> findAll(Pageable pageable);
	
	List<T> findAll(Specification<T> spec);
    
	List<T> findAll(Specification<T> spec, Sort sort);
    
	List<T> findAll(Specification<T> spec, Pageable pageable);
}
