package com.example.youshow.activity;

import com.example.youshow.R;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.CustomDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 日记的删改界面
 * 
 * @author cyl
 *
 */
public class DiaryManagerActivity extends Activity implements
		View.OnClickListener {
	private EditText diaryTitle, diaryContent;
	private Button back_btn, option_btn;
	private String[] diaryInfo;
	private int status = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_diary_manager);
		Intent intent = getIntent();
		diaryInfo = intent.getStringArrayExtra("diaryInfo");
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		diaryTitle = (EditText) findViewById(R.id.diary_title);
		diaryContent = (EditText) findViewById(R.id.diary_content);
		back_btn = (Button) findViewById(R.id.back_btn);
		option_btn = (Button) findViewById(R.id.option_btn);
		diaryTitle.setText(diaryInfo[1]);
		diaryContent.setText(diaryInfo[2]);
		back_btn.setOnClickListener(this);
		option_btn.setOnClickListener(this);
		diaryTitle.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				option_btn.setBackgroundResource(R.drawable.btn_save_selector);
				status = 1;
			}
		});
		diaryContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				option_btn.setBackgroundResource(R.drawable.btn_save_selector);
				status = 1;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			backToIndex();
			break;
		case R.id.option_btn:
			if (status == 1) {
				String dTitle = diaryTitle.getText().toString();
				String dContent = diaryContent.getText().toString();
				CommonFunction.updateDiary(new String[] { dTitle, dContent },
						diaryInfo[0], DiaryManagerActivity.this);
				option_btn.setBackgroundResource(R.drawable.btn_delete_selector);
				status = 2;
			} else if (status == 2) {
				CustomDialog.Builder builder = new CustomDialog.Builder(
						DiaryManagerActivity.this);
				builder.setMessage("确定删除这篇日记吗？");
				builder.setTitle("删除提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								CommonFunction.deleteDiary(diaryInfo[0],
										DiaryManagerActivity.this);
								backToIndex();
							}
						});

				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 返回个人首页
	 */
	public void backToIndex() {
		Intent intent = new Intent(DiaryManagerActivity.this,
				IndexActivity.class);
		startActivity(intent);
		// 淡入淡出
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		DiaryManagerActivity.this.finish();
	}

	/**
	 * 点击回退时直接关闭当前activity
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		backToIndex();
		return super.onKeyDown(keyCode, event);
	}

}
