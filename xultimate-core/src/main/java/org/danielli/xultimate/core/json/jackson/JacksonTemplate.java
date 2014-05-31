package org.danielli.xultimate.core.json.jackson;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.json.JSONException;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.core.json.ValueType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON模板类，使用Jackson实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see JSONTemplate
 */
public class JacksonTemplate implements JSONTemplate {

	/** 对象映射器 */
	private ObjectMapper objectMapper;
	
	/**
	 * 设置对象映射器。
	 * 
	 * @param objectMapper 对象映射器。
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public <T> String writeValueAsString(T value) throws JSONException {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> byte[] writeValueAsBytes(T value) throws JSONException {
		try {
			return objectMapper.writeValueAsBytes(value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> void writeValue(OutputStream out, T value) throws JSONException {
		try {
			objectMapper.writeValue(out, value);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(String content, ValueType<T> valueType) throws JSONException {
		try {
			return objectMapper.readValue(content, new ValueTypeAdapter<T>(valueType));
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(byte[] src, ValueType<T> valueType) throws JSONException {
		try {
			return objectMapper.readValue(src, new ValueTypeAdapter<T>(valueType));
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}

	@Override
	public <T> T readValue(InputStream src, ValueType<T> valueType) throws JSONException {
		try {
			return objectMapper.readValue(src, new ValueTypeAdapter<T>(valueType));
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		} 
	}
	
	@Override
	public <T> T readValue(byte[] src, Class<T> clazz) throws JSONException {
		try {
			return objectMapper.readValue(src, clazz);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	@Override
	public <T> T readValue(InputStream src, Class<T> clazz) throws JSONException {
		try {
			return objectMapper.readValue(src, clazz);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	@Override
	public <T> T readValue(String content, Class<T> clazz) throws JSONException {
		try {
			return objectMapper.readValue(content, clazz);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}

}
