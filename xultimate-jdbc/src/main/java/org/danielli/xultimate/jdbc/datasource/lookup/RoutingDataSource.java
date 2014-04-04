package org.danielli.xultimate.jdbc.datasource.lookup;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * {@link javax.sql.DataSource} implementation that routes {@link #getConnection()}
 * calls to one of various target DataSources based on a lookup key. The latter is usually
 * (but not necessarily) determined through some thread-bound transaction context.
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see #setTargetDataSources
 * @see #setDefaultTargetDataSource
 * @see #determineCurrentLookupKey()
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContext.currentLookupKey();
	}

}
