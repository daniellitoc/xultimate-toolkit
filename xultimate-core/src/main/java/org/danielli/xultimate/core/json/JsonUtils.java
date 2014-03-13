package org.danielli.xultimate.core.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.danielli.xultimate.core.json.fastjson.ValueTypeAdapter;
import org.danielli.xultimate.util.CharsetUtils;
import org.danielli.xultimate.util.io.IOUtils;

import com.alibaba.fastjson.JSON;

/**
 * JSON工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class JsonUtils {

	/**
	 * 用于返回Jsonp格式的数据。
	 * 
	 * @param jsCallbackFunctionName Javascript回调方法名称。
	 * @param jsonBody JSON体信息。
	 * @return Jsonp格式的数据。
	 */
	public static String toJsonp(String jsCallbackFunctionName, String jsonBody) {
		StringBuilder jsonpBuilder = new StringBuilder();
		jsonpBuilder.append(jsCallbackFunctionName);
		jsonpBuilder.append("(");
		jsonpBuilder.append(jsonBody);
		jsonpBuilder.append(")");
		return jsonpBuilder.toString();
	}
	
	/**
     * Method that can be used to serialize any Java value as
     * a String. Functionally equivalent to calling
     * {@link #writeValue(Writer,Object)} with {@link java.io.StringWriter}
     * and constructing String, but more efficient.
     */
	public static <T> String writeValueAsString(T value) {
		try {
			return JSON.toJSONString(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
	/**
     * Method that can be used to serialize any Java value as
     * a byte array. Functionally equivalent to calling
     * {@link #writeValue(Writer,Object)} with {@link java.io.ByteArrayOutputStream}
     * and getting bytes, but more efficient.
     * Encoding used will be UTF-8.
     */
	public static <T> byte[] writeValueAsBytes(T value) throws JSONException {
		try {
			return JSON.toJSONBytes(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
	/**
     * Method that can be used to serialize any Java value as
     * JSON output, using output stream provided (using encoding
     * {@link CharsetUtils#UTF_8}).
     */
	public static <T> void writeValue(OutputStream out, T value) throws JSONException {
		try {
			IOUtils.write(JSON.toJSONBytes(value), out);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
    /**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	public static <T> T readValue(String content, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(content, new ValueTypeAdapter<>(valueType));
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	public static <T> T readValue(byte[] src, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(src, valueType.getType());
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	public static <T> T readValue(InputStream src, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(IOUtils.toByteArray(src), valueType.getType());
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
}
