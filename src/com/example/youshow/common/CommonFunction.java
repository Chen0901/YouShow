package com.example.youshow.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.youshow.R;
import com.example.youshow.bean.Diary;
import com.example.youshow.bean.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ͨ�õ�һЩ����
 * 
 * @author cyl
 *
 */
public class CommonFunction {
	private static SharedPreferences sp; // �����������洢

	/**
	 * MD5����
	 * 
	 * @param str
	 *            - ����������
	 * @return - ���ܺ�İ���
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * ��֤�ֻ������ʽ
	 * 
	 * @param phonenum
	 *            - �ֻ�����
	 * @return - ��֤���,booleanֵ
	 */
	public static boolean checkPhoneNum(String phonenum) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phonenum);
		return matcher.matches();
	}

	/**
	 * �ж��Ƿ��¼��
	 * 
	 * @param activity
	 *            - activity����
	 * @return - booleanֵ,��¼״̬��ʶ
	 */
	public static boolean loginStatus(Context context) {
		// ��sp��ȡ���û���,����Ƿ��¼��
		if (getUserId(context) != null && !"".equals(getUserId(context))) {// ��¼��
			return true;
		} else {// û�е�¼��
			return false;
		}
	}

	/**
	 * ���sp�б�����û���Ϣ
	 */
	public static void clearSP(Context context) {
		sp = context.getSharedPreferences("loginRes", 0);
		sp.edit().clear().commit();
	}

	/**
	 * �ж��û��Ƿ����
	 * 
	 * @param user
	 *            - user����
	 * @param context
	 *            - activiy������
	 * @return - booleanֵ,�û��Ƿ���ڵı�ʶ
	 */
	public static boolean userIsExistOrNot(User user, Context context) {
		UserManager manager = new UserManager(context);
		User u = manager.selectForLogin(new String[] { user.getUserId() });
		if (u.getUserId() != null) {// �û�����
			return true;
		} else {// �û�������
			return false;
		}
	}

	/**
	 * ��¼���
	 * 
	 * @param user
	 *            - user����
	 * @param context
	 *            - activity��������
	 * @return - booleanֵ,�û����������Ƿ�ƥ�����ݿ�ı�ʶ
	 */
	public static boolean checkForLogin(User user, Context context) {
		UserManager manager = new UserManager(context);
		User u = manager.selectForLogin(new String[] { user.getUserId() });
		String pwd = getMD5Str(user.getPassword());
		if (u.getUserId() != null && pwd.equals(u.getPassword())) {
			// ���û��������sp��
			sp = context.getSharedPreferences("loginRes", 0);
			sp.edit().putString("userId", user.getUserId()).commit();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����diary����
	 * 
	 * @param str
	 *            - ��activity��������diary���������Ϣ
	 * @param act
	 *            - activity����
	 */
	public static void setDiary(String[] str, Activity act) {
		if (str[2].equals("��λ��...") || str[2].equals("��λʧ�ܣ�")
				|| str[2].equals("�����ఴť������λ")) {
			str[2] = "";
		}
		UserManager manager = new UserManager(act);
		Diary diary = new Diary(getUserId(act), str[0], str[1], getTime(),
				str[2], str[3], str[4]);
		if (manager.addDiary(diary) > 0) {
			toastShow("����ɹ�", act);
		} else {
			toastShow("����ʧ��,����ϵ���ݿ����Ա�Ժ�����!", act);
		}
	}

	/**
	 * ��ȡ��ǰϵͳʱ��
	 * 
	 * @return - ��ǰϵͳʱ��
	 */
	public static String getTime() {
		Calendar c = Calendar.getInstance();
		StringBuffer time = new StringBuffer();
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "������";
		} else if ("2".equals(mWay)) {
			mWay = "����һ";
		} else if ("3".equals(mWay)) {
			mWay = "���ڶ�";
		} else if ("4".equals(mWay)) {
			mWay = "������";
		} else if ("5".equals(mWay)) {
			mWay = "������";
		} else if ("6".equals(mWay)) {
			mWay = "������";
		} else if ("7".equals(mWay)) {
			mWay = "������";
		}
		time.append(c.get(Calendar.YEAR)).append("-")
				.append(formatTime(c.get(Calendar.MONTH) + 1)).append("-")
				.append(formatTime(c.get(Calendar.DAY_OF_MONTH))).append(" ")
				.append(formatTime(c.get(Calendar.HOUR_OF_DAY))).append(":")
				.append(formatTime(c.get(Calendar.MINUTE))).append(":")
				.append(formatTime(c.get(Calendar.SECOND))).append(" ")
				.append(mWay);
		return time.toString();
	}

	/**
	 * ת��ʱ����ĸ�ʽ
	 * 
	 * @param t
	 *            - ʱ�������ֵ
	 * @return - ��ʽת�����ʱ������ַ���
	 */
	public static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// ��Ԫ����� t>10ʱȡ ""+t
	}

	/**
	 * ��ȡ�û���
	 * 
	 * @param context
	 *            - activity��������
	 * @return - �û���
	 */
	private static String getUserId(Context context) {
		sp = context.getSharedPreferences("loginRes", 0);
		String userId = sp.getString("userId", "");
		return userId;
	}

	/**
	 * չʾ�Զ���toast
	 * 
	 * @param msg
	 *            - Ҫ�������Ϣ
	 * @param context
	 *            - activity��������
	 */
	public static void toastShow(String msg, Context context) {
		View toastRoot = LayoutInflater.from(context).inflate(
				R.layout.toast_layout, null);
		Toast toast = new Toast(context);
		toast.setView(toastRoot);
		TextView textView = (TextView) toastRoot
				.findViewById(R.id.toast_notice);
		textView.setText(msg);
		toast.show();
	}

	/**
	 * ��ҳ��ȡ��ǰ��¼�û����ռ���Ϣ
	 * 
	 * @param pageSize
	 *            - ��ҳ��
	 * @param context
	 *            - activity��������
	 * @return - ��ǰ��¼�û����ռ���Ϣ
	 */
	public static ArrayList<Diary> getData(int pageSize, Context context) {
		UserManager manager = new UserManager(context);
		String pageNum = pageSize + ",5";
		return manager.selectDiaryByPage(new String[] { getUserId(context) },
				pageNum);
	}

	/**
	 * ��ҳ��ȡ��ǰ��¼�û���ѯ���ռ���Ϣ
	 * 
	 * @param info
	 *            - ��ѯ����
	 * @param pageSize
	 *            - ��ҳ��
	 * @param context
	 *            - activity��������
	 * @return - ��ǰ��¼�û���ѯ���ռ���Ϣ
	 */
	public static ArrayList<Diary> getSearchData(String info, int pageSize,
			Context context) {
		UserManager manager = new UserManager(context);
		String pageNum = pageSize + ",5";
		String searchInfo = "%" + info + "%";
		return manager.selectDiaryBySearch(new String[] { getUserId(context),
				searchInfo, searchInfo }, pageNum);
	}

	/**
	 * ��ҳ��ȡ��ǰ��¼�û����ж�λ���ռ���Ϣ
	 * 
	 * @param pageSize
	 *            - ��ҳ��
	 * @param context
	 *            - activity��������
	 * @return - ��ǰ��¼�û����ж�λ���ռ���Ϣ
	 */
	public static ArrayList<Diary> getSiteData(int pageSize, Context context) {
		UserManager manager = new UserManager(context);
		String pageNum = pageSize + ",5";
		return manager.selectDiaryBySite(new String[] { getUserId(context) },
				pageNum);
	}

	/**
	 * ɾ����ǰ��¼�û�ָ�����ռ�
	 * 
	 * @param diaryId
	 *            - ָ���ռǵ�ID
	 * @param context
	 *            - activity��������
	 */
	public static void deleteDiary(String diaryId, Context context) {
		UserManager manager = new UserManager(context);
		if (manager.deleteDiary(new String[] { getUserId(context), diaryId }) > 0) {
			toastShow("ɾ���ɹ�", context);
		} else {
			toastShow("ɾ��ʧ�ܣ�����ϵ���ݿ����Ա�Ժ����ԣ�", context);
		}
	}

	public static void updateDiary(String[] diaryInfo, String diaryId,
			Context context) {
		String[] whereArgs = new String[] { getUserId(context), diaryId };
		UserManager manager = new UserManager(context);
		if (manager.updateDiary(diaryInfo, whereArgs) > 0) {
			toastShow("���³ɹ�", context);
		} else {
			toastShow("����ʧ�ܣ�����ϵ���ݿ����Ա�Ժ����ԣ�", context);
		}
	}

}
