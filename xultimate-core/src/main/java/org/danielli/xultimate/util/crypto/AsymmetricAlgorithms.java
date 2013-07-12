package org.danielli.xultimate.util.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;

/**
 * 非对称加密算法枚举类。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see KeyPair
 * @see Cipher
 */
public enum AsymmetricAlgorithms {
	/**
	 * RSA算法。
	 */
	RSA("RSA");
	
	/** 算法名称 */
	private String name;
	
	/**
	 * 使用算法名称实例化算法。
	 * 
	 * @param name 算法名称。
	 */
	private AsymmetricAlgorithms(String name) {
		this.name = name;
	}
	
	/**
	 * 获取该算法的密钥对。
	 * 
	 * @return 该算法的密钥对
	 */
	public KeyPair getKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(name);
			generator.initialize(512, new SecureRandom());
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
	        throw new CryptoException(e.getMessage(), e);
	    }
	}
	
	/**
	 * 获取该算法的加密器。
	 * 
	 * @return 该算法的加密器。
	 */
	public Cipher getCipher() {
		try {
			return Cipher.getInstance(name);
		} catch (Exception e) {
	        throw new CryptoException(e.getMessage(), e);
	    }
	}
}
