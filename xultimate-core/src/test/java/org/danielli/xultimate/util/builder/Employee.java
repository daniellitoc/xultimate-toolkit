package org.danielli.xultimate.util.builder;

import java.util.Date;

public class Employee extends Person {
	
	public Employee(String name, double salary, Date hireDay) {
		super(name);
		this.salary = salary;
		this.hireDay = hireDay;
	}

	private double salary;
	private Date hireDay;
	
	public double getSalary() {
		return salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	@Override
	public String getDescription() {
		return String.format("an employee with a salary of $%.2f", salary);
	}
}
