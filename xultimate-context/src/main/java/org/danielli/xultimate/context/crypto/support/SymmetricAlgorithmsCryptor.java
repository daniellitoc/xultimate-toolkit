package org.danielli.xultimate.context.crypto.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;

import javax.crypto.Cipher;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.Encryptor;
import org.danielli.xultimate.context.crypto.EncryptorException;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.AsymmetricAlgorithms;
import org.danielli.xultimate.util.crypto.CipherUtils;
import org.danielli.xultimate.util.crypto.SymmetricAlgorithms;
import org.danielli.xultimate.util.io.IOUtils;
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
	
	/** 密钥存储路径 */
	private String secretKeyPath;
	
	/** 非对称密钥加密算法 */
	private AsymmetricAlgorithms asymmetricAlgorithms;
	
	/** 加密算法 */
	private SymmetricAlgorithms symmetricAlgorithms;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotEmpty(secretKeyPath)) {
			if (StringUtils.isNotEmpty(secretKeyString)) {
				throw new IllegalArgumentException("this argument `secretKeyString` must be empty or null");
			}
			Assert.notNull(asymmetricAlgorithms, "this argument `secretKeyString` must be not empty or null");
			File secretKeyFile = new File(secretKeyPath);
			if (secretKeyFile.exists()) {
				ObjectInputStream inputStream = null;
				try {
					inputStream = new ObjectInputStream(new FileInputStream(secretKeyFile));
					int secretKeyLength = inputStream.readInt();
					byte[] secretKeyBytes = new byte[secretKeyLength];
					inputStream.read(secretKeyBytes);
					KeyPair keyPair = (KeyPair) inputStream.readObject();
					Cipher cipher = asymmetricAlgorithms.getCipher();
					key = CipherUtils.unwrap(cipher, keyPair.getPrivate(), secretKeyBytes, symmetricAlgorithms);
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			} else {
				ObjectOutputStream outputStream = null;
				try {
					outputStream = new ObjectOutputStream(new FileOutputStream(secretKeyFile));
					Cipher cipher = asymmetricAlgorithms.getCipher();
					KeyPair keyPair = asymmetricAlgorithms.getKeyPair();
					key = symmetricAlgorithms.getKey();
					byte[] bytes = CipherUtils.wrap(cipher, keyPair.getPublic(), key);
					outputStream.writeInt(bytes.length);
					outputStream.write(bytes);
					outputStream.writeObject(keyPair);
				} finally {
					IOUtils.closeQuietly(outputStream);
				}
			}
		} else if (StringUtils.isNotEmpty(secretKeyString)) {
			if (asymmetricAlgorithms != null) {
				throw new IllegalArgumentException("this argument `asymmetricAlgorithms` must be null");
			}
			key = symmetricAlgorithms.getKey(secretKeyString);
		} else {
			key = symmetricAlgorithms.getKey();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		key = null;
	}
	
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

	/**
	 * 设置密钥存储路径。
	 * @param secretKeyPath 密钥存储路径。
	 */
	public void setSecretKeyPath(String secretKeyPath) {
		this.secretKeyPath = secretKeyPath;
	}

	/**
	 * 设置非对称密钥加密算法。
	 * @param asymmetricAlgorithms 非对称密钥加密算法
	 */
	public void setAsymmetricAlgorithms(AsymmetricAlgorithms asymmetricAlgorithms) {
		this.asymmetricAlgorithms = asymmetricAlgorithms;
	}
}
