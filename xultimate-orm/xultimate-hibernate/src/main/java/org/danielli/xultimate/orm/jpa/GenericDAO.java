package org.danielli.xultimate.orm.jpa;

import java.io.Serializable;
import java.util.List;

import org.danielli.xultimate.orm.jpa.id.IDEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericDAO<T extends IDEntity, ID extends Serializable> extends JpaRepository<T, ID> {
	
	List<T> findAll(Specification<T> spec);
    
	List<T> findAll(Specification<T> spec, Sort sort);
    
	List<T> findAll(Specification<T> spec, Pageable pageable);
}
