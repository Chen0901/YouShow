package com.example.youshow.common;

import java.util.ArrayList;

import com.example.youshow.bean.Diary;
import com.example.youshow.bean.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 管理数据库操作
 * 
 * @author cyl
 *
 */
public class UserManager {
	DatabaseHelper databaseHelper;
	Context context;
	SQLiteDatabase db = null;
	// 获取一个光标对象
	Cursor cursor = null;

	public UserManager(Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(context);
	}

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 *            - 用户实体类对象
	 * @return - long型数值判断是否插入成功
	 */
	public long addUser(User user) {
		try {
			db = databaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("userId", user.getUserId());
			values.put("password", CommonFunction.getMD5Str(user.getPassword()));
			return db.insert("users", null, values);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return 0;
	}
	
	/**
	 * 重置密码
	 * @param user - 用户信息
	 * @return - long型数值判断是否重置成功
	 */
	public long updatePwd(User user){
		try {
			db = databaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("password", CommonFunction.getMD5Str(user.getPassword()));
			return db.update("users", values, "userId=?", new String[]{user.getUserId()});
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return 0;
	}

	/**
	 * 根据用户名检测用户是否存在
	 * 
	 * @param arg
	 *            - 一维数组,存放用户名、密码
	 * @return - user对象
	 */
	public User selectForLogin(String[] arg) {
		User user = new User();
		try {
			db = databaseHelper.getReadableDatabase();
			cursor = db.query("users", null, "userId=?", arg, null, null, null);
			while (cursor.moveToNext()) {
				user.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
				user.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return user;
	}

	/**
	 * 添加一个日记
	 * 
	 * @param diary
	 *            - 日记类diary的对象
	 * @return - long型数值判断是否插入成功
	 */
	public long addDiary(Diary diary) {
		try {
			db = databaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("userId", diary.getUserId());
			values.put("diaryTitle", diary.getDiaryTitle());
			values.put("diaryContent", diary.getDiaryContent());
			values.put("diaryTime", diary.getDiaryTime());
			values.put("diarySite", diary.getDiarySite());
			values.put("diaryWeather", diary.getDiaryWeather());
			values.put("diaryMood", diary.getDiaryMood());
			return db.insert("diary", null, values);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return 0;
	}

	/**
	 * 分页获取当前用户的日记信息
	 * 
	 * @param arg
	 *            - 当前登录用户的用户名
	 * @param pageNum
	 *            - 分页数
	 * @return - 当前用户的日记信息
	 */
	public ArrayList<Diary> selectDiaryByPage(String[] arg, String pageNum) {
		ArrayList<Diary> diarList = new ArrayList<Diary>();
		try {
			db = databaseHelper.getReadableDatabase();
			cursor = db.query("diary", null, "userId=?", arg, null, null,
					"diaryTime asc", pageNum);
			while (cursor.moveToNext()) {
				Diary diary = new Diary();
				diary.setDiaryId(cursor.getInt(cursor.getColumnIndex("diaryId")));
				diary.setUserId(cursor.getString(cursor
						.getColumnIndex("userId")));
				diary.setDiaryTitle(cursor.getString(cursor
						.getColumnIndex("diaryTitle")));
				diary.setDiaryContent(cursor.getString(cursor
						.getColumnIndex("diaryContent")));
				diary.setDiaryTime(cursor.getString(cursor
						.getColumnIndex("diaryTime")));
				diary.setDiarySite(cursor.getString(cursor
						.getColumnIndex("diarySite")));
				diary.setDiaryWeather(cursor.getString(cursor
						.getColumnIndex("diaryWeather")));
				diary.setDiaryMood(cursor.getString(cursor
						.getColumnIndex("diaryMood")));
				diarList.add(diary);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return diarList;
	}

	/**
	 * 分页获取当前用户查询的日记信息
	 * 
	 * @param arg
	 *            - 查询条件及当前登录用户的用户名
	 * @param pageNum
	 *            - 分页数
	 * @return - 当前用户查询的日记信息
	 */
	public ArrayList<Diary> selectDiaryBySearch(String[] arg, String pageNum) {
		ArrayList<Diary> diarList = new ArrayList<Diary>();
		try {
			db = databaseHelper.getReadableDatabase();
			cursor = db.query("diary", null,
					"userId=? and diaryTitle like ? or diaryContent like ?",
					arg, null, null, "diaryTime asc", pageNum);
			while (cursor.moveToNext()) {
				Diary diary = new Diary();
				diary.setDiaryId(cursor.getInt(cursor.getColumnIndex("diaryId")));
				diary.setUserId(cursor.getString(cursor
						.getColumnIndex("userId")));
				diary.setDiaryTitle(cursor.getString(cursor
						.getColumnIndex("diaryTitle")));
				diary.setDiaryContent(cursor.getString(cursor
						.getColumnIndex("diaryContent")));
				diary.setDiaryTime(cursor.getString(cursor
						.getColumnIndex("diaryTime")));
				diary.setDiarySite(cursor.getString(cursor
						.getColumnIndex("diarySite")));
				diary.setDiaryWeather(cursor.getString(cursor
						.getColumnIndex("diaryWeather")));
				diary.setDiaryMood(cursor.getString(cursor
						.getColumnIndex("diaryMood")));
				diarList.add(diary);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return diarList;
	}

	/**
	 * 分页获取当前用户含有定位的日记信息
	 * 
	 * @param arg
	 *            - 当前登录用户的用户名
	 * @param pageNum
	 *            - 分页数
	 * @return - 当前用户含有定位的日记信息
	 */
	public ArrayList<Diary> selectDiaryBySite(String[] arg, String pageNum) {
		ArrayList<Diary> diarList = new ArrayList<Diary>();
		try {
			db = databaseHelper.getReadableDatabase();
			cursor = db.query("diary", null,
					"userId=? and diarySite !=''", arg, null, null,
					"diaryTime asc", pageNum);
			while (cursor.moveToNext()) {
				Diary diary = new Diary();
				diary.setDiaryId(cursor.getInt(cursor.getColumnIndex("diaryId")));
				diary.setUserId(cursor.getString(cursor
						.getColumnIndex("userId")));
				diary.setDiaryTitle(cursor.getString(cursor
						.getColumnIndex("diaryTitle")));
				diary.setDiaryContent(cursor.getString(cursor
						.getColumnIndex("diaryContent")));
				diary.setDiaryTime(cursor.getString(cursor
						.getColumnIndex("diaryTime")));
				diary.setDiarySite(cursor.getString(cursor
						.getColumnIndex("diarySite")));
				diary.setDiaryWeather(cursor.getString(cursor
						.getColumnIndex("diaryWeather")));
				diary.setDiaryMood(cursor.getString(cursor
						.getColumnIndex("diaryMood")));
				diarList.add(diary);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return diarList;
	}
	
	/**
	 * 删除当前登录用户指定的日记
	 * @param arg - 当前登录用户的用户名和指定日记的Id
	 * @return - long型数值判断是否删除成功
	 */
	public long deleteDiary(String[] arg){
		try {
			db = databaseHelper.getWritableDatabase();
			return db.delete("diary", "userId=? and diaryId=?", arg);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return 0;
	}
	
	/**
	 * 更新当前登录用户指定的日记
	 * @param arg - 当前登录用户的用户名和指定日记的信息
	 * @return - long型数值判断是否更新成功
	 */
	public long updateDiary(String[] updateInfo,String[] whereArgs){
		try {
			db = databaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("diaryTitle", updateInfo[0]);
			values.put("diaryContent", updateInfo[1]);
			return db.update("diary", values, "userId=? and diaryId=?", whereArgs);
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		} finally {
			db.close();
		}
		return 0;
	}
	
}
