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
 * �һ��������
 * @author cyl
 *
 */
public class FindPasswordActivity extends Activity {
	private Button setPwd, getCode; // �������롢��ȡ��֤�밴ť
	private EditText userId, password, checkCode; // �û���(�ֻ���)�����롢��֤��༭��
	private String uId, pwd, code; // �û���(�ֻ���)�����롢��֤��
	private TextView goLogin; // ǰ����¼�����ı�
	private int i = 60; // �ٴλ�ȡ��֤��ļ��ʱ��
	
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
					// ����ע��ɹ�����ʾ
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�
						// ע���û�
						UserManager manager = new UserManager(
								FindPasswordActivity.this);
						User user = new User(uId, pwd);
						if (manager.updatePwd(user) > 0) {// ���óɹ�
							CommonFunction.toastShow("�������óɹ�",
									FindPasswordActivity.this);
							goLogin();
						} else {// ����ʧ��
							CommonFunction.toastShow("��������ʧ��,����ϵ���ݿ����Ա�Ժ�����!",
									FindPasswordActivity.this);
						}
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						CommonFunction.toastShow("��֤���Ѿ�����",
								FindPasswordActivity.this);
					} else {
						((Throwable) data).printStackTrace();
					}
				} else {
					// ����ע��ʧ�ܺ���ʾ
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// �ύ��֤��ɹ�

						CommonFunction.toastShow("��֤����֤ʧ��,����ϸ��������ֻ�������֤���Ƿ���ȷ��",
								FindPasswordActivity.this);
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						CommonFunction.toastShow("��֤�뷢��ʧ��,�������������Ƿ�����!",
								FindPasswordActivity.this);
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};
	
	/**
	 * ����¼�
	 */
	private void ClickEvent() {
		// �����ת����¼����
		goLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goLogin();
			}
		});
		// �����ȡ��֤��
		getCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��ȡ�ֻ���
				uId = userId.getText().toString().trim();
				// ����ֻ��Ÿ�ʽ
				if (CommonFunction.checkPhoneNum(uId)) {
					// �������״̬
					if (!NetHelper.checkNetWork(FindPasswordActivity.this)) {// û����������
						return;
					}
					// �����ֻ����Ƿ����
					if (!CommonFunction.userIsExistOrNot(new User(uId),
							FindPasswordActivity.this)) {
						CommonFunction.toastShow("�����ڴ��ֻ�������û�!",
								FindPasswordActivity.this);
					} else {
						// ͨ��sdk���Ͷ�����֤
						SMSSDK.getVerificationCode("86", uId);
						// �Ѱ�ť��ɲ��ɵ����������ʾ����ʱ�����ڻ�ȡ��
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
					CommonFunction.toastShow("��������ȷ���ֻ������ʽ!",
							FindPasswordActivity.this);
				}
			}
		});
		// ��ȡ��֤����Ϣ,�ύ��֤��ע��
		setPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				uId = userId.getText().toString().trim(); // �ֻ���
				pwd = password.getText().toString().trim(); // ����
				code = checkCode.getText().toString().trim(); // ��֤��
				// ����ֻ��š����롢��֤�벻Ϊ�����ύ��֤
				if (!"".equals(uId) && !"".equals(pwd) && !"".equals(code)) {
					// �ύ������֤
					SMSSDK.submitVerificationCode("86", uId, code);
				} else {
					CommonFunction
							.toastShow("�뽫��Ϣ��д����!", FindPasswordActivity.this);
				}
			}
		});
	}

	/**
	 * ��ת����¼����
	 */
	private void goLogin() {
		Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
		startActivity(intent);
		FindPasswordActivity.this.finish();
		// ���뵭��
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	/**
	 * ���������
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
	 * ��ʼ���ؼ�
	 */
	private void init() {
		setPwd = (Button) findViewById(R.id.btn_setPwd);
		getCode = (Button) findViewById(R.id.getCheckCode);
		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		checkCode = (EditText) findViewById(R.id.checkCode);
		goLogin = (TextView) findViewById(R.id.goLoginText);
		// ����������֤sdk
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
		// ע��ص������ӿ�
		SMSSDK.registerEventHandler(eventHandler);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// ע��SMSSDK��Handle��Ϣ����
		SMSSDK.unregisterAllEventHandler();
	}
	
}
