package org.danielli.xultimate.core.json;

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
}
