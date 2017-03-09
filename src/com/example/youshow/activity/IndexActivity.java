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
 * 登录后的个人首页
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
		// 获取当前时间
		mExitTime = System.currentTimeMillis();
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		// 点击注销按钮退出当前账号
		out_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CustomDialog.Builder builder = new CustomDialog.Builder(
						IndexActivity.this);
				builder.setMessage("确定注销当前账号吗？");
				builder.setTitle("注销提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// 清除sp中的用户登录信息
								CommonFunction.clearSP(IndexActivity.this);
								// 跳转登录页
								Intent i = new Intent(IndexActivity.this,
										LoginActivity.class);
								startActivity(i);
								IndexActivity.this.finish();
								// 淡入淡出
								overridePendingTransition(
										android.R.anim.slide_in_left,
										android.R.anim.slide_out_right);
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
		});
		write_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(IndexActivity.this,
						WriteDiaryActivity.class);
				startActivity(i);
				IndexActivity.this.finish();
				// 淡入淡出
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
			}
		});

	}

	/**
	 * 根据选中的底部菜单做出对应处理
	 * 
	 * @param index
	 *            - 选中的底部菜单的序号
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的 Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了游生活tab时，改变控件的图片
			trip_image.setImageResource(R.drawable.trip2);
			top_title.setText("游生活");
			if (tripFragment == null) {
				// 如果游生活fragment为空，则创建一个并添加到界面上
				tripFragment = new TripFragment();
				transaction.add(R.id.content, tripFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(tripFragment);
			}
			break;
		case 1:
			// 当点击了游轨迹tab时，改变控件的图片
			site_image.setImageResource(R.drawable.site2);
			top_title.setText("游轨迹");
			if (siteFragment == null) {
				// 如果游轨迹fragment为空，则创建一个并添加到界面上
				siteFragment = new SiteFragment();
				transaction.add(R.id.content, siteFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(siteFragment);
			}
			break;
		case 2:
			// 当点击了游世界tab时，改变控件的图片
			chatbot_image.setImageResource(R.drawable.chatbot2);
			top_title.setText("游世界");
			if (chatbotFragment == null) {
				// 如果游世界fragment为空，则创建一个并添加到界面上
				chatbotFragment = new ChatbotFragment();
				transaction.add(R.id.content, chatbotFragment);
			} else {
				// 如果不为空，则直接将它显示出来
				transaction.show(chatbotFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            - 用于对Fragment执行操作的事务
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
	 * 清除掉所有的选中状态
	 */
	private void clearSelection() {
		trip_image.setImageResource(R.drawable.trip1);
		site_image.setImageResource(R.drawable.site1);
		chatbot_image.setImageResource(R.drawable.chatbot1);
	}

	/**
	 * 初始化控件
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
	 * 底部菜单的点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.trip_layout:
			// 当点击了游生活tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.site_layout:
			// 当点击了游轨迹tab时，选中第3个tab
			setTabSelection(1);
			break;
		case R.id.chatbot_layout:
			// 当点击了游世界tab时，选中第4个tab
			setTabSelection(2);
			break;
		default:
			break;
		}
	}

	/**
	 * 点击手机后退键时，询问是否退出程序
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				CommonFunction.toastShow("再按一次退出程序", this);
				mExitTime = System.currentTimeMillis();// 更新mExitTime
			} else {
				System.exit(0);// 否则退出程序
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
