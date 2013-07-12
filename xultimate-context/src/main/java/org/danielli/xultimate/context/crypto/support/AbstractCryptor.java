package org.danielli.xultimate.context.crypto.support;

import org.danielli.xultimate.context.crypto.Decryptor;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.Encryptor;
import org.danielli.xultimate.context.crypto.EncryptorException;


/**
 * 抽象加密和解密器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Encryptor
 * @see Decryptor
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 * @param <IS> 内部处理原始源。
 * @param <IT> 内部处理目标源。
 */
public abstract class AbstractCryptor<S, T, IS, IT> implements Encryptor<S, T>, Decryptor<T, S> {
	/** 解密器，处理IT到IS类型。 */
	protected Decryptor<IT, IS> decryptor;
	/** 加密器，处理IS到IT类型。 */
	protected Encryptor<IS, IT> encryptor;

	/**
	 * 内部加密。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws EncryptorException 编码异常，在编码过程中出现的任何异常都会使用{@link EncryptorException}封装并抛出。
	 */
	public IT innerEncrypt(IS source) throws EncryptorException {
		return encryptor.encrypt(source);
	}
	
	/**
	 * 内部解密。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws DecryptorException 解密异常，在解密过程中出现的任何异常都会使用{@link DecryptorException}封装并抛出。
	 */
	public IS innerDencrypt(IT source) throws DecryptorException {
		return decryptor.decrypt(source);
	}
	
	/**
	 * 设置解密器。
	 * @param decryptor 解密器。处理IT到IS类型。
	 */
	public void setDecryptor(Decryptor<IT, IS> decryptor) {
		this.decryptor = decryptor;
	}

	/**
	 * 设置加密器。
	 * @param encryptor 加密器。处理IS到IT类型。
	 */
	public void setEncryptor(Encryptor<IS, IT> encryptor) {
		this.encryptor = encryptor;
	}

}
