package com.example.youshow.common;

import java.util.ArrayList;

import com.example.youshow.R;
import com.example.youshow.bean.Diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiaryAdapter extends BaseAdapter {
	private ArrayList<Diary> diary_list;
	private LayoutInflater inflater;

	public DiaryAdapter(Context context, ArrayList<Diary> diary_list) {
		this.diary_list = diary_list;
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(ArrayList<Diary> diary_list) {
		this.diary_list = diary_list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return diary_list.size();
	}

	@Override
	public Object getItem(int position) {
		return diary_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Diary diary = diary_list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_layout, null);
			holder.diaryTitle = (TextView) convertView
					.findViewById(R.id.diaryTitle);
			holder.diaryWeather = (TextView) convertView
					.findViewById(R.id.diaryWeather);
			holder.diaryMood = (TextView) convertView
					.findViewById(R.id.diaryMood);
			holder.diarySite = (TextView) convertView
					.findViewById(R.id.diarySite);
			holder.diaryTime = (TextView) convertView
					.findViewById(R.id.diaryTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.diaryTitle.setText(diary.getDiaryTitle());
		holder.diaryWeather.setText("天气："+diary.getDiaryWeather());
		holder.diaryMood.setText("心情："+diary.getDiaryMood());
		holder.diaryTime.setText(diary.getDiaryTime());
		return convertView;
	}

	class ViewHolder {
		TextView diaryTitle;
		TextView diaryWeather;
		TextView diaryMood;
		TextView diarySite;
		TextView diaryTime;
	}

}
