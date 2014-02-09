package org.danielli.xultimate.jdbc.type;

/**
 * 对应MySQL当中的Bit或Set
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class StateSet {

	private long value;
	
	public StateSet(long value) {
		this.value = value;
	}
	
	public StateSet() {
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public void clear() {
		value = 0;
	}
	
	public void add(long states) {
		value = value | states;
	}
	
	public void remove(long states) {
		value = value & (~states);
	}
	
	public boolean contain(long states) {
		return (value & states) == states;
	}
}
