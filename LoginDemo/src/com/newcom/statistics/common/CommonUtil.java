package com.newcom.statistics.common;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import com.newcom.agent.CollFunction;
import com.newcom.statistics.activitypath.ActivityPathUtil;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * ����������
 * @author zhaohonglin
 *
 */
public class CommonUtil {
	static boolean flag=true;
	/**
	 * �ж�Ӧ�ó���ʲôʱ���˳�
	 * @param paramContext
	 * @return ����true�������������,����false��ǰӦ��ֹͣ����
	 */

	public  static boolean judgeApp(Context paramContext)
	{
		ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");

		List<?> localList = localActivityManager.getRunningAppProcesses();
		if (localList == null)
			return false;

		String str = paramContext.getPackageName();
		for (Iterator<?> localIterator = localList.iterator(); localIterator.hasNext(); ) {
			ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
			if ((localRunningAppProcessInfo.importance == 100) && (localRunningAppProcessInfo.processName.equals(str)))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * ���ϼ��Ӧ�ó���ʲôʱ�����,һ��Ӧ�ó���������򱣴�,�Զ����¼�,ҳ�����·��,����ʱ������Ϣ
	 * @param paramContext
	 */
	public static void testProcess(final Context paramContext){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(flag){
					//System.out.println("-----testProcess-----");
					if(!CommonUtil.judgeApp(paramContext)){

						//ActivityPathUtil.saveActivityPathDataToJSON(paramContext);
						//EventUtil.saveEventDataToJSON(paramContext);
						//AppDurationUtil.saveAppDurationData(paramContext);
						//System.out.println("endProcess");
						CollFunction.colldurationInfo(paramContext);
						/**
						 * ��������
						 */
						/*ChannelUtil.getChannelData(paramContext);
						DeviceUtil.getDeviceData(paramContext);
						InternetUtil.getInternetData(paramContext);*/

						flag=false;
					}
				}
			}
		}).start();
	}
//	/**
//	 * ���ϼ�������Ƿ��ж�,һ�������ж��򱣴��Զ����¼�,ҳ�����·��,����ʱ������Ϣ
//	 * @param paramContext
//	 */
//	public static void testNet(final Context paramContext){
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(flag){
//					if(!CommonUtil.checkNet(paramContext)){
//
//						ActivityPathUtil.saveActivityPathDataToJSON(paramContext);
//						EventUtil.saveEventDataToJSON(paramContext);
//						AppDurationUtil.saveAppDurationData(paramContext);
//
//
//						/**
//						 * ��������
//						 */
//						ChannelUtil.getChannelData(paramContext);
//						DeviceUtil.getDeviceData(paramContext);
//						InternetUtil.getInternetData(paramContext);
//
//						flag=false;
//					}
//				}
//			}
//		}).start();
//	}
	/**
	 * ��ȡӦ�õ���Activity
	 * @param paramContext
	 * @return
	 */
	public static String getMainActivity(Context paramContext){
		String classname = null;
		Intent intent = new Intent(Intent.ACTION_MAIN, null); 
		intent.addCategory(Intent.CATEGORY_LAUNCHER);  
		List<ResolveInfo> list =paramContext.getPackageManager().
				queryIntentActivities(intent,   PackageManager.GET_ACTIVITIES);
		for (int i = 0; i < list.size(); i++){
			String  packageName=list.get(i).activityInfo.packageName;
			if(packageName.equals(paramContext.getPackageName())){
				classname=list.get(i).activityInfo.name;
			}
		}
		return classname;
	}
	/**
	 * ��ȡ�ս���Ӧ��ʱ��ʱ��
	 * @return ���ص�ǰ��ʱ��
	 */
	public static Date firstStartDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date firststartDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
		return firststartDate;
	}
	/**
	 * ��ȡ�ս���Ӧ��ʱ������ʱ��
	 * @return ���ص�ǰ��ʱ��
	 */
	public static String firstStartTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date firststartDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��    
		String firststartTime = formatter.format(firststartDate);
		return firststartTime;
	}
	/**
	 * ��ȡҳ������ʱ�䣬�¼�����ʱ��ȵ�ǰʱ��
	 * @return ���ص�ǰ��ʱ��
	 */
	public static String getNowTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date nowDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��    
		String nowTime = formatter.format(nowDate);
		return nowTime;
	}
	/**
	 * ��ȡ�ս���ĳactivityʱ��ʱ�䣬Ϊ��ͳ��ҳ�����ʱ��
	 * @return ���ص�ǰ��ʱ��
	 */
	public static Date intoActivityDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
		return startDate;
	}

	/**
	 * ��ȡ��ʽ�����ʱ��
	 * @param timeMillis
	 * @return
	 */
	public static String getFormatTime(long timeMillis)
	{
		Date localDate = new Date(timeMillis);
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime=localSimpleDateFormat.format(localDate);
		return currentTime;
	}
	/**
	 * ��ȡ��ʽ�����ʱ��
	 * @param timeMillis
	 * @return
	 */
/*	public static String getFormatTime()
	{
		Date localDate = new Date();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime=localSimpleDateFormat.format(localDate);
		return currentTime;
	}*/
	/**
	 * ����Ӧ��������ʱ��
	 * @param paramContext
	 */
	public static void saveStartTime(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("mobclick_agent_" + paramContext.getPackageName(), 0);
		Editor editor=time.edit().putLong("start_millis",System.currentTimeMillis());
		editor.commit();
	}
	/**
	 * �ж��ǲ��ǵ�һ������
	 * @param paramContext
	 */
	public static boolean checkFirstStart(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("collection_agent_" + paramContext.getPackageName(), 0);
		int flag=time.getInt("flag",0);
		if(flag==0){
			return true;
		}
		return false;
	}	
	/**
	 * ÿ����������һ��
	 * @param paramContext
	 */
	public static void savaStartFlag(Context paramContext){
		SharedPreferences time=paramContext.getSharedPreferences("collection_agent_" + paramContext.getPackageName(), 0);
		int flag=time.getInt("flag",0);
		Editor editor=time.edit().putInt("flag",(flag+1));
		editor.commit();
	}	
	/**
	 * �������״��
	 * @param paramContext
	 * @return 
	 */
	public static boolean checkNet(Context paramContext)
	{  
		ConnectivityManager manager = (ConnectivityManager) paramContext.getSystemService("connectivity");    
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();    
		if (networkinfo == null || !networkinfo.isAvailable()) {    
			return false;  
		}  
		return true;  
	}
}
