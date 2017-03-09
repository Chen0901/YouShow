package com.example.youshow.bean;

/**
 * 日记实体类
 * 
 * @author cyl
 *
 */
public class Diary {
	private int diaryId; // 日记ID,主键
	private String userId; // 用户名
	private String diaryTitle; // 标题
	private String diaryContent; // 内容
	private String diaryTime; // 时间
	private String diarySite; // 位置
	private String diaryWeather; // 天气
	private String diaryMood; // 心情

	public int getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDiaryTitle() {
		return diaryTitle;
	}

	public void setDiaryTitle(String diaryTitle) {
		this.diaryTitle = diaryTitle;
	}

	public String getDiaryContent() {
		return diaryContent;
	}

	public void setDiaryContent(String diaryContent) {
		this.diaryContent = diaryContent;
	}

	public String getDiaryTime() {
		return diaryTime;
	}

	public void setDiaryTime(String diaryTime) {
		this.diaryTime = diaryTime;
	}

	public String getDiarySite() {
		return diarySite;
	}

	public void setDiarySite(String diarySite) {
		this.diarySite = diarySite;
	}

	public String getDiaryWeather() {
		return diaryWeather;
	}

	public void setDiaryWeather(String diaryWeather) {
		this.diaryWeather = diaryWeather;
	}

	public String getDiaryMood() {
		return diaryMood;
	}

	public void setDiaryMood(String diaryMood) {
		this.diaryMood = diaryMood;
	}

	public Diary(int diaryId, String userId, String diaryTitle,
			String diaryContent, String diaryTime, String diarySite,
			String diaryWeather, String diaryMood) {
		this.diaryId = diaryId;
		this.userId = userId;
		this.diaryTitle = diaryTitle;
		this.diaryContent = diaryContent;
		this.diaryTime = diaryTime;
		this.diarySite = diarySite;
		this.diaryWeather = diaryWeather;
		this.diaryMood = diaryMood;
	}

	
	
	public Diary(String userId, String diaryTitle, String diaryContent,
			String diaryTime, String diarySite, String diaryWeather,
			String diaryMood) {
		this.userId = userId;
		this.diaryTitle = diaryTitle;
		this.diaryContent = diaryContent;
		this.diaryTime = diaryTime;
		this.diarySite = diarySite;
		this.diaryWeather = diaryWeather;
		this.diaryMood = diaryMood;
	}

	public Diary() {
	}

}
