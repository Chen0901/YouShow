package com.example.youshow.activity;

import com.example.youshow.R;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.CustomDialog;
import com.example.youshow.fragment.ChatbotFragment;
import com.example.youshow.fragment.SiteFragment;
import com.example.youshow.fragment.TripFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ��¼��ĸ�����ҳ
 * 
 * @author cyl
 *
 */
public class IndexActivity extends Activity implements View.OnClickListener {
	private long mExitTime;
	private FragmentManager fragmentManager;
	private TripFragment tripFragment;
	private SiteFragment siteFragment;
	private ChatbotFragment chatbotFragment;
	private View tripLayout, siteLayout, chatbotLayout;
	private ImageView trip_image, site_image, chatbot_image;
	private TextView top_title;
	private Button out_btn, write_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
		// ��ȡ��ǰʱ��
		mExitTime = System.currentTimeMillis();
		// ��ʼ������Ԫ��
		initViews();
		fragmentManager = getFragmentManager();
		// ��һ������ʱѡ�е�0��tab
		setTabSelection(0);
		// ���ע����ť�˳���ǰ�˺�
		out_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CustomDialog.Builder builder = new CustomDialog.Builder(
						IndexActivity.this);
				builder.setMessage("ȷ��ע����ǰ�˺���");
				builder.setTitle("ע����ʾ");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// ���sp�е��û���¼��Ϣ
								CommonFunction.clearSP(IndexActivity.this);
								// ��ת��¼ҳ
								Intent i = new Intent(IndexActivity.this,
										LoginActivity.class);
								startActivity(i);
								IndexActivity.this.finish();
								// ���뵭��
								overridePendingTransition(
										android.R.anim.slide_in_left,
										android.R.anim.slide_out_right);
							}
						});

				builder.setNegativeButton("ȡ��",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();
			}
		});
		write_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(IndexActivity.this,
						WriteDiaryActivity.class);
				startActivity(i);
				IndexActivity.this.finish();
				// ���뵭��
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});

	}

	/**
	 * ����ѡ�еĵײ��˵�������Ӧ����
	 * 
	 * @param index
	 *            - ѡ�еĵײ��˵������
	 */
	private void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е� Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			// �������������tabʱ���ı�ؼ���ͼƬ
			trip_image.setImageResource(R.drawable.trip2);
			top_title.setText("������");
			if (tripFragment == null) {
				// ���������fragmentΪ�գ��򴴽�һ������ӵ�������
				tripFragment = new TripFragment();
				transaction.add(R.id.content, tripFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(tripFragment);
			}
			break;
		case 1:
			// ��������ι켣tabʱ���ı�ؼ���ͼƬ
			site_image.setImageResource(R.drawable.site2);
			top_title.setText("�ι켣");
			if (siteFragment == null) {
				// ����ι켣fragmentΪ�գ��򴴽�һ������ӵ�������
				siteFragment = new SiteFragment();
				transaction.add(R.id.content, siteFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(siteFragment);
			}
			break;
		case 2:
			// �������������tabʱ���ı�ؼ���ͼƬ
			chatbot_image.setImageResource(R.drawable.chatbot2);
			top_title.setText("������");
			if (chatbotFragment == null) {
				// ���������fragmentΪ�գ��򴴽�һ������ӵ�������
				chatbotFragment = new ChatbotFragment();
				transaction.add(R.id.content, chatbotFragment);
			} else {
				// �����Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(chatbotFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            - ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (tripFragment != null) {
			transaction.hide(tripFragment);
		}
		if (siteFragment != null) {
			transaction.hide(siteFragment);
		}
		if (chatbotFragment != null) {
			transaction.hide(chatbotFragment);
		}
	}

	/**
	 * ��������е�ѡ��״̬
	 */
	private void clearSelection() {
		trip_image.setImageResource(R.drawable.trip1);
		site_image.setImageResource(R.drawable.site1);
		chatbot_image.setImageResource(R.drawable.chatbot1);
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initViews() {
		tripLayout = findViewById(R.id.trip_layout);
		siteLayout = findViewById(R.id.site_layout);
		chatbotLayout = findViewById(R.id.chatbot_layout);
		trip_image = (ImageView) findViewById(R.id.trip_image);
		site_image = (ImageView) findViewById(R.id.site_image);
		chatbot_image = (ImageView) findViewById(R.id.chatbot_image);
		top_title = (TextView) findViewById(R.id.top_title);
		out_btn = (Button) findViewById(R.id.out_btn);
		write_btn = (Button) findViewById(R.id.write_btn);
		tripLayout.setOnClickListener(this);
		siteLayout.setOnClickListener(this);
		chatbotLayout.setOnClickListener(this);
	}

	/**
	 * �ײ��˵��ĵ���¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.trip_layout:
			// �������������tabʱ��ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.site_layout:
			// ��������ι켣tabʱ��ѡ�е�3��tab
			setTabSelection(1);
			break;
		case R.id.chatbot_layout:
			// �������������tabʱ��ѡ�е�4��tab
			setTabSelection(2);
			break;
		default:
			break;
		}
	}

	/**
	 * ����ֻ����˼�ʱ��ѯ���Ƿ��˳�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {// ������ΰ���ʱ��������2000���룬���˳�
				CommonFunction.toastShow("�ٰ�һ���˳�����", this);
				mExitTime = System.currentTimeMillis();// ����mExitTime
			} else {
				System.exit(0);// �����˳�����
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
