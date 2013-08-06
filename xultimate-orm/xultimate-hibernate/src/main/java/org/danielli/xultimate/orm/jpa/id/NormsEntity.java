package org.danielli.xultimate.orm.jpa.id;

import javax.persistence.Cacheable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 权重。
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
@MappedSuperclass
@Cacheable(true)
@EntityListeners({ NormsListener.class })
public abstract class NormsEntity extends DateEntity {

	private static final long serialVersionUID = -6174042248508311497L;
	
	private Float boost;

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}
}
