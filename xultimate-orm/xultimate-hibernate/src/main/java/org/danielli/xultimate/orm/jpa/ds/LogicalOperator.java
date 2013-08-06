package org.danielli.xultimate.orm.jpa.ds;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * 动态查询的逻辑符操作器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public enum LogicalOperator {
	OR {
		@Override
		public Predicate getPredicate(Predicate predicate, CriteriaBuilder builder) {
			return builder.or(predicate);
		}
	},
	AND {
		@Override
		public Predicate getPredicate(Predicate predicate, CriteriaBuilder builder) {
			return builder.and(predicate);
		}
	},
	NOT {
		@Override
		public Predicate getPredicate(Predicate predicate, CriteriaBuilder builder) {
			return builder.not(predicate);
		}
	};
	
	public abstract Predicate getPredicate(Predicate predicate, CriteriaBuilder builder);
}
