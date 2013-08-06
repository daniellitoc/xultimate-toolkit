package org.danielli.xultimate.context.kvStore.redis.jedis;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -1738355062692076549L;

	private String name;
	
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}