package org.danielli.xultimate.util.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

import org.danielli.xultimate.util.io.IOUtils;

/**
 * 加密工具类。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see AsymmetricAlgorithms
 * @see SymmetricAlgorithms
 * @see Cipher
 */
public class CipherUtils {
	
	/**
	 * 使用指定的密钥初始化加密器，并对数据进行加密。
	 * 
	 * @param cipher 加密器。
	 * @param key 密钥。
	 * @param data 加密数据。
	 * @return 加密后的数据。
	 */
    public static byte[] encrypt(Cipher cipher, Key key, byte[] data) {
    	try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			 throw new CryptoException(e.getMessage(), e);
		}
    }
    
	/**
	 * 使用指定的密钥初始化加密器，并对数据进行解密。
	 * 
	 * @param cipher 加密器。
	 * @param key 密钥。
	 * @param data 解密数据。
	 * @return 解密后的数据。
	 */
    public static byte[] decrypt(Cipher cipher, Key key, byte[] data) {
    	try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
		} catch (Exception e) {
			 throw new CryptoException(e.getMessage(), e);
		}
    }
    
    private static void crypt(InputStream input, OutputStream out, Cipher cipher) throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
    	int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(blockSize);

		int inLength;
		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];
		while ((inLength = IOUtils.read(input, inBytes)) != 0) {
			int outLength = cipher.update(inBytes, 0, inLength, outBytes);
			out.write(outBytes, 0, outLength);
		}
		IOUtils.write(cipher.doFinal(), out);
    }
    
	/**
	 * 使用指定的密钥初始化加密器，并对数据进行加密。
	 * 
	 * @param cipher 加密器。
	 * @param key 密钥。
	 * @param data 加密数据流。
	 * @param out 加密后的数据流。
	 */
    public static void encrypt(Cipher cipher, Key key, InputStream data, OutputStream out) throws CryptoException {
   	 	try {
   	 		cipher.init(Cipher.ENCRYPT_MODE, key);
   	 		crypt(data, out, cipher);
	   	 } catch (Exception e) {
	   		 throw new CryptoException(e.getMessage(), e);
	   	 }
   }
    
	/**
	 * 使用指定的密钥初始化加密器，并对数据进行解密。
	 * 
	 * @param cipher 加密器。
	 * @param key 密钥。
	 * @param data 解密数据流。
	 * @param out 解密后的数据流。
	 */
   public static void decrypt(Cipher cipher, Key key, InputStream data, OutputStream out) throws CryptoException {
   	 	try {
   	 		cipher.init(Cipher.DECRYPT_MODE, key);
   	 		crypt(data, out, cipher);
	   	 } catch (Exception e) {
	   		 throw new CryptoException(e.getMessage(), e);
	   	 }
   }
   
	/**
	 * 使用指定的公钥初始化加密器，并对密钥进行加密。
	 * 
	 * @param cipher 加密器。
	 * @param publicKey 公钥。
	 * @param key 加密密钥。
	 * @return 加密后的数据。
	 */
   public static byte[] wrap(Cipher cipher, PublicKey publicKey, Key key) {
   		try {
			cipher.init(Cipher.WRAP_MODE, publicKey);
			return cipher.wrap(key);
		} catch (Exception e) {
			 throw new CryptoException(e.getMessage(), e);
		}
   }
   
	/**
	 * 使用指定的私钥初始化加密器，并对数据进行解密。
	 * 
	 * @param cipher 加密器。
	 * @param privateKey 私钥。
	 * @param wrappedKey 解密的数据。
	 * @return 密钥。
	 */
   public static Key unwrap(Cipher cipher, PrivateKey privateKey, byte[] wrappedKey, SymmetricAlgorithms wrappedKeyAlgorithm) {
  		try {
			cipher.init(Cipher.UNWRAP_MODE, privateKey);
			return cipher.unwrap(wrappedKey, wrappedKeyAlgorithm.name(), Cipher.SECRET_KEY);
		} catch (Exception e) {
			 throw new CryptoException(e.getMessage(), e);
		}
  }
}
