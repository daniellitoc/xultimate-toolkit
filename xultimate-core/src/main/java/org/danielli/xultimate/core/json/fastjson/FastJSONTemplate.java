package org.danielli.xultimate.core.json.fastjson;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.json.JSONException;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.core.json.fastjson.ValueTypeAdapter;
import org.danielli.xultimate.util.io.IOUtils;

import com.alibaba.fastjson.JSON;

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
		try {
			return JSON.toJSONString(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> byte[] writeValueAsBytes(T value) throws JSONException {
		try {
			return JSON.toJSONBytes(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> void writeValue(OutputStream out, T value) throws JSONException {
		try {
			IOUtils.write(JSON.toJSONBytes(value), out);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(String content, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(content, new ValueTypeAdapter<>(valueType));
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(byte[] src, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(src, valueType.getType());
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(InputStream src, ValueType<T> valueType) throws JSONException {
		try {
			return JSON.parseObject(IOUtils.toByteArray(src), valueType.getType());
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
}
