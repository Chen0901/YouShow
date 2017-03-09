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
 * ��¼
 * 
 * @author cyl
 *
 */
public class LoginActivity extends Activity {
	private EditText userId, password; // �û���(�ֻ���)������༭��
	private String uId, pwd; // �û���(�ֻ���)������
	private Button login; // ��¼��ť
	private TextView goRegister,finPwd; // ǰ��ע������ı�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		init();
		clickEvent();
		hideKeyBoard();
	}

	/**
	 * ����¼�
	 */
	private void clickEvent() {
		// ��ת��ע�����
		goRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				// ���뵭��
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
		// ��¼���
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				uId = userId.getText().toString().trim();
				pwd = password.getText().toString().trim();
				if (CommonFunction.checkForLogin(new User(uId, pwd),
						LoginActivity.this)) {
					// ��ת��������ҳ
					Intent intent = new Intent(LoginActivity.this,
							IndexActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
					// ���뵭��
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				} else {
					CommonFunction.toastShow("��¼ʧ��,�û������������",
							LoginActivity.this);
				}
			}
		});
		// ��ת���һ��������
		finPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						FindPasswordActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				// ���뵭��
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void init() {
		userId = (EditText) findViewById(R.id.userId);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.btn_login);
		goRegister = (TextView) findViewById(R.id.goRegisterText);
		finPwd = (TextView) findViewById(R.id.goFindPwdText);
	}

	/**
	 * ���������
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
