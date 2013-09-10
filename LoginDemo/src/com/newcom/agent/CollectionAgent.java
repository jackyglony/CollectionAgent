package com.newcom.agent;

import java.security.Provider;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.newcom.http.BaseHttp;
import com.newcom.statistics.baseproperty.DeviceUtil;
import com.newcom.statistics.channel.ChannelUtil;
import com.newcom.statistics.common.CommonUtil;
import com.newcom.statistics.common.Constants;
import com.newcom.statistics.activity.ActivityNumUtil;
import com.newcom.statistics.activitypath.ActivityPathUtil;

public class CollectionAgent extends BaseHttp {

	
	public CollectionAgent() {
		super();
	}
	/**
	 * �ռ����ֻ�����Ϣ,�����豸������Ϣ,����������Ϣ,����activity��(�˷���ֻ����ҳ���������)
	 * @param context
	 */
	public static void onMain(final Context context){
		CommonUtil.testProcess(context);
		new Thread(){
			@Override
			public void run() {
				CollFunction.firstStartDate=CommonUtil.firstStartDate();//�ռ���һ��������������Ϣ
				CollFunction.firstStartTime=CommonUtil.firstStartTime();//�ռ���һ��������ʱ����Ϣ
				/*
				CollFunction.activityFirstStartDate=CommonUtil.intoActivityDate();//�ս���ĳactivityҳ���ʱ��
				CollFunction.nowActivityName=CollFunction.getRunningActivityName(context);//�ս����activityҳ����
				*/
				System.out.println("---------------"+CommonUtil.checkFirstStart(context));
				if(CommonUtil.checkFirstStart(context)){//�ж��ǲ��ǵ�һ������Ӧ��
					CommonUtil.savaStartFlag(context);
					CollFunction.collBasePropertyInfo(context);//�ռ��ֻ�������Ϣ
					CollFunction.collchannelInfo(context);	//�ռ�Ӧ������������Ϣ
					CollFunction.collactivityInfo(context);//�ռ�Ӧ��������activityҳ���� 
				}else {
					CollFunction.collactivitypathInfo(context);//�����ǵ�һ����������������һ��ҳ����ת����Ϣ
				}
			}
		}.start();
	}
	/**
	 * �ռ�ÿ��activityҳ�����Ϣ,��ÿ��activityҳ�����
	 * @param context
	 */
	public static void onCreate(final Context context){
		new Thread(){
			@Override
			public void run() {
				CollFunction.activityFirstStartDate=CommonUtil.intoActivityDate();//�ս���ĳactivityҳ���ʱ��
				CollFunction.nowActivityName=CollFunction.getRunningActivityName(context);//�ս����activityҳ����
			}
		}.start();
	}
	/**
	 * ͳ������Ӧ������ʱ��,Ŀǰ����������,ͳ�Ʋ������˳�ʱ��???????
	 * @param context
	 */
	public static void onDestroy(final Context context){
		new Thread(){

			@Override
			public void run() {				
				CollFunction.colldurationInfo(context);//�ռ�����Ӧ��ʹ��ʱ����Ϣ
			}
		}.start();
	}
	/**
	 * ҳ����ת��ʱ��ͳ�Ƶ�ǰҳ�����Ϣ,ÿ��ҳ�����
	 * @param context
	 */
	public static void onPause(final Context context){
		new Thread(){
			@Override
			public void run() {				
				CollFunction.getactivitypathInfo(context);//�ռ���ǰactivityҳ����Ϣ
			}
		}.start();
	}
	/**
	 * �ռ��Զ����¼���Ϣ,ֻ���¼���Ӧ�ڲ����
	 * @param eventNum �¼����
	 * @param context
	 */
	public static void onEvent(final int eventNum,final Context context){
		new Thread(){
			@Override
			public void run() {
				CollFunction.colleventInfo(eventNum, context);//�ռ��Զ����¼���Ϣ
			}
		}.start();
	}
}
