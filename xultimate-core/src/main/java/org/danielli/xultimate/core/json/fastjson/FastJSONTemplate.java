package org.danielli.xultimate.core.json.fastjson;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.json.JSONException;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.core.json.JsonUtils;
import org.danielli.xultimate.core.json.ValueType;

/**
 * JSON模板类，使用FastJSON实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see JSONTemplate
 */
public class FastJSONTemplate implements JSONTemplate {

	@Override
	public <T> String writeValueAsString(T value) throws JSONException {
		return JsonUtils.writeValueAsString(value);
	}
	
	@Override
	public <T> byte[] writeValueAsBytes(T value) throws JSONException {
		return JsonUtils.writeValueAsBytes(value);
	}

	@Override
	public <T> void writeValue(OutputStream out, T value) throws JSONException {
		JsonUtils.writeValue(out, value);
	}

	@Override
	public <T> T readValue(String content, ValueType<T> valueType) throws JSONException {
		return JsonUtils.readValue(content, valueType);
	}
	
	@Override
	public <T> T readValue(byte[] src, ValueType<T> valueType) throws JSONException {
		return JsonUtils.readValue(src, valueType);
	}

	@Override
	public <T> T readValue(InputStream src, ValueType<T> valueType) throws JSONException {
		return JsonUtils.readValue(src, valueType);
	}
}
