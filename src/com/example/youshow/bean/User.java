package com.example.youshow.bean;

/**
 * �û�ʵ����
 * 
 * @author cyl
 *
 */
public class User {
	private String userId; // �û���(�ֻ���),����
	private String password; // ����

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String userId) {
		super();
		this.userId = userId;
	}

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public User() {
	}

}
