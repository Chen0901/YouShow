package com.example.youshow.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 * @author cyl
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		// 第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类
		// 设置为null,代表使用系统默认的工厂类
		super(context, "my.db", null, 1);
	}

	/**
	 * 数据库第一次创建时被调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建用户表、日记表
		String create_users = "CREATE TABLE IF NOT EXISTS users(userId varchar(11) primary key,password varchar(1000))";
		String create_diary = "CREATE TABLE IF NOT EXISTS diary(diaryId integer primary key autoincrement,userId varchar(11),diaryTitle varchar(50),diaryContent varchar(1000),diaryTime varchar(1000),diarySite varchar(1000),diaryWeather varchar(10),diaryMood varchar(10))"; 
		db.execSQL(create_users);
		db.execSQL(create_diary);
	}

	/**
	 * 版本号发生改变时调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop if table exists users");
        db.execSQL("drop if table exists diary");
        onCreate(db);
	}

}
