package org.danielli.xultimate.util.builder;

import org.joda.time.DateTime;

public class Manager extends Employee {
	
	private double bouns;
	
	public Manager(String name, double salary, DateTime hireDay, double bouns) {
		super(name, salary, hireDay);
		this.bouns = bouns;
	}

	public double getBouns() {
		return bouns;
	}

}
