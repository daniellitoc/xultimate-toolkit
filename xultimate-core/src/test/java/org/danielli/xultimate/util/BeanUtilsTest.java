package org.danielli.xultimate.util;

import java.util.HashMap;
import java.util.Map;

import org.danielli.xultimate.util.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtilsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilsTest.class);
	
	private Person person;
	private Map<String, Object> personMap;
	
	@Before
	public void setup() {
		person = new Person("Daniel Li", 23);
		personMap = new HashMap<String, Object>();
		personMap.put("name", "Daniel Li");
		personMap.put("age", 23);
	}
	
	@Test
	public void testCopyPropertiesObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target);
		LOGGER.info("1{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target);
		LOGGER.info("2{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesObjectMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, target);
		LOGGER.info("3{name: {}, age: {}}", target.get("name"), target.get("age"));
	}

	@Test
	public void testCopyPropertiesObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		LOGGER.info("4{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, new String[] { "age" }, target);
		LOGGER.info("5{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesObjectStringArrayMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		LOGGER.info("6{name: {}, age: {}}", target.get("name"), target.get("age"));
	}

	@Test
	public void testCopyPropertiesObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target, new String[] { "age" });
		LOGGER.info("7{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target, new String[] { "age" });
		LOGGER.info("8{name: {}, age: {}}", target.getName(), target.getAge());
	}

	@Test
	public void testCopyPropertiesObjectMapOfStringObjectStringArray() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, target, new String[] { "age" });
		LOGGER.info("9{name: {}, age: {}}", target.get("name"), target.get("age"));
	}

}



class Person {
	private String name;
	private Integer age;
	
	public Person() {
		
	}
	
	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

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