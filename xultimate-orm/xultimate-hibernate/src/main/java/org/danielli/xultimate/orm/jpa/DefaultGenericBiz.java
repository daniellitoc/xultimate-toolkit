package org.danielli.xultimate.orm.jpa;

import java.io.Serializable;
import java.util.List;

import org.danielli.xultimate.orm.jpa.id.IDEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 通用数据访问接口的默认实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 * @param <T> 操作对象类型。
 * @param <ID> 操作对象主键类型。
 */
public abstract class DefaultGenericBiz<T extends IDEntity<ID>, ID extends Serializable> implements GenericBiz<T, ID> {
	/** 通用数据访问接口。 */
	private GenericDAO<T, ID> genericDAO;
	
	@Override
	public <S extends T> S save(S entity) {
		return genericDAO.save(entity);
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		return genericDAO.save(entities);
	}

	@Override
	public T findOne(ID id) {
		return genericDAO.findOne(id);
	}

	@Override
	public boolean exists(ID id) {
		return genericDAO.exists(id);
	}

	@Override
	public List<T> findAll() {
		return genericDAO.findAll();
	}

	@Override
	public List<T> findAll(Iterable<ID> ids) {
		return genericDAO.findAll(ids);
	}

	@Override
	public long count() {
		return genericDAO.count();
	}

	@Override
	public void delete(ID id) {
		genericDAO.delete(id);
	}

	@Override
	public void delete(T entity) {
		genericDAO.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		genericDAO.delete(entities);
	}

	@Override
	public void deleteAll() {
		genericDAO.deleteAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return genericDAO.findAll(sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return genericDAO.findAll(pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return genericDAO.findAll(spec);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return genericDAO.findAll(spec, sort);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return genericDAO.findAll(spec, pageable);
	}
	
	/**
	 * 设置通用数据访问接口。
	 * 
	 * @param genericDAO 通用数据访问接口。
	 */
	public void setGenericDAO(GenericDAO<T, ID> genericDAO) {
		this.genericDAO = genericDAO;
	}
}
