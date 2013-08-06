package org.danielli.xultimate.orm.jpa.config;

import java.io.Serializable;

import org.danielli.xultimate.util.StringUtils;

public class DatabaseNamingStrategyModel implements Serializable {
	
	public enum Formater {
		lower {
			@Override
			public String format(StringBuilder stringBuilder) {
				return StringUtils.lowerCase(stringBuilder.toString());
			}
		}, upper {
			@Override
			public String format(StringBuilder stringBuilder) {
				return StringUtils.upperCase(stringBuilder.toString());
			}
		};
		
		public abstract String format(StringBuilder stringBuilder);
	}
	
	private static final long serialVersionUID = 5835334163543001400L;
	
	// 数据库表名前缀
	private String tablePrefix;
	// 是否以下划线形式命名
	private Boolean useUnderscores;
	
	private Formater columnFormater;
	
	private Formater tableFormater;
	
	private Integer maxLength;

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public Boolean getUseUnderscores() {
		return useUnderscores;
	}

	public void setUseUnderscores(Boolean useUnderscores) {
		this.useUnderscores = useUnderscores;
	}

	public Formater getColumnFormater() {
		return columnFormater;
	}

	public void setColumnFormater(Formater columnFormater) {
		this.columnFormater = columnFormater;
	}

	public Formater getTableFormater() {
		return tableFormater;
	}

	public void setTableFormater(Formater tableFormater) {
		this.tableFormater = tableFormater;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
}
