package org.danielli.xultimate.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.ServletContextResource;

/**
 * Spring Framework中Resource的工具类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see Resource
 */
public class ResourceUtils {
	
	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	/**
	 * 创建ClassPathresource实例。
	 * 
	 * @param path 资源路径。
	 * @see ClassPathResource
	 */
	public static ClassPathResource getClassPathResource(String path) {
		return new ClassPathResource(path);
	}
	
	/**
	 * 创建InputStreamResource实例。
	 * 
	 * @param inputStream 输入流。
	 * @see InputStreamResource
	 */
	public static InputStreamResource getInputStreamResource(InputStream inputStream) {
		return new InputStreamResource(inputStream);
	}
	
	/**
	 * 创建ByteArrayResource实例。
	 * 
	 * @param byteArray 字节数组。
	 * @see ByteArrayResource
	 */
	public static ByteArrayResource getByteArrayResource(byte[] byteArray) {
		return new ByteArrayResource(byteArray);
	}
	
	/**
	 * 创建FileSystemResource实例。
	 * 
	 * @param path 资源路径。
	 * @see FileSystemResource
	 */
	public static FileSystemResource getFileSystemResource(String path) {
		return new FileSystemResource(path);
	}
	
	/**
	 * 创建ServletContextResource实例。
	 * 
	 * @param servletContext Servlet上下文。
	 * @param path 资源路径。
	 * @see ServletContextResource
	 */
	public static ServletContextResource getServletContextResource(ServletContext servletContext, String path) {
		return new ServletContextResource(servletContext, path);
	}
	
	/**
	 * 创建UrlResource实例。
	 * 
	 * @param path 资源路径。
	 * @see UrlResource
	 */
	public static UrlResource getUrlResource(String path) throws MalformedURLException {
		return new UrlResource(path);
	}
	
	/**
	 * 获取Resource实例。
	 * 
	 * @param location 资源路径。
	 * @see PathMatchingResourcePatternResolver#getResource(String)
	 */
	public static Resource getResource(String location) {
		return resourcePatternResolver.getResource(location);
	}
	
	/**
	 * 获取Resource数组。
	 * 
	 * @param location 资源路径模式。
	 * @see PathMatchingResourcePatternResolver#getResources(String)
	 */
	public static Resource[] getResources(String locationPattern) throws IOException {
		return resourcePatternResolver.getResources(locationPattern);
	}
	
	/**
	 * 创建EncodedResource实例。
	 * 
	 * @param resource 资源。
	 * @param encoding 编码格式。
	 * @see EncodedResource
	 */
	public static EncodedResource encodeResource(Resource resource, String encoding) {
		return new EncodedResource(resource, encoding);
	}
}
