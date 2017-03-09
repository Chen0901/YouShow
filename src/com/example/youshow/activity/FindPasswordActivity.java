package com.example.youshow.activity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.youshow.R;
import com.example.youshow.bean.User;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.NetHelper;
import com.example.youshow.common.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * 找回密码界面
 * @author cyl
 *
 */
public class FindPasswordActivity extends Activity {
	private Button setPwd, getCode; // 重置密码、获取验证码按钮
	private EditText userId, password, checkCode; // 用户名(手机号)、密码、验证码编辑框
	private String uId, pwd, code; // 用户名(手机号)、密码、验证码
	private TextView goLogin; // 前往登录界面文本
	private int i = 60; // 再次获取验证码的间隔时间
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_pwdfind);
		getWindow().setBackgroundDrawableResource(R.drawable.background);
		init();
		hideKeyBoard();
		ClickEvent();
	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				getCode.setText("(" + i + ")");
			} else if (msg.what == -8) {
				getCode.setText("GetCode!");
				getCode.setClickable(true);
				i = 60;
			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						// 注册用户
						UserManager manager = new UserManager(
								FindPasswordActivity.this);
						User user = new User(uId, pwd);
						if (manager.updatePwd(user) > 0) {// 重置成功
							CommonFunction.toastShow("密码重置成功",
									FindPasswordActivity.this);
							goLogin();
						} else {// 插入失败
							CommonFunction.toastShow("密码重置失败,请联系数据库管理员稍后再试!",
									FindPasswordActivity.this);
						}
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						CommonFunction.toastShow("验证码已经发送",
								FindPasswordActivity.this);
					} else {
						((Throwable) data).printStackTrace();
					}
				} else {
					// 短信注册失败后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功

						CommonFunction.toastShow("验证码验证失败,请仔细检查您的手机号与验证码是否正确！",
								FindPasswordActivity.this);
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						CommonFunction.toastShow("验证码发送失败,请检查您的网络是否正常!",
								FindPasswordActivity.this);
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};
	
	/**
	 * 点击事件
	 */
	private void ClickEvent() {
		// 点击跳转至登录界面
		goLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goLogin();
			}
		});
		// 点击获取验证码
		getCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 获取手机号
				uId = userId.getText().toString().trim();
				// 检测手机号格式
				if (CommonFunction.checkPhoneNum(uId)) {
					// 检测网络状态
					if (!NetHelper.checkNetWork(FindPasswordActivity.this)) {// 没有连接网络
						return;
					}
					// 检测此手机号是否存在
					if (!CommonFunction.userIsExistOrNot(new User(uId),
							FindPasswordActivity.this)) {
						CommonFunction.toastShow("不存在此手机号码的用户!",
								FindPasswordActivity.this);
					} else {
						// 通过sdk发送短信验证
						SMSSDK.getVerificationCode("86", uId);
						// 把按钮变成不可点击，并且显示倒计时（正在获取）
						getCode.setClickable(false);
						getCode.setText("(" + i + ")");
						new Thread(new Runnable() {
							@Override
							public void run() {
								for (; i > 0; i--) {
									handler.sendEmptyMessage(-9);
									if (i <= 0) {
										break;
									}
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								handler.sendEmptyMessage(-8);
							}
						}).start();
					}
				} else {
					CommonFunction.toastShow("请输入正确的手机号码格式!",
							FindPasswordActivity.this);
				}
			}
		});
		// 获取验证码信息,提交验证并注册
		setPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				uId = userId.getText().toString().trim(); // 手机号
				pwd = password.getText().toString().trim(); // 密码
				code = checkCode.getText().toString().trim(); // 验证码
				// 如果手机号、密码、验证码不为空再提交验证
				if (!"".equals(uId) && !"".equals(pwd) && !"".equals(code)) {
					// 提交短信验证
					SMSSDK.submitVerificationCode("86", uId, code);
				} else {
					CommonFunction
							.toastShow("请将信息填写完整!", FindPasswordActivity.this);
				}
			}
		});
	}

	/**
	 * 跳转至登录界面
	 */
	private void goLogin() {
		Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
		startActivity(intent);
		FindPasswordActivity.this.finish();
		// 淡入淡出
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyBoard() {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_pwdfind);
		rl.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		setPwd = (Button) findViewById(R.id.btn_setPwd);
		getCode = (Button) findViewById(R.id.getCheckCode);
		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		checkCode = (EditText) findViewById(R.id.checkCode);
		goLogin = (TextView) findViewById(R.id.goLoginText);
		// 启动短信验证sdk
		SMSSDK.initSDK(this, "115a61413e574",
				"692e6c5104ca5da6b82efe8d6cd133ac");
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 注销SMSSDK的Handle消息处理
		SMSSDK.unregisterAllEventHandler();
	}
	
}
