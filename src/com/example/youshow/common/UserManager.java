package com.example.youshow.common;

import java.util.ArrayList;

import com.example.youshow.bean.Diary;
import com.example.youshow.bean.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �������ݿ����
 * 
 * @author cyl
 *
 */
public class UserManager {
	DatabaseHelper databaseHelper;
	Context context;
	SQLiteDatabase db = null;
	// ��ȡһ��������
	Cursor cursor = null;

	public UserManager(Context context) {
		this.context = context;
		databaseHelper = new DatabaseHelper(context);
	}

	/**
	 * ���һ���û�
	 * 
	 * @param user
	 *            - �û�ʵ�������
	 * @return - long����ֵ�ж��Ƿ����ɹ�
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
	 * ��������
	 * @param user - �û���Ϣ
	 * @return - long����ֵ�ж��Ƿ����óɹ�
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
	 * �����û�������û��Ƿ����
	 * 
	 * @param arg
	 *            - һά����,����û���������
	 * @return - user����
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
	 * ���һ���ռ�
	 * 
	 * @param diary
	 *            - �ռ���diary�Ķ���
	 * @return - long����ֵ�ж��Ƿ����ɹ�
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
	 * ��ҳ��ȡ��ǰ�û����ռ���Ϣ
	 * 
	 * @param arg
	 *            - ��ǰ��¼�û����û���
	 * @param pageNum
	 *            - ��ҳ��
	 * @return - ��ǰ�û����ռ���Ϣ
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
	 * ��ҳ��ȡ��ǰ�û���ѯ���ռ���Ϣ
	 * 
	 * @param arg
	 *            - ��ѯ��������ǰ��¼�û����û���
	 * @param pageNum
	 *            - ��ҳ��
	 * @return - ��ǰ�û���ѯ���ռ���Ϣ
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
	 * ��ҳ��ȡ��ǰ�û����ж�λ���ռ���Ϣ
	 * 
	 * @param arg
	 *            - ��ǰ��¼�û����û���
	 * @param pageNum
	 *            - ��ҳ��
	 * @return - ��ǰ�û����ж�λ���ռ���Ϣ
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
	 * ɾ����ǰ��¼�û�ָ�����ռ�
	 * @param arg - ��ǰ��¼�û����û�����ָ���ռǵ�Id
	 * @return - long����ֵ�ж��Ƿ�ɾ���ɹ�
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
	 * ���µ�ǰ��¼�û�ָ�����ռ�
	 * @param arg - ��ǰ��¼�û����û�����ָ���ռǵ���Ϣ
	 * @return - long����ֵ�ж��Ƿ���³ɹ�
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
