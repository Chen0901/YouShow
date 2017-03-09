package com.example.youshow.bean;

/**
 * 聊天信息的实体类
 * @author cyl
 *
 */
public class Message {
	private String text; // 聊天内容
	private int type; // 发送方或接收方的标识

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Message(String text, int type) {
		super();
		this.text = text;
		this.type = type;
	}

	public Message() {
		super();
	}

}
