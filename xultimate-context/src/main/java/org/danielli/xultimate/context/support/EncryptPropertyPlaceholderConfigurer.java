package org.danielli.xultimate.context.support;

import java.util.Properties;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 提供了加密功能的属性置换配置器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see PropertyPlaceholderConfigurer
 * @see Decryptor
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	/** 已加密的属性名称列表 */
	private String[] encryptPropertyNames;
	/** 对加密值的解密器 */
	private Decryptor<String, String> decryptor;
	
	@Override
	protected void convertProperties(Properties props) {
		if (ArrayUtils.isNotEmpty(encryptPropertyNames)) {
			for (String key : encryptPropertyNames) {
				String value = props.getProperty(key);
				if (StringUtils.isNotEmpty(value)) {
					props.setProperty(key, decryptProperty(value));  // 对属性值重新解密。
				}
			}
		}
		super.convertProperties(props);
	}

	/**
	 * 解密给定的属性值。
	 * @param propertyValue 加密属性值。	
	 * @return	解密的属性值。
	 */
	protected String decryptProperty(String propertyValue) {
		return decryptor.decrypt(propertyValue);
	}

	/**
	 * 获取已加密的属性名称列表
	 * 
	 * @return 已加密的属性名称列表
	 */
	public String[] getEncryptPropertyNames() {
		return encryptPropertyNames;
	}

	/**
	 * 设置已加密的属性名称列表
	 * 
	 * @param encryptPropertyNames 已加密的属性名称列表
	 */
	public void setEncryptPropertyNames(String[] encryptPropertyNames) {
		this.encryptPropertyNames = encryptPropertyNames;
	}

	/**
	 * 设置解密器。
	 * 
	 * @param decryptor 解密器。
	 */
	public void setDecryptor(Decryptor<String, String> decryptor) {
		this.decryptor = decryptor;
	}
}
