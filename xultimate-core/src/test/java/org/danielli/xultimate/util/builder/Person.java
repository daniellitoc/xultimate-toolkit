package org.danielli.xultimate.util.builder;

@Buildable({BuildType.TO_STRING, BuildType.HASH_CODE, BuildType.EQUALS, BuildType.COMPARE_TO})
public abstract class Person {
	private String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	public abstract String getDescription();

	public String getName() {
		return name;
	}
}
