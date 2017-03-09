package com.example.youshow.activity;

import java.util.Calendar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.youshow.R;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.NetHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * ��д�ռǽ���
 * 
 * @author cyl
 *
 */
public class WriteDiaryActivity extends Activity implements
		View.OnClickListener {
	private Button save_btn, back_btn, locationInfo; // ���水ť
	private Spinner diaryWeather, diaryMood; // ���������б����������б�
	private EditText diaryTitle, diaryContent; // �ռǱ��⡢����
	private TextView diarySite, time; // λ�á�ʱ��
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private boolean startLocation = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary_layout);
		initView();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		save_btn = (Button) findViewById(R.id.save_btn);
		diaryWeather = (Spinner) findViewById(R.id.diary_Weather);
		diaryMood = (Spinner) findViewById(R.id.diary_Mood);
		diaryTitle = (EditText) findViewById(R.id.diary_title);
		diaryContent = (EditText) findViewById(R.id.diary_content);
		locationInfo = (Button) findViewById(R.id.getSite);
		diarySite = (TextView) findViewById(R.id.diarySite);
		time = (TextView) findViewById(R.id.time);
		back_btn = (Button) findViewById(R.id.back_btn);
		Calendar c = Calendar.getInstance();
		StringBuffer timeStr = new StringBuffer();
		timeStr.append(c.get(Calendar.YEAR))
				.append("-")
				.append(CommonFunction.formatTime(c.get(Calendar.MONTH) + 1))
				.append("-")
				.append(CommonFunction.formatTime(c.get(Calendar.DAY_OF_MONTH)));
		time.setText(timeStr.toString());
		mLocationClient = new LocationClient(
				WriteDiaryActivity.this.getApplicationContext());
		mLocationClient.registerLocationListener(myListener); // ע���������
		locationInfo.setOnClickListener(this);
		save_btn.setOnClickListener(this);
		back_btn.setOnClickListener(this);
	}

	/**
	 * ������ز���
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span = 5000;
		option.setScanSpan(span);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		option.setProdName("���ּ�");
		mLocationClient.setLocOption(option);
	}

	/**
	 * �ٶȶ�λ��������ڲ���
	 * 
	 * @author cyl
	 *
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				diarySite.setText("��λʧ�ܣ�");
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append(location.getAddrStr());
			}
			diarySite.setText(sb.toString());
			mLocationClient.stop();
			startLocation = false;
			CommonFunction.toastShow("�ٴε�����¶�λ", WriteDiaryActivity.this);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.stop();// ֹͣ��λ
		mLocationClient.unRegisterLocationListener(myListener);
	}

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_btn:
			/* �����ռ� */
			String title = diaryTitle.getText().toString().trim();
			String content = diaryContent.getText().toString().trim();
			String site = diarySite.getText().toString();
			String weather = (String) diaryWeather.getSelectedItem();
			String mood = (String) diaryMood.getSelectedItem();
			String[] str = { title, content, site, weather, mood };
			if (TextUtils.isEmpty(str[0]) || TextUtils.isEmpty(str[1])) {
				CommonFunction.toastShow("������������벻�ÿ�", WriteDiaryActivity.this);
			} else {
				CommonFunction.setDiary(str, WriteDiaryActivity.this);
				diaryTitle.setText("");
				diaryContent.setText("");
			}
			break;
		case R.id.getSite:
			// �������״̬
			if (!NetHelper.checkNetWork(WriteDiaryActivity.this)) {// û����������
				return;
			}
			setLocationOption();// �趨��λ����
			if (startLocation) {
				mLocationClient.stop();
				startLocation = false;
			} else {
				mLocationClient.start();// ��ʼ��λ
				diarySite.setText("��λ��...");
				startLocation = true;
			}
			break;
		case R.id.back_btn:
			Intent i = new Intent(WriteDiaryActivity.this, IndexActivity.class);
			startActivity(i);
			WriteDiaryActivity.this.finish();
			// ���뵭��
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			break;
		default:
			break;
		}
	}

	/**
	 * �������ʱֱ�ӹرյ�ǰactivity
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Intent i = new Intent(WriteDiaryActivity.this, IndexActivity.class);
		startActivity(i);
		WriteDiaryActivity.this.finish();
		// ���뵭��
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		return super.onKeyDown(keyCode, event);
	}

}
