package org.danielli.xultimate.core.json;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class User {
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
