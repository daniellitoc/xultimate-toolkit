package org.danielli.xultimate.context.crypto.support;

import java.security.KeyPair;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.Encryptor;
import org.danielli.xultimate.context.crypto.EncryptorException;
import org.danielli.xultimate.util.crypto.AsymmetricAlgorithms;
import org.danielli.xultimate.util.crypto.CipherUtils;

/**
 * 非对称加密和解密器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 */
public class AsymmetricAlgorithmsCryptor implements Encryptor<byte[], byte[]>, Decryptor<byte[], byte[]>  {

	/** 密钥对 */
	private KeyPair keyPair;
	
	/** 非对称加密算法 */
	private AsymmetricAlgorithms asymmetricAlgorithms;
	
	@PostConstruct
	public void init() {
		keyPair = asymmetricAlgorithms.getKeyPair();
	}
	
	@PreDestroy
	public void destroy() {
		keyPair = null;
	}
	
	@Override
	public byte[] decrypt(byte[] source) throws DecryptorException {
		try {
			return CipherUtils.decrypt(asymmetricAlgorithms.getCipher(), keyPair.getPrivate(), source);
		} catch (Exception e) {
			throw new DecryptorException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] encrypt(byte[] source) throws EncryptorException {
		try {
			return CipherUtils.encrypt(asymmetricAlgorithms.getCipher(), keyPair.getPublic(), source);
		} catch (Exception e) {
			throw new EncryptorException(e.getMessage(), e);
		}
	}

	/**
	 * 设置非对称加密算法
	 * @param asymmetricAlgorithms 非对称加密算法
	 */
	public void setAsymmetricAlgorithms(AsymmetricAlgorithms asymmetricAlgorithms) {
		this.asymmetricAlgorithms = asymmetricAlgorithms;
	}

}
