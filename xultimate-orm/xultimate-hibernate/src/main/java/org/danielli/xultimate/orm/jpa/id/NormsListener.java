package org.danielli.xultimate.orm.jpa.id;

import javax.persistence.PrePersist;

/**
 * 权重监听器。
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class NormsListener {
	
	@PrePersist
	public void prePersist(NormsEntity<?> norms) {
		if (norms.getBoost() == null) {
			norms.setBoost(0.0F);
		}
	}
}
