package com.newcom.statistics.activitypath;

public class ActivityPathData {
	private int activityNum;//ҳ����
	private long activityDuration;//ҳ�����ʱ��
	private String startTime;//�������ʱ��
	private String appVersion;//����汾��

	public int getActivityNum() {
		return activityNum;
	}
	public void setActivityNum(int activityNum) {
		this.activityNum = activityNum;
	}
	public long getActivityDuration() {
		return activityDuration;
	}
	public void setActivityDuration(long activityDuration) {
		this.activityDuration = activityDuration;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
}
