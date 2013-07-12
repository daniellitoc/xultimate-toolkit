package org.danielli.xultimate.util.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * 摘要工具类。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see MessageDigestAlgorithms
 * @see MessageDigest
 */
public class DigestUtils {
	
	/**
	 * 数据流缓冲大小。
	 */
	private static final int STREAM_BUFFER_LENGTH = 1024;
    
	/**
	 * 使用指定数据计算摘要。
	 * 
	 * @param digest 摘要算法。
	 * @param data 计算数据。
	 * @return 摘要值。
	 */
    public static byte[] digest(MessageDigest digest, byte[] data) {
    	return digest.digest(data);
    }
    
	/**
	 * 使用指定数据更新摘要。
	 * 
	 * @param messageDigest 摘要算法。
	 * @param valueToDigest 计算数据。
	 * @return 摘要算法。
	 */
    public static MessageDigest updateDigest(MessageDigest messageDigest, byte[] valueToDigest) {
   	 	messageDigest.update(valueToDigest);
        return messageDigest;
    }
    
	/**
	 * 使用指定数据计算摘要。
	 * 
	 * @param digest 摘要算法。
	 * @param data 计算数据流。
	 * @return 摘要值。
	 */
    public static byte[] digest(MessageDigest digest, InputStream data) throws CryptoException {
    	 try {
    		 data = IOUtils.toBufferedInputStream(data);
    		 byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
    		 int length;
    		 while ((length = IOUtils.read(data, buffer)) != 0) {
    			 digest.update(buffer, 0, length);
    		 }
             return digest.digest(); 
    	 } catch (IOException e) {
    		 throw new CryptoException(e.getMessage(), e);
    	 }
    }
   
    /**
     * 使用Hex算法将数据转为字符串。
     * 
     * @param data 数据。
     * @return 转换后的字符串。
     */
    public static String getHexString(byte[] data) {
    	return new String(Hex.encodeHex(data));
    }
    
    /**
     * 使用Hex算法将字符串转换为数据。
     * 
     * @param hexString 字符串
     * @return 数据。
     */
    public static byte[] fromHexString(String hexString) {
    	try {
    		return Hex.decodeHex(hexString.toCharArray());
    	} catch (DecoderException e) {
    		throw new IllegalArgumentException(e.getMessage(), e);
    	}
    }
    
	/**
	 * 使用指定数据计算摘要。
	 * 
	 * @param digest 摘要算法。
	 * @param data 计算数据。
	 * @return 摘要值。
	 */
    public static String digest(MessageDigestAlgorithms algorithms, String data) throws CryptoException {
    	MessageDigest digest = algorithms.getDigest();
    	return getHexString(digest(digest, StringUtils.getBytesUtf8(data)));
    }
}
