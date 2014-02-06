package org.danielli.xultimate.context.image.awt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JLabel;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageInfoException;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageFormat;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.context.image.model.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.context.image.model.ImageSize;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.io.FilenameUtils;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * 图片工具。
 * 
 * @author Daniel Li
 * @since JDK 1.7
 */
public class ImageUtils {
	/**
	 * 获取图片信息。
	 * 
	 * @param imageFile
	 * 				图片文件。
	 * @return		图片信息。
	 */
	public static ImageInfo getImageInfo(File imageFile) throws ImageInfoException {
		Assert.notNull(imageFile, "this argument imageFile is required; it must not be null");
		ImageInputStream imageInputStream = null;
		ImageReader imageReader = null;
		try {
			imageInputStream = ImageIO.createImageInputStream(imageFile);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
			imageReader = iterator.next();
			imageReader.setInput(imageInputStream, true);
			Integer imageWidth = imageReader.getWidth(0);
			Integer imageHeight = imageReader.getHeight(0);
			String imageFormat = imageReader.getFormatName(); 
			return new ImageInfo(imageWidth, imageHeight, ImageFormat.valueOf(StringUtils.upperCase(imageFormat)));
		} catch (Exception e) {
			throw new ImageInfoException(e.getMessage(), e);
		} finally {
			if (imageReader != null) {
				imageReader.dispose();
			}
			IOUtils.closeQuietly(imageInputStream);
		}
	}
	
	/**
	 * 通过图片文件创建缓冲图片对象。
	 * 
	 * @param imageFile 
	 * 				图片文件。
	 * @return		图片文件的缓冲图片对象。
	 */
	public static BufferedImage createBufferedImage(File imageFile) throws ImageInfoException {
		Assert.notNull(imageFile, "this argument imageFile is required; it must not be null");
		try {
			return ImageIO.read(imageFile);
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取图片文件的扩展名。
	 * 
	 * @param imageFile
	 * 				图片文件。与图片文件是否存在无关。
	 * @return		图片文件的扩展名。
	 */
	public static ImageFormat getExtension(File imageFile) throws ImageInfoException {
		Assert.notNull(imageFile, "this argument imageFile is required; it must not be null");
		String extension = StringUtils.upperCase(FilenameUtils.getExtension(imageFile.getName()));
		ImageFormat[] imageFormats = ImageFormat.values();
		for (ImageFormat imageFormat : imageFormats) {
			if (ArrayUtils.contains(imageFormat.getExtensions(), extension)) {
				return imageFormat;
			}	
		}
		throw new ImageInfoException("the argument imageFile must have correct image extension");
	}
	
	/**
	 * 获取图片文件的扩展名。
	 * 
	 * @param imageFile
	 * 				图片文件。与图片文件是否存在无关。
	 * @return		图片文件的扩展名。
	 */
	public static ImageFormat getExtension(String imageFilePath) throws ImageInfoException {
		Assert.notNull(imageFilePath, "this argument imageFile is required; it must not be null");
		String extension = StringUtils.upperCase(FilenameUtils.getExtension(imageFilePath));
		ImageFormat[] imageFormats = ImageFormat.values();
		for (ImageFormat imageFormat : imageFormats) {
			if (ArrayUtils.contains(imageFormat.getExtensions(), extension)) {
				return imageFormat;
			}	
		}
		throw new ImageInfoException("the argument imageFile must have correct image extension");
	}
	
	/**
	 * 输出缓冲图片对象到目标图片文件。
	 * 
	 * @param srcBufferedImage
	 * 				原缓冲图片对象。
	 * @param defaultFormat
	 * 				默认格式。若目标图片文件的格式为非标准图片格式，将使用默认格式。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param quality
	 * 				目标图片品质(取值范围: 0 - 100)
	 */
	public static void writeBufferedImage(BufferedImage srcBufferedImage, ImageFormat defaultFormat, File destImageFile, Integer quality) throws ImageInfoException {
		Assert.notNull(srcBufferedImage, "this argument srcBufferedImage is required; it must not be null");
		Assert.notNull(defaultFormat, "this argument defaultFormat is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		Assert.isTrue(quality >= 0 && quality <= 100, "this argument quality is required; it must between 0 and 100");
		
		ImageFormat imageFormat;
		try {
			imageFormat = getExtension(destImageFile);
		} catch (ImageInfoException e) {
			imageFormat = defaultFormat;
		}
		
		try {
			ImageIO.write(srcBufferedImage, imageFormat.name(), destImageFile);
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 剪裁图片。
	 * @param srcBufferedImage
	 * 				原缓冲图片对象。 
	 * @param imageGeometryCoordinate
	 * 				目标缓冲图片对象的尺寸。
	 * @param imageGeometryCoordinate
	 * 				目标缓冲图片在原缓冲图片对象上剪裁坐标。
	 * @param backgroundColor
	 * 				背景颜色。
	 * @return		目标缓冲图片对象。
	 */
	public static BufferedImage cropImage(BufferedImage srcBufferedImage, ImageGeometryCoordinate imageGeometryCoordinate, Color backgroundColor) throws ImageInfoException {
		ImageSize imageSize = imageGeometryCoordinate.getImageSize();
		ImageCoordinate imageCoordinate = imageGeometryCoordinate.getImageCoordinate();
		Assert.notNull(srcBufferedImage, "this srcBufferedImage is required; it must not be null");
		Assert.notNull(imageSize, "this size is required; it must not be null");
		
		try {
			Integer destWidth = imageSize.getWidth();
			Integer destHeight = imageSize.getHeight();
			
			if (destWidth + imageCoordinate.getLatitude() > srcBufferedImage.getWidth()) {
				destWidth = srcBufferedImage.getWidth() - imageCoordinate.getLatitude();
			}
			
			if (destHeight + imageCoordinate.getLongitude() > srcBufferedImage.getHeight()) {
				destHeight = srcBufferedImage.getHeight() - imageCoordinate.getLongitude();
			}
			
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(backgroundColor);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			graphics2D.drawImage(srcBufferedImage.getSubimage(imageCoordinate.getLatitude(), imageCoordinate.getLongitude(), destWidth, destHeight), 0, 0, null);
			graphics2D.dispose();
			return destBufferedImage;
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 按尺寸缩放图片。
	 * 
	 * @param srcBufferedImage
	 * 				原缓冲图片。
	 * @param imageGeometry
	 * 				图片几何方位。
	 * @param backgroundColor
	 * 				背景颜色。
	 * @return		目标缓冲图片。
	 */
	public static BufferedImage resizeImage(BufferedImage srcBufferedImage, ImageGeometry imageGeometry, Color backgroundColor) throws ImageInfoException {
		Assert.notNull(srcBufferedImage, "this srcBufferedImage is required; it must not be null");
		Assert.notNull(imageGeometry.getImageSize(), "this size is required; it must not be null");
		try {
			ImageSize destImageSize = imageGeometry.convertImageSize(new ImageSize(srcBufferedImage));
			Integer destWidth = destImageSize.getWidth();
			Integer destHeight = destImageSize.getHeight();
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(backgroundColor);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH), 0, 0, null);
			graphics2D.dispose();
			return destBufferedImage;
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 按尺寸缩放图片。进行缩放后在指定方位以指定宽度与高度截取图片。
	 * 
	 * @param srcBufferedImage
	 * 				原缓冲图片。
	 * @param imageGeometry
	 * 				图片几何方位。包含图片尺寸和几何操作。
	 * @param gravity
	 * 				截取图片位置。
	 * @param backgroundColor
	 * 				背景颜色。
	 * @return		目标缓冲图片。
	 */
	public static BufferedImage resizeImage(BufferedImage srcBufferedImage, ImageGeometry imageGeometry, Gravity gravity, Color backgroundColor) throws ImageInfoException {
		Assert.notNull(srcBufferedImage, "this srcBufferedImage is required; it must not be null");
		Assert.notNull(imageGeometry.getImageSize(), "this size is required; it must not be null");
		
		try {
			ImageSize srcImageSize = new ImageSize(srcBufferedImage);
			ImageSize destImageSize = new ImageGeometry(imageGeometry.getImageSize(), GeometryOperator.Emphasize).convertImageSize(srcImageSize);
			Integer destWidth = destImageSize.getWidth();
			Integer destHeight = destImageSize.getHeight();
			ImageSize destImageResizeSize = imageGeometry.convertImageSize(srcImageSize);
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(backgroundColor);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			ImageCoordinate coordinate = new ImageCoordinate(destImageSize, destImageResizeSize, gravity);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(destImageResizeSize.getWidth(), destImageResizeSize.getHeight(), Image.SCALE_SMOOTH), coordinate.getLatitude(), coordinate.getLongitude(), null);
			graphics2D.dispose();
			return destBufferedImage;
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加水印图片。
	 * 
	 * @param srcBufferedImage
	 * 				原缓冲图片对象。 
	 * @param watermarkBufferedImage
	 * 				水印缓冲图片对象。　
	 * @param imageCoordinate
	 * 				小印缓冲图片对象的坐标。    
	 * @param alpha
	 * 				水印图片透明度
	 * @param backgroundColor
	 * 				背景颜色。
	 * @return		目标缓冲图片对象。
	 */
	public static BufferedImage addWatermarkImage(BufferedImage srcBufferedImage, BufferedImage watermarkBufferedImage, ImageCoordinate imageCoordinate, int alpha, Color backgroundColor) throws ImageInfoException {
		Assert.notNull(srcBufferedImage, "this srcBufferedImage is required; it must not be null");
		Assert.notNull(watermarkBufferedImage, "this watermarkBufferedImage is required; it must not be null");
		Assert.notNull(imageCoordinate, "this imageCoordinate is required; it must not be null");
		Assert.isTrue(alpha >= 0 && alpha <= 100, "this argument alpha is required; it must between 0 and 100");
		
		try {
			ImageSize srcImageSize = new ImageSize(srcBufferedImage);
			
			BufferedImage destBufferedImage = new BufferedImage(srcImageSize.getWidth(), srcImageSize.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(backgroundColor);
			graphics2D.clearRect(0, 0, srcImageSize.getWidth(), srcImageSize.getHeight());
			graphics2D.drawImage(srcBufferedImage, 0, 0, null);
			graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha / 100.0F));

			ImageSize watermarkImageSize = new ImageSize(watermarkBufferedImage);
			graphics2D.drawImage(watermarkBufferedImage, imageCoordinate.getLatitude(), imageCoordinate.getLongitude(), watermarkImageSize.getWidth(), watermarkImageSize.getHeight(), null);
			graphics2D.dispose();
			return destBufferedImage;
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取图片信息。为图片内容使用字体所占用的宽度与高度
	 * 
	 * @param message
	 * 				图片内容。
	 * @param font
	 * 				图片内容使用字体。
	 * @return		图片信息。
	 */
	public static ImageSize getFontAsImageWidth(String message, Font font) {
		Assert.notNull(font, "this font is required; it must not be null");
		Assert.hasLength(message, "this message argument must have length; it must not be null or empty");
		FontMetrics fm = new JLabel().getFontMetrics(font);
		return new ImageSize(fm.charsWidth(message.toCharArray(), 0, message.length()), fm.getHeight());
	}
	
	/**
	 * 创建透明缓冲图片对象。图片内容以指定内容填冲。
	 * 
	 * @param imageWidth
	 * 				缓冲图片对象的宽度。
	 * @param imageHeight
	 * 				缓冲图片对象的高度。
	 * @param message
	 * 				缓冲图片对象显示的内容。
	 * @param messageFont
	 * 				缓冲图片对象显示的内容的字体。
	 * @param messageColor
	 * 				缓冲图片对象显示的内容的字体的颜色。
	 * @return		透明缓冲图片对象。
	 */
	public static BufferedImage createTransluentBufferedImage(ImageSize imageSize, String message, Font messageFont, Color messageColor) {
		Integer imageWidth = imageSize.getWidth();
		Integer imageHeight = imageSize.getHeight();
		
		BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		graphics2D.fillRect(0, 0, imageWidth, imageHeight);
		graphics2D.dispose();
		
		BufferedImage finalBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
		Graphics2D graphics = (Graphics2D) finalBufferedImage.getGraphics();
		graphics.drawImage(bufferedImage, 0, 0, finalBufferedImage.getWidth(), finalBufferedImage.getHeight(), null);
		graphics.setColor(messageColor);
		graphics.setFont(messageFont);
		graphics.drawString(message, 0, 0 + messageFont.getSize());
		graphics.dispose();
		return finalBufferedImage;
	}
	
	/**
	 * 转换颜色为十六进制代码
	 * 
	 * @param color
	 *            颜色
	 * @return 十六进制代码
	 */
	public static String toHexEncoding(Color color) {
		String R, G, B;
		StringBuilder stringBuilder = new StringBuilder();
		R = Integer.toHexString(color.getRed());
		G = Integer.toHexString(color.getGreen());
		B = Integer.toHexString(color.getBlue());
		R = R.length() == 1 ? "0" + R : R;
		G = G.length() == 1 ? "0" + G : G;
		B = B.length() == 1 ? "0" + B : B;
		stringBuilder.append("#");
		stringBuilder.append(R);
		stringBuilder.append(G);
		stringBuilder.append(B);
		return stringBuilder.toString();
	}
}
