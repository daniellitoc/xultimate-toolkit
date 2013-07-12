package org.danielli.xultimate.util.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.danielli.xultimate.util.StringUtils;

/**
 * 对称加密算法枚举类。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see SecretKey
 * @see Cipher
 */
public enum SymmetricAlgorithms {
	/**
	 * AES算法。
	 */
	AES("AES");
	
	/** 算法名称 */
	private String name;
	
	/**
	 * 使用算法名称实例化算法。
	 * 
	 * @param name 算法名称。
	 */
	private SymmetricAlgorithms(String name) {
		this.name = name;
	}
	
	/**
	 * 获取该算法的随机密钥。
	 * 
	 * @return 该算法的密钥。
	 */
	public SecretKey getKey() throws CryptoException {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(name);
			generator.init(new SecureRandom());
			return generator.generateKey();
		} catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e.getMessage(), e);
        }
	}
	
	/**
	 * 获取该算法的密钥对。
	 * 
	 * @param keyString 密钥码。
	 * @return 该算法的密钥。
	 */
	public SecretKey getKey(String keyString) {
		try {
//			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(name); 
			byte[] keyBytes = DigestUtils.digest(MessageDigestAlgorithms.MD5.getDigest(), StringUtils.getBytesUtf8(keyString));
//			return secretKeyFactory.generateSecret(new SecretKeySpec(keyBytes, name));
			return new SecretKeySpec(keyBytes, name);
		} catch (Exception e) {
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
