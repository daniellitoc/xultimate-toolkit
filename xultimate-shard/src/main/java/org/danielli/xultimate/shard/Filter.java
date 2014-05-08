package org.danielli.xultimate.shard;


public interface Filter<S, T> {
	
	T doFilter(S source);
}
