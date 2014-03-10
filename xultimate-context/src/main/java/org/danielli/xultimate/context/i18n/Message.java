package org.danielli.xultimate.context.i18n;

/**
 * 消息体。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class Message {
	
	/** 消息类型 */
	protected MessageType type;
	
	/** 消息内容 */
	protected String content;
	
	public Message(MessageType type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * 获取消息类型。
	 * 
	 * @return 消息类型。
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * 获取消息内容。
	 * 
	 * @return 消息内容。
	 */
	public String getContent() {
		return content;
	}
}
