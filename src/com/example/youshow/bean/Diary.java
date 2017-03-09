package com.example.youshow.bean;

/**
 * �ռ�ʵ����
 * 
 * @author cyl
 *
 */
public class Diary {
	private int diaryId; // �ռ�ID,����
	private String userId; // �û���
	private String diaryTitle; // ����
	private String diaryContent; // ����
	private String diaryTime; // ʱ��
	private String diarySite; // λ��
	private String diaryWeather; // ����
	private String diaryMood; // ����

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
