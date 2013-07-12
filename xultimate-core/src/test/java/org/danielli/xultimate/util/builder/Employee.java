package org.danielli.xultimate.util.builder;

import org.joda.time.DateTime;

public class Employee extends Person {
	
	public Employee(String name, double salary, DateTime hireDay) {
		super(name);
		this.salary = salary;
		this.hireDay = hireDay;
	}

	private double salary;
	private DateTime hireDay;
	
	public double getSalary() {
		return salary;
	}

	public DateTime getHireDay() {
		return hireDay;
	}

	@Override
	public String getDescription() {
		return String.format("an employee with a salary of $%.2f", salary);
	}
}
