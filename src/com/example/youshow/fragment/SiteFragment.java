package com.example.youshow.fragment;

import java.util.ArrayList;

import com.example.youshow.R;
import com.example.youshow.activity.DiaryManagerActivity;
import com.example.youshow.bean.Diary;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.DiarySiteAdapter;
import com.example.youshow.view.LoadListView;
import com.example.youshow.view.LoadListView.ILoadListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 游轨迹界面
 * 
 * @author cyl
 *
 */
public class SiteFragment extends Fragment implements ILoadListener {
	private View siteLayout;
	private ArrayList<Diary> diary_list = new ArrayList<Diary>();
	private DiarySiteAdapter adapter;
	private LoadListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		siteLayout = inflater.inflate(R.layout.site_layout, container, false);
		// 初始化adapter
		for (Diary diary : CommonFunction.getSiteData(0, getActivity())) {
			diary_list.add(0, diary);
		}
		showListView(diary_list);
		return siteLayout;
	}

	/**
	 * 初始化来显示列表数据
	 * 
	 * @param list
	 *            - 数据链表
	 */
	private void showListView(ArrayList<Diary> list) {
		if (adapter == null) {
			listview = (LoadListView) siteLayout.findViewById(R.id.sitelistview);
			listview.setInterface(this);
			adapter = new DiarySiteAdapter(getActivity(), list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (parent.getId()) {
					case R.id.sitelistview:
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
				for (Diary diary : CommonFunction.getSiteData(
						diary_list.size(), getActivity())) {
					diary_list.add(0, diary);
				}
				showListView(diary_list);
				// 通知listview加载完毕
				listview.loadComplete();
			}
		}, 2000);
	}
}
