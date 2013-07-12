package org.danielli.xultimate.beans;

import java.util.HashMap;
import java.util.Map;

import org.danielli.xultimate.beans.BeanUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanUtilsTest {
	private Person person;
	private Map<String, Object> personMap;
	
	@Before
	public void setup() {
		person = new Person("Daniel Li", 23);
		personMap = new HashMap<String, Object>();
		personMap.put("name", "李天棚");
		personMap.put("age", 24);
	}
	
	@Test
	public void testCopyPropertiesObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target);
		
		Assert.assertEquals(target.getName(), person.getName());
		Assert.assertEquals(target.getAge(), person.getAge());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target);
		Assert.assertEquals(target.getName(), personMap.get("name"));
		Assert.assertEquals(target.getAge(), personMap.get("age"));
	}

	@Test
	public void testCopyPropertiesObjectMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, target);
		Assert.assertEquals(target.get("name"), person.getName());
		Assert.assertEquals(target.get("age"), person.getAge());
	}

	@Test
	public void testCopyPropertiesObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		Assert.assertEquals(target.getAge(), person.getAge());
		Assert.assertNull(target.getName());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, new String[] { "age" }, target);
		Assert.assertEquals(target.getAge(), personMap.get("age"));
		Assert.assertNull(target.getName());
	}

	@Test
	public void testCopyPropertiesObjectStringArrayMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		Assert.assertEquals(target.get("age"), target.get("age"));
		Assert.assertNull(target.get("name"));
	}

	@Test
	public void testCopyPropertiesObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target, new String[] { "age" });
		Assert.assertEquals(target.getName(), person.getName());
		Assert.assertNull(target.getAge());
	}

	@Test
	public void testCopyPropertiesMapOfStringObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target, new String[] { "age" });
		Assert.assertEquals(target.getName(), personMap.get("name"));
		Assert.assertNull(target.getAge());
	}

	@Test
	public void testCopyPropertiesObjectMapOfStringObjectStringArray() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, target, new String[] { "age" });
		Assert.assertEquals(target.get("name"), person.getName());
		Assert.assertNull(target.get("age"));
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