package org.danielli.xultimate.orm.jpa.ds;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * 动态查询的Specification。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <T> 根类型
 */
public class ItemsSpecification<T> implements Specification<T> {
	private List<Item<? extends Object>> itemList;

	public ItemsSpecification(List<Item<? extends Object>> itemList) {
		this.itemList = itemList;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = null;
		if (CollectionUtils.isNotEmpty(itemList)) {
			for (Item<? extends Object> item : itemList) {
				predicate = item.getComparsionOperator().getPredicate(item.getKey(), item.getValue(), root, query, cb);
				if (predicate != null)
					predicate = item.getLogicalOperator().getPredicate(predicate, cb);
			}
		}
		if (predicate == null)
			return cb.conjunction();
		return predicate;
	}
}
