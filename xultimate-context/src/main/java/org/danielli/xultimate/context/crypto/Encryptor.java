package org.danielli.xultimate.context.crypto;

/**
 * 加密器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 */
public interface Encryptor<S, T> {
	
	/**
	 * 加密。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws EncryptorException 编码异常，在编码过程中出现的任何异常都会使用{@link EncryptorException}封装并抛出。
	 */
	T encrypt(S source) throws EncryptorException;
}
