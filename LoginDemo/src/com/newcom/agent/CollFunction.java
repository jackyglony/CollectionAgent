package com.newcom.agent;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.newcom.http.BaseHttp;
import com.newcom.statistics.activity.ActivityNumUtil;
import com.newcom.statistics.activitypath.ActivityPathUtil;
import com.newcom.statistics.baseproperty.DeviceUtil;
import com.newcom.statistics.channel.ChannelUtil;
import com.newcom.statistics.common.CommonUtil;
import com.newcom.statistics.common.Constants;

public class CollFunction extends BaseHttp{
	static Date firstStartDate;//��ȡ����ʱ����ʱ��
	static String firstStartTime;//Ӧ������ʱ��ʱ��
	static Date activityFirstStartDate;//ĳ��activity����ʱ��
	static String nowActivityName;//��ǰ�����е�activityҳ����
	public CollFunction() {
		super();
	}
	/**
	 * �ռ������ֻ���Ϣ
	 * @param deviceId �豸���
	 * @param devModel �豸�ͺ�
	 * @param devType �ն�����
	 * @param operator �豸�ֱ���
	 * @param osVersion ϵͳ�汾
	 * @param resolution ������Ӫ��
	 * @return
	 */
	public static boolean collBasePropertyInfo(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		Build bd = new Build();  
		String devModel = bd.MODEL;
		String deviceId=DeviceUtil.getDeviceNum(context);
		int operator=tm.getNetworkType();
		int devType=0;
		int osVersion=android.os.Build.VERSION_CODES.BASE;
		String resolution=DeviceUtil.getResolution(context);
		try{
			JSONObject jsonObject = new JSONObject();  
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("devModel", devModel);  
            jsonObject.put("devType", devType); 
            jsonObject.put("operator", operator); 
            jsonObject.put("osVersion", osVersion); 
            jsonObject.put("resolution", resolution); 
          
            addNameValuePair("basepropertyinfo_jsonString", jsonObject.toString());
            System.out.println("basepropertyinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.BASEPROPERTYSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �ռ����缰λ����Ϣ
	 * @param deviceId �豸���
	 * @param connectMode ������ʽ
	 * @param startTime ����ʱ��
	 * @param longitude ����
	 * @param latitude ά��
	 * @return
	 */
	public static boolean collInternetInfo(Context context){
		
		String deviceId=DeviceUtil.getDeviceNum(context);
		String startTime =  CommonUtil.getNowTime();
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		String connectMode = info.getTypeName();
		
		
		//�õ�ϵͳ�����LocationManager����
		LocationManager locationManager;
		String contextService=Context.LOCATION_SERVICE;
		//ͨ��ϵͳ����ȡ��LocationManager����
		locationManager=(LocationManager) context.getSystemService(contextService);
		//ʹ�ñ�׼���ϣ���ϵͳ�Զ�ѡ����õ����λ���ṩ�����ṩλ��
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//�߾���
		criteria.setAltitudeRequired(false);//��Ҫ�󺣰�
		criteria.setBearingRequired(false);//��Ҫ��λ
		criteria.setCostAllowed(true);//�����л���
		criteria.setPowerRequirement(Criteria.POWER_LOW);//�͹���
		//�ӿ��õ�λ���ṩ���У�ƥ�����ϱ�׼������ṩ��
		String provider = locationManager.getBestProvider(criteria, true);
		//������һ�α仯��λ��
		Location location = locationManager.getLastKnownLocation(provider);
		
        String longitude=location.getLongitude()+"";
        String latitude=location.getLatitude()+"";
        location.getLatitude();
        
        //loctionManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        
        
        
        
        
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("connectMode", connectMode);  
            jsonObject.put("startTime", startTime);
            jsonObject.put("longitude", longitude); 
            jsonObject.put("latitude", latitude); 
          
            addNameValuePair("internetinfo_jsonString", jsonObject.toString());
            System.out.println("internetinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.INTERNETSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �ռ�����������Ϣ
	 * @param deviceId �豸���
	 * @param downloadChannel ��������
	 * @param appVersion ����汾
	 * @param lastChannel �ϴ�����
	 * @param update ����ʱ��
	 * @return
	 */
	public static boolean collchannelInfo(Context context){
		
		String deviceId=DeviceUtil.getDeviceNum(context);
		String downloadChannel=ChannelUtil.getDownloadChannel(context);
		String appVersion=ChannelUtil.getAppVersion(context); 
		String upTime =   CommonUtil.getNowTime();
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("downloadChannel", downloadChannel);
			jsonObject.put("appVersion", appVersion);  
			jsonObject.put("upTime", upTime);
          
            addNameValuePair("channelinfo_jsonString", jsonObject.toString());
            System.out.println("channelinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.CHANNELSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �ռ��Զ����¼���Ϣ
	 * @param deviceId �豸���
	 * @param eventNum �Զ����¼����
	 * @param eventTime �¼�����ʱ��
	 * @param appVersion ����汾
	 * @param inTime ���ʱ��
	 * @return
	 */
	public static boolean colleventInfo(int eventNum,Context context){
		String deviceId=DeviceUtil.getDeviceNum(context);
		String eventTime=  CommonUtil.getNowTime();
		String appVersion=ChannelUtil.getAppVersion(context);
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("eventNum", eventNum);
			jsonObject.put("appVersion", appVersion);  
			jsonObject.put("eventTime", eventTime);
          
            addNameValuePair("eventinfo_jsonString", jsonObject.toString());
            System.out.println("eventinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.EVENTSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * ��ȡҳ����
	 * @param activity activityȫ����
	 * @return
	 */
	public static void collactivityInfo(Context context){
		String activityName = null;
		/*InputStream inputStream=context.getClass().getClassLoader().getResourceAsStream("aaa.xml");
		System.out.println("inputstream1:"+inputStream);
		DomgetresponseXml domXml= new DomgetresponseXml();*/
		try {
			//System.out.println("inputstream2:"+inputStream);
			//activityNameList = domXml.getActivitynames(inputStream);
			List<String> activityNameList=ActivityNumUtil.getAllActivity(context);
			JSONArray jsonArray = new JSONArray();
		for(int i = 0;i < activityNameList.size();i++){
			activityName=activityNameList.get(i);
			System.out.println("name:"+activityName);
			JSONObject temp  = new JSONObject();
			temp.put("activityname", activityName);
			jsonArray.put(i,temp);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name",jsonArray);
			

            addNameValuePair("activityinfo_jsonString", jsonObject.toString());
            System.out.println("activityinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.GETACTIVITYNUMSERVLET); 
			getresponse();
			JSONObject object =BaseHttp.getJson();
			String activity=object.getString("activity");
			System.out.println("activity:"+activity);
			ActivityNumUtil.saveActivityNumDataToFile(context,activity);//����activity��json�������ļ���
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * ��ȡ��ǰ����activity��
	 * @param context
	 * @return
	 */
	public static String getRunningActivityName(Context context){
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
        RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String runningActivityName = info.topActivity.getClassName();
        System.out.println("runningActivityName:"+runningActivityName);//��ǰactivity��
        return runningActivityName;
	}
	/**
	 * ��ȡ����ǰҳ���ҳ����Ϣ
	 * @param context
	 */
	public static void getactivitypathInfo(Context context){
		
        String startTime =   CommonUtil.getNowTime();//���ʱ��
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date endDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��   
		long activityDuration=endDate.getTime()-activityFirstStartDate.getTime();//ʹ��ʱ��
		int nowActivityNum=ActivityNumUtil.getActivityNumByActivity(context,nowActivityName);
		String appVersion=ChannelUtil.getAppVersion(context); 
		//System.out.println("nowActivityNum:"+nowActivityNum);
		ActivityPathUtil.saveActivityPathData(context, nowActivityNum, activityDuration,startTime);
		ActivityPathUtil.saveActivityPathDataToJSON(context);
	}
	/**
	 * ����ҳ�����·����Ϣ
	 * @param deviceId �豸���
	 * @param activityDuration ҳ�����ʱ��
	 * @param startTime ����ʱ��
	 * @param lastActivityNum ��һ��ҳ����
	 * @param nowActivityNum ��ǰҳ����
	 * @param nextActivityNum ��һ��ҳ����
	 * @return
	 */
	public static boolean collactivitypathInfo(Context context){
		String activityPathString=ActivityPathUtil.getActivityPathDataFromFile(context);
		System.out.println("activityPathString:"+activityPathString);
		try{
			//net.sf.json.JSONObject activityPathjsonObject =net.sf.json.JSONObject.fromObject(activityPathString);
			//net.sf.json.JSONArray resJsonArray=new net.sf.json.JSONArray();
			//JSONObject jsonObject = new JSONObject();
			/*jsonObject.put("deviceId", deviceId);
			jsonObject.put("duration", activityDuration);
			jsonObject.put("startTime", startTime);
			jsonObject.put("nowActivityNum", nowActivityNum);*/
			//jsonObject.put("nowActivityNum", nowActivityNum); 
			//jsonObject.put("nextActivityNum", nextActivityNum);
            addNameValuePair("activitypathinfo_jsonString", activityPathString);
            System.out.println("activitypathinfo_jsonString:"+activityPathString);
			sendrequest(Constants.ACTIVITYPATHSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �ռ�Ӧ��ʹ��ʱ����Ϣ
	 * @param deviceId 
	 * @param startTime 
	 * @param duration 
	 * @return
	 */
	public static boolean colldurationInfo(Context context){
		String deviceId=DeviceUtil.getDeviceNum(context);	//�豸���
		String startTime =   CommonUtil.getNowTime();	//����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date endDate = new Date(System.currentTimeMillis()); 
		long duration=endDate.getTime()-firstStartDate.getTime();	//ʹ��ʱ��
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("deviceId", deviceId);
			jsonObject.put("startTime", startTime);
			jsonObject.put("duration", duration);

            addNameValuePair("durationinfo_jsonString", jsonObject.toString());
            System.out.println("durationinfo_jsonString:"+jsonObject.toString());
			sendrequest(Constants.DURATIONSERVLET);
			getresponse();
			JSONObject object =BaseHttp.getJson();
			System.out.println("errcode:"+object.getString("errcode"));
			if(object.getString("errcode").equals("123")){//���ش�����
			return true;
			}else{
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
