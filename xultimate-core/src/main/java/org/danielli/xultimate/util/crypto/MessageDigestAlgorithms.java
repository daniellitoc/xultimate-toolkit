package org.danielli.xultimate.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法枚举类。
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see MessageDigest
 */
public enum MessageDigestAlgorithms {
	/** MD5算法 */
	MD5("MD5"), 
	/** SHA1算法 */
	SHA_1("SHA1"), 
	/** SHA256算法 */
	SHA_256("SHA256"), 
	/** SHA384算法 */
	SHA_384("SHA384"), 
	/** SHA384算法 */
	SHA_512("SHA384");
	
	/** 算法名称 */
	private String name;
	
	/**
	 * 使用算法名称实例化算法。
	 * 
	 * @param name 算法名称。
	 */
	private MessageDigestAlgorithms(String name) {
		this.name = name;
	}
	
	/**
	 * 获取该算法的消息摘要。
	 * 
	 * @return 该算法的消息摘要。
	 */
	public MessageDigest getDigest() throws CryptoException {
        try {
            return MessageDigest.getInstance(this.name);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e.getMessage(), e);
        }
    }
}
