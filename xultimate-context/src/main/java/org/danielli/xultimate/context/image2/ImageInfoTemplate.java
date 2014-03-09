package org.danielli.xultimate.context.image2;

import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * 图片信息模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageInfoTemplate {
	
	/**
	 * 转换图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 */
	void convertImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource) throws ImageException;
}
