package org.danielli.xultimate.orm.jpa;

import javax.persistence.Cacheable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.danielli.xultimate.orm.jpa.id.NormsEntity;

@MappedSuperclass
@Cacheable(true)
public class BaseEntity extends NormsEntity<Long> {

	private static final long serialVersionUID = 1298348676340359002L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return super.getId();
	}

}
