package com.example.youshow.activity;

import com.example.youshow.R;
import com.example.youshow.bean.User;
import com.example.youshow.common.CommonFunction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 登录
 * 
 * @author cyl
 *
 */
public class LoginActivity extends Activity {
	private EditText userId, password; // 用户名(手机号)、密码编辑框
	private String uId, pwd; // 用户名(手机号)、密码
	private Button login; // 登录按钮
	private TextView goRegister,finPwd; // 前往注册界面文本

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		init();
		clickEvent();
		hideKeyBoard();
	}

	/**
	 * 点击事件
	 */
	private void clickEvent() {
		// 跳转至注册界面
		goRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				// 淡入淡出
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
		// 登录检测
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				uId = userId.getText().toString().trim();
				pwd = password.getText().toString().trim();
				if (CommonFunction.checkForLogin(new User(uId, pwd),
						LoginActivity.this)) {
					// 跳转至个人首页
					Intent intent = new Intent(LoginActivity.this,
							IndexActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
					// 淡入淡出
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				} else {
					CommonFunction.toastShow("登录失败,用户名或密码错误！",
							LoginActivity.this);
				}
			}
		});
		// 跳转至找回密码界面
		finPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						FindPasswordActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				// 淡入淡出
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.btn_login);
		goRegister = (TextView) findViewById(R.id.goRegisterText);
		finPwd = (TextView) findViewById(R.id.goFindPwdText);
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyBoard() {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_login);
		rl.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
	}
}
