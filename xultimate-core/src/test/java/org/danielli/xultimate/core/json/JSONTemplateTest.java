package org.danielli.xultimate.core.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-json.xml" })
public class JSONTemplateTest {
	
	@Resource
	private JSONTemplate jacksonTemplate;
	
	@Resource
	private JSONTemplate fastJSONTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONTemplateTest.class);
	
	@Test
	public void testReadWriteValue() {
		PerformanceMonitor.start("JSONTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				String value = testWriteValueAsString1(fastJSONTemplate);
				fastJSONTemplate.readValue(value, new ValueType<List<User>>() {});
				
				value = testWriteValueAsString2(fastJSONTemplate);
				fastJSONTemplate.readValue(value, new ValueType<List<UserWithList>>() {});
			}
			PerformanceMonitor.mark("fastJSONTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50000; j++) {
				String value = testWriteValueAsString1(jacksonTemplate);
				jacksonTemplate.readValue(value, new ValueType<List<User>>() {});
				
				value = testWriteValueAsString2(jacksonTemplate);
				jacksonTemplate.readValue(value, new ValueType<List<UserWithList>>() {});
			}
			PerformanceMonitor.mark("jacksonTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test() {
		String value = testWriteValueAsString1(fastJSONTemplate);
		LOGGER.info("JSON String:{}", value);
		List<User> users = fastJSONTemplate.readValue(value, new ValueType<List<User>>() {});
		for (User user : users) {
			LOGGER.info("Objects:");
			LOGGER.info("\t{}:{}", new Object[] {user.getUsername(), user.getPassword()});
		}
		
		value = testWriteValueAsString2(fastJSONTemplate);
		
		LOGGER.info("JSON String:{}", value);
		LOGGER.info("Objects:");
		List<UserWithList> userWithLists = fastJSONTemplate.readValue(value, new ValueType<List<UserWithList>>() {});
		for (UserWithList user : userWithLists) {
			LOGGER.info("\t{}:{}", new Object[] {user.getUsername(), user.getPassword()});
			for (Order order : user.getOrders()) {
				LOGGER.info("\t\t{}", order.getAmount());
			}
		}
		
		value = testWriteValueAsString1(jacksonTemplate);
		LOGGER.info("JSON String:{}", value);
		users = jacksonTemplate.readValue(value, new ValueType<List<User>>() {});
		for (User user : users) {
			LOGGER.info("Objects:");
			LOGGER.info("\t{}:{}", new Object[] {user.getUsername(), user.getPassword()});
		}
		
		value = testWriteValueAsString2(jacksonTemplate);
		
		LOGGER.info("JSON String:{}", value);
		LOGGER.info("Objects:");
		userWithLists = jacksonTemplate.readValue(value, new ValueType<List<UserWithList>>() {});
		for (UserWithList user : userWithLists) {
			LOGGER.info("\t{}:{}", new Object[] {user.getUsername(), user.getPassword()});
			for (Order order : user.getOrders()) {
				LOGGER.info("\t\t{}", order.getAmount());
			}
		}
	}

	public String testWriteValueAsString2(JSONTemplate jsonTemplate) {
		Order jackOrder1 = new Order();
		jackOrder1.setAmount(new BigDecimal("1.1"));
		jackOrder1.setSome1("11");
		jackOrder1.setSome2("12");
		Order jackOrder2 = new Order();
		jackOrder2.setAmount(new BigDecimal("1.2"));
		jackOrder2.setSome1("11");
		jackOrder2.setSome2("12");
		List<Order> jackOrders = new ArrayList<Order>();
		jackOrders.add(jackOrder1);
		jackOrders.add(jackOrder2);
		
		
		UserWithList jackUser = new UserWithList();
		jackUser.setUsername("Jack Li");
		jackUser.setPassword("123456");
		jackUser.setOrders(jackOrders);
		
		Order danielOrder1 = new Order();
		danielOrder1.setAmount(new BigDecimal("2.1"));
		danielOrder1.setSome1("21");
		danielOrder1.setSome2("22");
		Order danielOrder2 = new Order();
		danielOrder2.setAmount(new BigDecimal("2.2"));
		danielOrder2.setSome1("21");
		danielOrder2.setSome2("22");
		List<Order> danielOrders = new ArrayList<Order>();
		danielOrders.add(danielOrder1);
		danielOrders.add(danielOrder2);
		
		
		UserWithList danielUser = new UserWithList();
		danielUser.setUsername("Daniel Li");
		danielUser.setPassword("123456");
		danielUser.setOrders(jackOrders);
		
		List<UserWithList> users = new ArrayList<UserWithList>();
		users.add(danielUser);
		users.add(jackUser);
		String value = jsonTemplate.writeValueAsString(users);
		return value;
	}
	
	public String testWriteValueAsString1(JSONTemplate jsonTemplate) {
		User danielUser = new User();
		danielUser.setUsername("Daniel Li");
		danielUser.setPassword("123456");
		User jackUser = new User();
		jackUser.setUsername("Jack Li");
		jackUser.setPassword("123456");
		User johnUser = new User();
		johnUser.setUsername("John Li");
		johnUser.setPassword("123456");
		
		List<User> users = new ArrayList<User>();
		users.add(danielUser);
		users.add(jackUser);
		users.add(johnUser);
		String value = jsonTemplate.writeValueAsString(users);
		return value;
	}
}

class User {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

@JsonIgnoreProperties(value = { "amount", "hibernateLazyInitializer" })
class Order {
	
	private BigDecimal amount;
	
	private String some1;
	private String some2;

	@JSONField(deserialize = false, serialize = false)
	public BigDecimal getAmount() {
		return amount;
	}

	@JSONField(deserialize = false, serialize = false)
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSome1() {
		return some1;
	}

	public void setSome1(String some1) {
		this.some1 = some1;
	}

	public String getSome2() {
		return some2;
	}

	public void setSome2(String some2) {
		this.some2 = some2;
	}


}

class UserWithList extends User {
	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
