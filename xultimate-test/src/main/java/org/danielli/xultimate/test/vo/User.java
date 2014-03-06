package org.danielli.xultimate.test.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class User implements Serializable {
	
	private static final long serialVersionUID = -1517649455741479617L;
	
	private Long id;
	
	@NotNull
    @Pattern(regexp = "\\w{4,30}")
	private String name;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
