package org.danielli.xultimate.core.json;

import java.io.Writer;

import org.danielli.xultimate.core.json.fastjson.FastJSONTemplate;

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
	public static <T> String writeValueAsString(T value) throws JSONException {
		return FastJSONTemplate.INSTANCE.writeValueAsString(value);
	}
	
    /**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	public static <T> T readValue(String content, ValueType<T> valueType) throws JSONException {
		return FastJSONTemplate.INSTANCE.readValue(content, valueType);
	}
}
