package com.example.youshow.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ�
 * @author cyl
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		// ����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����
		// ����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����
		super(context, "my.db", null, 1);
	}

	/**
	 * ���ݿ��һ�δ���ʱ������
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// �����û����ռǱ�
		String create_users = "CREATE TABLE IF NOT EXISTS users(userId varchar(11) primary key,password varchar(1000))";
		String create_diary = "CREATE TABLE IF NOT EXISTS diary(diaryId integer primary key autoincrement,userId varchar(11),diaryTitle varchar(50),diaryContent varchar(1000),diaryTime varchar(1000),diarySite varchar(1000),diaryWeather varchar(10),diaryMood varchar(10))"; 
		db.execSQL(create_users);
		db.execSQL(create_diary);
	}

	/**
	 * �汾�ŷ����ı�ʱ����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop if table exists users");
        db.execSQL("drop if table exists diary");
        onCreate(db);
	}

}
