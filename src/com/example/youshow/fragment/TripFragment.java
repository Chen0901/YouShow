package com.example.youshow.fragment;

import java.util.ArrayList;

import com.example.youshow.R;
import com.example.youshow.activity.DiaryManagerActivity;
import com.example.youshow.activity.SearchResultActivity;
import com.example.youshow.bean.Diary;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.DiaryAdapter;
import com.example.youshow.view.LoadListView;
import com.example.youshow.view.LoadListView.ILoadListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 游生活主页
 * 
 * @author cyl
 *
 */
public class TripFragment extends Fragment implements ILoadListener {
	private ArrayList<Diary> diary_list = new ArrayList<Diary>();
	private DiaryAdapter adapter;
	private LoadListView listview;
	private EditText searchInfo;
	private Button search_btn;
	private View tripLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tripLayout = inflater.inflate(R.layout.trip_layout, container, false);
		initView();
		// 初始化adapter
		for (Diary diary : CommonFunction.getData(0, getActivity())) {
			diary_list.add(0, diary);
		}
		showListView(diary_list);
		search_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String info = searchInfo.getText().toString().trim();
				if (info == null || "".equals(info)) {
					CommonFunction.toastShow("请输入搜索内容", getActivity());
				} else {
					Intent intent = new Intent(getActivity(),
							SearchResultActivity.class);
					intent.putExtra("info", info);
					startActivity(intent);
					// 淡入淡出
					getActivity().overridePendingTransition(
							android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				}
			}
		});
		return tripLayout;
	}

	/**
	 * 初始化来显示列表数据
	 * 
	 * @param list
	 *            - 数据链表
	 */
	private void showListView(ArrayList<Diary> list) {
		if (adapter == null) {
			listview = (LoadListView) tripLayout.findViewById(R.id.listview);
			listview.setInterface(this);
			adapter = new DiaryAdapter(getActivity(), list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (parent.getId()) {
					case R.id.listview:
						Diary diary = (Diary) parent
								.getItemAtPosition(position);
						Intent intent = new Intent(getActivity(),
								DiaryManagerActivity.class);
						intent.putExtra(
								"diaryInfo",
								new String[] {
										String.valueOf(diary.getDiaryId()),
										diary.getDiaryTitle(),
										diary.getDiaryContent() });
						startActivity(intent);
						getActivity().finish();
						// 淡入淡出
						getActivity().overridePendingTransition(
								android.R.anim.slide_in_left,
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
	 * 初始化控件
	 */
	private void initView() {
		searchInfo = (EditText) tripLayout.findViewById(R.id.searchInfo);
		search_btn = (Button) tripLayout.findViewById(R.id.search_btn);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * 下拉刷新加载新数据
	 */
	@Override
	public void onLoad() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 获取更多数据
				for (Diary diary : CommonFunction.getData(diary_list.size(),
						getActivity())) {
					diary_list.add(0, diary);
				}
				showListView(diary_list);
				// 通知listview加载完毕
				listview.loadComplete();
			}
		}, 2000);
	}
}
