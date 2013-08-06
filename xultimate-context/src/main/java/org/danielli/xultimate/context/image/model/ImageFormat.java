package org.danielli.xultimate.context.image.model;

/**
 * 图片格式。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public enum ImageFormat {
	JPEG("JPG", "JPEG"), 
	GIF("GIF"),
	BMP("BMP"),
	PNG("PNG");
	
	private String[] extensions;
	
	private ImageFormat(String... extensions) {
		this.extensions = extensions;
	}
	
	public String[] getExtensions() {
		return extensions;
	}
}
