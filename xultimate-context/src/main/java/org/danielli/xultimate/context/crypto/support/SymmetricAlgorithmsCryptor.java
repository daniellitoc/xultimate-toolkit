package org.danielli.xultimate.context.crypto.support;

import java.security.Key;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.Encryptor;
import org.danielli.xultimate.context.crypto.EncryptorException;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.CipherUtils;
import org.danielli.xultimate.util.crypto.SymmetricAlgorithms;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 对称加密和解密器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 */
public class SymmetricAlgorithmsCryptor implements Encryptor<byte[], byte[]>, Decryptor<byte[], byte[]>, InitializingBean, DisposableBean {

	/** 密钥 */
	private Key key;
	
	/** 加密密钥*/
	private String secretKeyString;
	
	/** 加密算法 */
	private SymmetricAlgorithms symmetricAlgorithms;
	
	@Override
	public byte[] decrypt(byte[] source) throws DecryptorException {
		try {
			return CipherUtils.decrypt(symmetricAlgorithms.getCipher(), key, source);
		} catch (Exception e) {
			throw new DecryptorException(e.getMessage(), e);
		}
		
	}

	@Override
	public byte[] encrypt(byte[] source) throws EncryptorException {
		try {
			return CipherUtils.encrypt(symmetricAlgorithms.getCipher(), key, source);
		} catch (Exception e) {
			throw new EncryptorException(e.getMessage(), e);
		}
	}
	
	/**
	 * 设置加密密钥
	 * @param secretKeyString 加密密钥
	 */
	public void setSecretKeyString(String secretKeyString) {
		this.secretKeyString = secretKeyString;
	}

	/**
	 * 设置加密算法
	 * @param symmetricAlgorithms 加密算法
	 */
	public void setSymmetricAlgorithms(SymmetricAlgorithms symmetricAlgorithms) {
		this.symmetricAlgorithms = symmetricAlgorithms;
	}
	
	@Override
	public void destroy() throws Exception {
		key = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotEmpty(secretKeyString)) {
			key = symmetricAlgorithms.getKey(secretKeyString);
		} else {
			key = symmetricAlgorithms.getKey();
		}
	}

}
