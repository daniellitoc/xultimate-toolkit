package org.danielli.xultimate.orm.jpa.ds.support;

import java.io.Serializable;

public class Pair<T> implements Serializable {
	
	private static final long serialVersionUID = 211613925559016612L;

	private T first;
	
	private T second;
	
	public T getFirst() {
		return first;
	}
	
	public void setFirst(T first) {
		this.first = first;
	}
	
	public T getSecond() {
		return second;
	}
	
	public void setSecond(T second) {
		this.second = second;
	}
}
