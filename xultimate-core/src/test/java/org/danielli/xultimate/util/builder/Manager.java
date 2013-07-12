package org.danielli.xultimate.util.builder;

import java.util.Date;

public class Manager extends Employee {
	
	private double bouns;
	
	public Manager(String name, double salary, Date hireDay, double bouns) {
		super(name, salary, hireDay);
		this.bouns = bouns;
	}

	public double getBouns() {
		return bouns;
	}

}
