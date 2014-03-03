package org.danielli.xultimate.orm.mybatis.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.util.builder.BuildType;
import org.danielli.xultimate.util.builder.Buildable;
import org.danielli.xultimate.util.builder.ToStringBuilderUtils;

@Buildable({ BuildType.TO_STRING })
public class TestPo implements Serializable {
	private static final long serialVersionUID = -352608483864538404L;
	
	public static final byte CAN_READ = 1 << 0;
	public static final byte CAN_WRITE = 1 << 1;
	public static final byte CAN_EXECUTE = 1 << 2;
	
	public enum Sex {
		FEMALE, MALE
	}
	
	private Long id;		// org.apache.ibatis.type.LongTypeHandler
	
	private Long parentId;	// org.danielli.xultimate.orm.mybatis.type.LongNullParameterTypeHandler
	
	private Integer otherId; // org.danielli.xultimate.orm.mybatis.type.IntegerNullParameterTypeHandler
	
	private Integer loginCount;	// org.apache.ibatis.type.IntegerTypeHandler
	
	private Float boost;	// org.apache.ibatis.type.FloatTypeHandler
	
	private BigDecimal money;	// org.danielli.xultimate.orm.mybatis.type.BigDecimalTypeHandler
	
	private String message;	// org.danielli.xultimate.orm.mybatis.type.StringNullParameterTypeHandler
	
	private String introduction;	// org.danielli.xultimate.orm.mybatis.type.ClobNullParameterTypeHandler
	
	private Sex sex;	// org.apache.ibatis.type.EnumOrdinalTypeHandler<E extends Enum<E>>
	
	private Date createTime;	// org.apache.ibatis.type.DateTypeHandler
	
	private Date updateTime;	// org.apache.ibatis.type.DateTypeHandler
	
	private Boolean isLock;	// org.danielli.xultimate.orm.mybatis.type.BooleanTypeHandler
	
	private Boolean hasLogin;	// org.apache.ibatis.type.BooleanTypeHandler
	
	private StateSet stateSet;	// org.danielli.xultimate.orm.mybatis.type.StateSetTypeHandler
	
	private String loginIp;	// org.apache.ibatis.type.StringTypeHandler
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public StateSet getStateSet() {
		return stateSet;
	}

	public void setStateSet(StateSet stateSet) {
		this.stateSet = stateSet;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public String toString() {
		return ToStringBuilderUtils.reflectionToString(this);
	}

	public Boolean getHasLogin() {
		return hasLogin;
	}

	public void setHasLogin(Boolean hasLogin) {
		this.hasLogin = hasLogin;
	};
}
