package com.example.youshow.bean;

/**
 * ������Ϣ��ʵ����
 * @author cyl
 *
 */
public class Message {
	private String text; // ��������
	private int type; // ���ͷ�����շ��ı�ʶ

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
