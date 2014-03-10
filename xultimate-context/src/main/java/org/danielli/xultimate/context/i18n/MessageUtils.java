package org.danielli.xultimate.context.i18n;

import java.util.Locale;

import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.context.util.BeanFactoryContext;

public class MessageUtils {

	/**
	 * 返回指定消息类型的消息实例。
	 * 
	 * @param messageType 消息类型。
	 * @param code 对应的Properties中的KEY。
	 * @param args 消息参数。
	 * @return 消息实例。
	 */
	public static Message valueOf(MessageType messageType, String code, Object... args) {
		String content = ApplicationContextUtils.getMessage(BeanFactoryContext.currentApplicationContext(), Locale.getDefault(), code, args);
		return new Message(messageType, content);
	}
	
	/**
	 * 返回成功类型的消息实例。
	 * 
	 * @param code 对应的Properties中的KEY。
	 * @param args 消息参数。
	 * @return 消息实例。
	 */
	public static Message success(String code, Object... args) {
		return valueOf(MessageType.success, code, args);
	}
	
	/**
	 * 返回错误类型的消息实例。
	 * 
	 * @param code 对应的Properties中的KEY。
	 * @param args 消息参数。
	 * @return 消息实例。
	 */
	public static Message error(String code, Object... args) {
		return valueOf(MessageType.error, code, args);
	}
	
	/**
	 * 返回警告类型的消息实例。
	 * 
	 * @param code 对应的Properties中的KEY。
	 * @param args 消息参数。
	 * @return 消息实例。
	 */
	public static Message warn(String code, Object... args) {
		return valueOf(MessageType.warn, code, args);
	}
}
