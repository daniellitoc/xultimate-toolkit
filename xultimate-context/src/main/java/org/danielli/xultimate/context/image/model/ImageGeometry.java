package org.danielli.xultimate.context.image.model;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.util.Assert;

/**
 * 图片几何方位。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageGeometry {
	
	private ImageSize imageSize;
	
	private GeometryOperator operator;
	
	/**
	 * 创建图片几何。
	 * 
	 * @param imageSize
	 * 				图片尺寸。
	 * @param operator
	 * 				几何操作。
	 */
	public ImageGeometry(ImageSize imageSize, GeometryOperator operator) {
		Assert.notNull(imageSize, "this argument coordinate is required; it must not be null");
		Assert.notNull(operator, "this argument operator is required; it must not be null");
		this.imageSize = imageSize;
		this.operator = operator;
	}
	
	/**
	 * 根据原图片尺寸转换图片尺寸。
	 * 
	 * @param srcImageSize
	 * 				原图片尺寸。
	 * @return		图片尺寸。
	 */
	public ImageSize convertImageSize(ImageSize srcImageSize) {
		ImageSize destImageSize = this.imageSize;;
		if (operator == GeometryOperator.Emphasize) {
			return destImageSize;
		}
		
		Integer destWidth = destImageSize.getWidth();
		Integer destHeight = destImageSize.getHeight();
		if (destWidth == null && destHeight == null) {
			return srcImageSize;
		}

		Integer srcWidth = srcImageSize.getWidth();
		Integer srcHeight = srcImageSize.getHeight();
		
		if (operator == GeometryOperator.Minimum) {
			if (destWidth == null) {
				destWidth = (int) Math.round(destHeight * 1.0 / srcHeight * srcWidth);
			} else if (destHeight == null) {
				destHeight = (int) Math.round(destWidth * 1.0 / srcWidth * srcHeight);
			}
			
			if (destHeight >= destWidth) {
				destHeight = (int) Math.round(destWidth * 1.0 / srcWidth * srcHeight);
			} else {
				destWidth = (int) Math.round(destHeight * 1.0 / srcHeight * srcWidth);
			}
		} else if (operator == GeometryOperator.Maximum) {
			if (destHeight <= destWidth) {
				destHeight = (int) Math.round(destWidth * 1.0 / srcWidth * srcHeight);
			} else {
				destWidth = (int) Math.round(destHeight * 1.0 / srcHeight * srcWidth);
			}
			

		} else if (operator == GeometryOperator.Shrink) {
			if (srcWidth >= destWidth || srcHeight >= destHeight) {
				if (destHeight >= destWidth) {
					destHeight = (int) Math.round(destWidth * 1.0 / srcWidth * srcHeight);
				} else {
					destWidth = (int) Math.round(destHeight * 1.0 / srcHeight * srcWidth);
				}
			} else {
				return destImageSize;
			}
		} else if (operator == GeometryOperator.Enlarge) {
			if (srcWidth <= destWidth || srcHeight <= destHeight) {
				if (destHeight <= destWidth) {
					destHeight = (int) Math.round(destWidth * 1.0 / srcWidth * srcHeight);
				} else {
					destWidth = (int) Math.round(destHeight * 1.0 / srcHeight * srcWidth);
				}
			} else {
				return destImageSize;
			}
		} else {
			throw new ImageException("this field operator must have correct value");
		}
		return new ImageSize(destWidth, destHeight);
	}
	
	/**
	 * 获取几何操作。
	 * 
	 * @return		几何操作。
	 */
	public GeometryOperator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(imageSize);
		if (imageSize.getWidth() != null && imageSize.getHeight() != null && !GeometryOperator.Minimum.equals(operator)) {
	    	builder.append(operator.getSpecial());
	    }
		return builder.toString();
	}
}
