package com.example.youshow.bean;

/**
 * 用户实体类
 * 
 * @author cyl
 *
 */
public class User {
	private String userId; // 用户名(手机号),主键
	private String password; // 密码

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
