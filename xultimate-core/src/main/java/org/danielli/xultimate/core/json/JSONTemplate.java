package org.danielli.xultimate.core.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import org.danielli.xultimate.core.json.JSONException;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.util.CharsetUtils;

/**
 * JSON模板类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface JSONTemplate {

	/**
     * Method that can be used to serialize any Java value as
     * a String. Functionally equivalent to calling
     * {@link #writeValue(Writer,Object)} with {@link java.io.StringWriter}
     * and constructing String, but more efficient.
     */
	<T> String writeValueAsString(T value) throws JSONException;

	/**
     * Method that can be used to serialize any Java value as
     * a byte array. Functionally equivalent to calling
     * {@link #writeValue(Writer,Object)} with {@link java.io.ByteArrayOutputStream}
     * and getting bytes, but more efficient.
     * Encoding used will be UTF-8.
     */
	<T> byte[] writeValueAsBytes(T value) throws JSONException;
	
	/**
     * Method that can be used to serialize any Java value as
     * JSON output, using output stream provided (using encoding
     * {@link CharsetUtils#UTF_8}).
     */
	<T> void writeValue(OutputStream out, T value) throws JSONException;

    /**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(String content, ValueType<T> valueType) throws JSONException;
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(byte[] src, ValueType<T> valueType) throws JSONException;
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(InputStream src, ValueType<T> valueType) throws JSONException;
	
    /**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(String content, Class<T> clazz) throws JSONException;
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(byte[] src, Class<T> clazz) throws JSONException;
	
	/**
     * Method that can be used to serialize any JSON output as
     * Java value.
     */
	<T> T readValue(InputStream src, Class<T> clazz) throws JSONException;
}
