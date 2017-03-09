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
 * 通用的一些方法
 * 
 * @author cyl
 *
 */
public class CommonFunction {
	private static SharedPreferences sp; // 本地轻量级存储

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            - 被加密明文
	 * @return - 加密后的暗文
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
	 * 验证手机号码格式
	 * 
	 * @param phonenum
	 *            - 手机号码
	 * @return - 验证结果,boolean值
	 */
	public static boolean checkPhoneNum(String phonenum) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phonenum);
		return matcher.matches();
	}

	/**
	 * 判断是否登录过
	 * 
	 * @param activity
	 *            - activity对象
	 * @return - boolean值,登录状态标识
	 */
	public static boolean loginStatus(Context context) {
		// 从sp中取出用户名,检测是否登录过
		if (getUserId(context) != null && !"".equals(getUserId(context))) {// 登录过
			return true;
		} else {// 没有登录过
			return false;
		}
	}

	/**
	 * 清除sp中保存的用户信息
	 */
	public static void clearSP(Context context) {
		sp = context.getSharedPreferences("loginRes", 0);
		sp.edit().clear().commit();
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param user
	 *            - user对象
	 * @param context
	 *            - activiy上下文
	 * @return - boolean值,用户是否存在的标识
	 */
	public static boolean userIsExistOrNot(User user, Context context) {
		UserManager manager = new UserManager(context);
		User u = manager.selectForLogin(new String[] { user.getUserId() });
		if (u.getUserId() != null) {// 用户存在
			return true;
		} else {// 用户不存在
			return false;
		}
	}

	/**
	 * 登录检测
	 * 
	 * @param user
	 *            - user对象
	 * @param context
	 *            - activity的上下文
	 * @return - boolean值,用户名、密码是否匹配数据库的标识
	 */
	public static boolean checkForLogin(User user, Context context) {
		UserManager manager = new UserManager(context);
		User u = manager.selectForLogin(new String[] { user.getUserId() });
		String pwd = getMD5Str(user.getPassword());
		if (u.getUserId() != null && pwd.equals(u.getPassword())) {
			// 将用户名存放至sp中
			sp = context.getSharedPreferences("loginRes", 0);
			sp.edit().putString("userId", user.getUserId()).commit();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置diary对象
	 * 
	 * @param str
	 *            - 从activity处传来的diary对象相关信息
	 * @param act
	 *            - activity对象
	 */
	public static void setDiary(String[] str, Activity act) {
		if (str[2].equals("定位中...") || str[2].equals("定位失败！")
				|| str[2].equals("点击左侧按钮开启定位")) {
			str[2] = "";
		}
		UserManager manager = new UserManager(act);
		Diary diary = new Diary(getUserId(act), str[0], str[1], getTime(),
				str[2], str[3], str[4]);
		if (manager.addDiary(diary) > 0) {
			toastShow("保存成功", act);
		} else {
			toastShow("保存失败,请联系数据库管理员稍后再试!", act);
		}
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return - 当前系统时间
	 */
	public static String getTime() {
		Calendar c = Calendar.getInstance();
		StringBuffer time = new StringBuffer();
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "星期天";
		} else if ("2".equals(mWay)) {
			mWay = "星期一";
		} else if ("3".equals(mWay)) {
			mWay = "星期二";
		} else if ("4".equals(mWay)) {
			mWay = "星期三";
		} else if ("5".equals(mWay)) {
			mWay = "星期四";
		} else if ("6".equals(mWay)) {
			mWay = "星期五";
		} else if ("7".equals(mWay)) {
			mWay = "星期六";
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
	 * 转换时分秒的格式
	 * 
	 * @param t
	 *            - 时分秒的数值
	 * @return - 格式转换后的时分秒的字符串
	 */
	public static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

	/**
	 * 获取用户名
	 * 
	 * @param context
	 *            - activity的上下文
	 * @return - 用户名
	 */
	private static String getUserId(Context context) {
		sp = context.getSharedPreferences("loginRes", 0);
		String userId = sp.getString("userId", "");
		return userId;
	}

	/**
	 * 展示自定义toast
	 * 
	 * @param msg
	 *            - 要输出的信息
	 * @param context
	 *            - activity的上下文
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
	 * 分页获取当前登录用户的日记信息
	 * 
	 * @param pageSize
	 *            - 分页数
	 * @param context
	 *            - activity的上下文
	 * @return - 当前登录用户的日记信息
	 */
	public static ArrayList<Diary> getData(int pageSize, Context context) {
		UserManager manager = new UserManager(context);
		String pageNum = pageSize + ",5";
		return manager.selectDiaryByPage(new String[] { getUserId(context) },
				pageNum);
	}

	/**
	 * 分页获取当前登录用户查询的日记信息
	 * 
	 * @param info
	 *            - 查询条件
	 * @param pageSize
	 *            - 分页数
	 * @param context
	 *            - activity的上下文
	 * @return - 当前登录用户查询的日记信息
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
	 * 分页获取当前登录用户含有定位的日记信息
	 * 
	 * @param pageSize
	 *            - 分页数
	 * @param context
	 *            - activity的上下文
	 * @return - 当前登录用户含有定位的日记信息
	 */
	public static ArrayList<Diary> getSiteData(int pageSize, Context context) {
		UserManager manager = new UserManager(context);
		String pageNum = pageSize + ",5";
		return manager.selectDiaryBySite(new String[] { getUserId(context) },
				pageNum);
	}

	/**
	 * 删除当前登录用户指定的日记
	 * 
	 * @param diaryId
	 *            - 指定日记的ID
	 * @param context
	 *            - activity的上下文
	 */
	public static void deleteDiary(String diaryId, Context context) {
		UserManager manager = new UserManager(context);
		if (manager.deleteDiary(new String[] { getUserId(context), diaryId }) > 0) {
			toastShow("删除成功", context);
		} else {
			toastShow("删除失败，请联系数据库管理员稍后再试！", context);
		}
	}

	public static void updateDiary(String[] diaryInfo, String diaryId,
			Context context) {
		String[] whereArgs = new String[] { getUserId(context), diaryId };
		UserManager manager = new UserManager(context);
		if (manager.updateDiary(diaryInfo, whereArgs) > 0) {
			toastShow("更新成功", context);
		} else {
			toastShow("更新失败，请联系数据库管理员稍后再试！", context);
		}
	}

}
