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
 * 书写日记界面
 * 
 * @author cyl
 *
 */
public class WriteDiaryActivity extends Activity implements
		View.OnClickListener {
	private Button save_btn, back_btn, locationInfo; // 保存按钮
	private Spinner diaryWeather, diaryMood; // 天气下拉列表、心情下拉列表
	private EditText diaryTitle, diaryContent; // 日记标题、内容
	private TextView diarySite, time; // 位置、时间
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
	 * 初始化控件
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
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		locationInfo.setOnClickListener(this);
		save_btn.setOnClickListener(this);
		back_btn.setOnClickListener(this);
	}

	/**
	 * 设置相关参数
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 5000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		option.setProdName("游手记");
		mLocationClient.setLocOption(option);
	}

	/**
	 * 百度定位监听类的内部类
	 * 
	 * @author cyl
	 *
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				diarySite.setText("定位失败！");
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
			CommonFunction.toastShow("再次点击重新定位", WriteDiaryActivity.this);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.stop();// 停止定位
		mLocationClient.unRegisterLocationListener(myListener);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_btn:
			/* 保存日记 */
			String title = diaryTitle.getText().toString().trim();
			String content = diaryContent.getText().toString().trim();
			String site = diarySite.getText().toString();
			String weather = (String) diaryWeather.getSelectedItem();
			String mood = (String) diaryMood.getSelectedItem();
			String[] str = { title, content, site, weather, mood };
			if (TextUtils.isEmpty(str[0]) || TextUtils.isEmpty(str[1])) {
				CommonFunction.toastShow("标题和内容输入不得空", WriteDiaryActivity.this);
			} else {
				CommonFunction.setDiary(str, WriteDiaryActivity.this);
				diaryTitle.setText("");
				diaryContent.setText("");
			}
			break;
		case R.id.getSite:
			// 检测网络状态
			if (!NetHelper.checkNetWork(WriteDiaryActivity.this)) {// 没有连接网络
				return;
			}
			setLocationOption();// 设定定位参数
			if (startLocation) {
				mLocationClient.stop();
				startLocation = false;
			} else {
				mLocationClient.start();// 开始定位
				diarySite.setText("定位中...");
				startLocation = true;
			}
			break;
		case R.id.back_btn:
			Intent i = new Intent(WriteDiaryActivity.this, IndexActivity.class);
			startActivity(i);
			WriteDiaryActivity.this.finish();
			// 淡入淡出
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			break;
		default:
			break;
		}
	}

	/**
	 * 点击回退时直接关闭当前activity
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Intent i = new Intent(WriteDiaryActivity.this, IndexActivity.class);
		startActivity(i);
		WriteDiaryActivity.this.finish();
		// 淡入淡出
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		return super.onKeyDown(keyCode, event);
	}

}
