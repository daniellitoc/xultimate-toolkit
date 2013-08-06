package org.danielli.xultimate.orm.jpa.config;

import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.danielli.xultimate.util.BooleanUtils;
import org.danielli.xultimate.util.CharUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.reflect.BeanUtils;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.util.StringHelper;
/**
 * 数据库表名、列名命名策略。
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class DatabaseNamingStrategy extends DatabaseNamingStrategyModel implements NamingStrategy {

	private static final long serialVersionUID = 6010248785529143540L;
	
	public DatabaseNamingStrategy() {
		try {
			DatabaseNamingStrategyModel model = ApplicationContextUtils.getBean(BeanFactoryContext.currentApplicationContext(), DatabaseNamingStrategyModel.class);
			BeanUtils.copyProperties(model, this);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	protected StringBuilder addUnderscores(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		if (BooleanUtils.isTrue(this.getUseUnderscores())) {
			stringBuilder.append(StringUtils.replaceChars(name, '.', '_'));
			for (int i = 1; i < stringBuilder.length() - 1; i++) {
				if (CharUtils.isAsciiAlphaLower(stringBuilder.charAt(i - 1)) && CharUtils.isAsciiAlphaUpper(stringBuilder.charAt(i)) && CharUtils.isAsciiAlphaLower(stringBuilder.charAt(i + 1))) {
					stringBuilder.insert(i++, '_');
				}
			}
		} else {
			stringBuilder.append(name);
		}
		return stringBuilder;
	}

	protected StringBuilder addTablePrefix(StringBuilder stringBuilder) {
		if (StringUtils.isNotEmpty(this.getTablePrefix())) {
			stringBuilder.insert(0, this.getTablePrefix());
		}
		return stringBuilder;
	}
	
	protected String processTableFormater(StringBuilder stringBuilder) {
		if (this.getTableFormater() == null) {
			return stringBuilder.toString();
		}
		return this.getTableFormater().format(stringBuilder);
	}
	
	protected String processColumnFormater(StringBuilder stringBuilder) {
		if (this.getColumnFormater() == null) {
			return stringBuilder.toString();
		}
		return this.getColumnFormater().format(stringBuilder);
	}
	
	protected String processMaxLength(String name) {
		if (this.getMaxLength().compareTo(0) > 0) {
			return StringUtils.substring(name, 0, this.getMaxLength());
		}
		return name;
	}
	
	@Override
	public String classToTableName(String className) {
		return processMaxLength(processTableFormater(addTablePrefix(addUnderscores(StringHelper.unqualify(className)))));
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return processMaxLength(processColumnFormater(addUnderscores(StringHelper.unqualify(propertyName))));
	}

	@Override
	public String tableName(String tableName) {
		return processMaxLength(processTableFormater(addTablePrefix(addUnderscores(tableName))));
	}

	@Override
	public String columnName(String columnName) {
		return processMaxLength(processColumnFormater(addUnderscores(columnName)));
	}

	@Override
	public String collectionTableName(String ownerEntity,
			String ownerEntityTable, String associatedEntity,
			String associatedEntityTable, String propertyName) {
		return processMaxLength(processTableFormater(addUnderscores(ownerEntityTable + StringUtils.capitalize(propertyToColumnName(propertyName)))));
	}

	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return columnName(joinedColumn);
	}

	@Override
	public String foreignKeyColumnName(String propertyName,
			String propertyEntityName, String propertyTableName,
			String referencedColumnName) {
		String header = propertyName != null ? StringHelper.unqualify( propertyName ) : propertyTableName;
		if (header == null) throw new AssertionFailure("NamingStrategy not properly filled");
		return columnName(header);
	}

	@Override
	public String logicalColumnName(String columnName, String propertyName) {
		return StringUtils.isNotEmpty( columnName ) ? columnName : StringHelper.unqualify(propertyName);
	}

	@Override
	public String logicalCollectionTableName(String tableName,
			String ownerEntityTable, String associatedEntityTable,
			String propertyName) {
		if ( tableName != null ) {
			return tableName;
		}
		else {
			//use of a stringbuffer to workaround a JDK bug
			return new StringBuffer(ownerEntityTable).append("_").append(associatedEntityTable != null ?
						associatedEntityTable : StringHelper.unqualify( propertyName )).toString();
		}
	}

	@Override
	public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
		return StringUtils.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName ) + "_" + referencedColumn;
	}
}
