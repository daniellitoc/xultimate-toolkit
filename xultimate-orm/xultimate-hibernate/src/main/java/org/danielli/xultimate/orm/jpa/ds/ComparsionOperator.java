package org.danielli.xultimate.orm.jpa.ds;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.orm.jpa.ds.support.Pair;

/**
 * 动态查询的比较符操作器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public enum ComparsionOperator {
	EQ {
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Object> expression = getExpression(root, key, Object.class);
			return builder.equal(expression, value.getValue());
		}
	},
	NOT_EQ {
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Object> expression = getExpression(root, key, Object.class);
			return builder.notEqual(expression, value.getValue());
		}
	},
	LIKE {
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<String> expression = getExpression(root, key, String.class);
			String pattern = (String) value.getValue();
			return builder.like(expression, pattern);
		}
	},
	NOT_LIKE {
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<String> expression = getExpression(root, key, String.class);
			String pattern = (String) value.getValue();
			return builder.notLike(expression, pattern);
		}
	}, 
	GT {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Comparable> expression = getExpression(root, key, Comparable.class);
			Comparable comparable = (Comparable) value.getValue();
			return builder.greaterThan(expression, comparable);
		}
	},
	LT {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Comparable> expression = getExpression(root, key, Comparable.class);
			Comparable comparable = (Comparable) value.getValue();
			return builder.lessThan(expression, comparable);
		}
	},
	GE {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Comparable> expression = getExpression(root, key, Comparable.class);
			Comparable comparable = (Comparable) value.getValue();
			return builder.lessThanOrEqualTo(expression, comparable);
		}
	},
	LE {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Comparable> expression = getExpression(root, key, Comparable.class);
			Comparable comparable = (Comparable) value.getValue();
			return builder.lessThanOrEqualTo(expression, comparable);
		}
	},
	IN {
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			Expression<Object> expression = getExpression(root, key, Object.class);
			@SuppressWarnings("rawtypes")
			Collection collection = (Collection) value.getValue();
			return builder.in(expression.in(collection));
		}
	},
	BETWEEN {
		@SuppressWarnings("unchecked")
		@Override
		public <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			@SuppressWarnings("rawtypes")
			Expression<Comparable> expression = getExpression(root, key, Comparable.class);
			@SuppressWarnings("rawtypes")
			Pair<Comparable> pair = (Pair<Comparable>) value.getValue();
			return builder.between(expression, pair.getFirst(), pair.getSecond());
		}
	};
	
	@SuppressWarnings("unchecked")
	protected <CT, FT> Expression<FT> getExpression(Root<CT> root, String key, Class<FT> clazz) {
		String[] names = StringUtils.split(key, ".");
		Path<Object> expression = null;
		for (String name : names) {
			expression = root.get(name);
		}
		return (Expression<FT>) expression;
	}
	
	public abstract <VT, CT> Predicate getPredicate(String key, Value<VT> value, Root<CT> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
