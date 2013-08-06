package org.danielli.xultimate.util.reflect;

import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.reflect.BeanUtils;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.tamper.BeanCopy;

public class BeanUtilsTest {

	@Test
	public void testBeanToBean() {
		Person person = new Person("Daniel Li", 24);
		PerformanceMonitor.start("BeanUtilsTest");
		// 自定义 & spring
		// 正常使用
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				PersonDto personDto = new PersonDto();
				BeanUtils.copyProperties(person, personDto);
			}
			PerformanceMonitor.mark("BeanUtils" + i);
		}
		// cglib，最好使用static BeanCopier copier...，而不采用static ConcurrentHashMap<String, BeanCopier> copiers。
		// 特殊情况使用
		BeanCopier copier = BeanCopier.create(Person.class, PersonDto.class, false);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				PersonDto personDto = new PersonDto();
				copier.copy(person, personDto, null);
			}
			PerformanceMonitor.mark("BeanCopier" + i);
		}
		// tamper
		// 复杂情况使用
		BeanCopy beanCopy = BeanCopy.create(Person.class, PersonDto.class);  
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				PersonDto personDto = new PersonDto();
				beanCopy.copy(person, personDto);
			}
			PerformanceMonitor.mark("BeanCopy" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void testBeanToMapping() {
		Person person = new Person("Daniel Li", 24);
		PerformanceMonitor.start("BeanUtilsTest");
		// 自定义 & spring
		// 正常使用
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				Map<String, Object> map = new HashMap<>();
				BeanUtils.copyProperties(person, map);
			}
			PerformanceMonitor.mark("BeanUtils" + i);
		}
		// cglib
		// 特殊情况使用
		BeanMap beanMap = BeanMap.create(Person.class);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				beanMap.newInstance(person);
			}
			PerformanceMonitor.mark("cglibBeanMap" + i);
		}
		// tamper
		// 复杂情况使用
		com.alibaba.tamper.BeanMap tamperBeanMap = com.alibaba.tamper.BeanMap.create(Person.class);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				tamperBeanMap.describe(person);
			}
			PerformanceMonitor.mark("tamperBeanMap" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	

	private Person person;
	private Map<String, Object> personMap;
	
//	@Before
	public void setup() {
		person = new Person("Daniel Li", 23);
		personMap = new HashMap<String, Object>();
		personMap.put("name", "李天棚");
		personMap.put("age", 24);
	}
	
//	@Test
	public void testCopyPropertiesObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target);
		
		Assert.assertEquals(target.getName(), person.getName());
		Assert.assertEquals(target.getAge(), person.getAge());
	}

//	@Test
	public void testCopyPropertiesMapOfStringObjectObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target);
		Assert.assertEquals(target.getName(), personMap.get("name"));
		Assert.assertEquals(target.getAge(), personMap.get("age"));
	}

//	@Test
	public void testCopyPropertiesObjectMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, target);
		Assert.assertEquals(target.get("name"), person.getName());
		Assert.assertEquals(target.get("age"), person.getAge());
	}

//	@Test
	public void testCopyPropertiesObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		Assert.assertEquals(target.getAge(), person.getAge());
		Assert.assertNull(target.getName());
	}

//	@Test
	public void testCopyPropertiesMapOfStringObjectStringArrayObject() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, new String[] { "age" }, target);
		Assert.assertEquals(target.getAge(), personMap.get("age"));
		Assert.assertNull(target.getName());
	}

//	@Test
	public void testCopyPropertiesObjectStringArrayMapOfStringObject() {
		Map<String, Object> target = new HashMap<String, Object>();
		BeanUtils.copyProperties(person, new String[] { "age" }, target);
		Assert.assertEquals(target.get("age"), target.get("age"));
		Assert.assertNull(target.get("name"));
	}

//	@Test
	public void testCopyPropertiesObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(person, target, new String[] { "age" });
		Assert.assertEquals(target.getName(), person.getName());
		Assert.assertNull(target.getAge());
	}

//	@Test
	public void testCopyPropertiesMapOfStringObjectObjectStringArray() {
		Person target = new Person();
		BeanUtils.copyProperties(personMap, target, new String[] { "age" });
		Assert.assertEquals(target.getName(), personMap.get("name"));
		Assert.assertNull(target.getAge());
	}

//	@Test
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

class PersonDto {
	private String name;
	private Integer age;
	
	public PersonDto() {
		
	}
	
	public PersonDto(String name, Integer age) {
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