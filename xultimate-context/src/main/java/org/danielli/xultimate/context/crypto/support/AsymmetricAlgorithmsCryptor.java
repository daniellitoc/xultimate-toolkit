package org.danielli.xultimate.context.crypto.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.Encryptor;
import org.danielli.xultimate.context.crypto.EncryptorException;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.AsymmetricAlgorithms;
import org.danielli.xultimate.util.crypto.CipherUtils;
import org.danielli.xultimate.util.io.IOUtils;

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
	
	/** 密钥对存储路径 */
	private String keyPairPath;
	
	@PostConstruct
	public void init() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (StringUtils.isNotBlank(keyPairPath)) {
			File keyPairFile = new File(keyPairPath);
			if (keyPairFile.exists()) {
				ObjectInputStream inputStream = null;
				try {
					inputStream = new ObjectInputStream(new FileInputStream(keyPairFile));
					keyPair = (KeyPair) inputStream.readObject();
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			} else {
				keyPair = asymmetricAlgorithms.getKeyPair();
				ObjectOutputStream outputStream = null;
				try {
					outputStream = new ObjectOutputStream(new FileOutputStream(keyPairFile));
					outputStream.writeObject(keyPair);
				} finally {
					IOUtils.closeQuietly(outputStream);
				}
			}
		} else {
			keyPair = asymmetricAlgorithms.getKeyPair();
		}
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

	/**
	 * 设置密钥对存储路径。
	 * @param keyPairPath 密钥对存储路径。
	 */
	public void setKeyPairPath(String keyPairPath) {
		this.keyPairPath = keyPairPath;
	}

}
