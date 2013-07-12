package org.danielli.xultimate.context.crypto;

/**
 * 解密器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 */
public interface Decryptor<S, T> {
	
	/**
	 * 解密。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws DecryptorException 解密异常，在解密过程中出现的任何异常都会使用{@link DecryptorException}封装并抛出。
	 */
	T decrypt(S source) throws DecryptorException;
}
