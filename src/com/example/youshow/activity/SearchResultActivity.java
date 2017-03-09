package com.example.youshow.activity;

import java.util.ArrayList;

import com.example.youshow.R;
import com.example.youshow.bean.Diary;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.DiaryAdapter;
import com.example.youshow.view.LoadListView;
import com.example.youshow.view.LoadListView.ILoadListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

/**
 * �����������
 * 
 * @author cyl
 *
 */
public class SearchResultActivity extends Activity implements ILoadListener {
	private ArrayList<Diary> diary_list = new ArrayList<Diary>();
	private DiaryAdapter adapter;
	private LoadListView listview;
	private String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_searchresult);
		Intent intent = getIntent();
		info = intent.getStringExtra("info");
		// ��ʼ��adapter
		for (Diary diary : CommonFunction.getSearchData(info, 0,
				SearchResultActivity.this)) {
			diary_list.add(0, diary);
		}
		showListView(diary_list);
		Button back = (Button) findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SearchResultActivity.this.finish();
			}
		});
	}

	/**
	 * ��ʼ������ʾ�б�����
	 * 
	 * @param list
	 *            - ��������
	 */
	private void showListView(ArrayList<Diary> list) {
		if (adapter == null) {
			listview = (LoadListView) findViewById(R.id.reslistview);
			listview.setInterface(this);
			adapter = new DiaryAdapter(SearchResultActivity.this, list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (parent.getId()) {
					case R.id.reslistview:
						Diary diary = (Diary) parent
								.getItemAtPosition(position);
						Intent intent = new Intent(SearchResultActivity.this,
								DiaryManagerActivity.class);
						intent.putExtra(
								"diaryInfo",
								new String[] {
										String.valueOf(diary.getDiaryId()),
										diary.getDiaryTitle(),
										diary.getDiaryContent() });
						startActivity(intent);
						SearchResultActivity.this.finish();
						// ���뵭��
						overridePendingTransition(android.R.anim.slide_in_left,
								android.R.anim.slide_out_right);
						break;
					default:
						break;
					}
				}
			});
		} else {
			adapter.onDateChange(list);
		}
	}

	/**
	 * �������ʱֱ�ӹرյ�ǰactivity
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		SearchResultActivity.this.finish();
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ����ˢ�¼���������
	 */
	@Override
	public void onLoad() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// ��ȡ��������
				for (Diary diary : CommonFunction.getSearchData(info,
						diary_list.size(), SearchResultActivity.this)) {
					diary_list.add(0, diary);
				}
				showListView(diary_list);
				// ֪ͨlistview�������
				listview.loadComplete();
			}
		}, 2000);
	}
}
