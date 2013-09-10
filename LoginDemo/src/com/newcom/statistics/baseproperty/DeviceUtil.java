package com.newcom.statistics.baseproperty;

import java.util.UUID;

import com.google.gson.Gson;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
/**
 * ��ȡ�ն����Թ�����
 * @author zhaohonglin
 */
public class DeviceUtil {
	protected static final String a = DeviceUtil.class.getName();
	/**
	 * ��ȡ�豸�ͺ� 
	 * @return
	 */
	public static String getDeviceModel(){
		return Build.MODEL; 
	}
	/**
	 * ��ȡ�ն�����
	 * @return
	 */
	public static String getDeviceType(){
		return "0"; // android=0��ios=1
	}
	/**
	 * ��ȡ����ϵͳ�汾
	 * @return 
	 */
	public static String getOSVersion(){

		return "android"+Build.VERSION.RELEASE; 
	}

	/**
	 * ��ȡ�豸�ֱ��� 
	 * @param paramContext
	 * @return
	 */
	public static String getResolution(Context paramContext)
	{
		DisplayMetrics localDisplayMetrics;
		try
		{
			localDisplayMetrics = new DisplayMetrics();
			WindowManager localWindowManager = (WindowManager)(WindowManager)paramContext.getSystemService("window");

			localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);

			int i = localDisplayMetrics.widthPixels;
			int j = localDisplayMetrics.heightPixels;

			String str = String.valueOf(j) + "*" + String.valueOf(i);

			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
			return "Unknown";
		}
	}
	/**
	 * ��ȡ������Ӫ��
	 * @param paramContext
	 * @return
	 */
	public static String getOperator(Context paramContext){
		String msg="Unknown";
		TelephonyManager telManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE); 
		String operator = telManager.getSimOperator();
		if(operator!=null){ 
			if(operator.equals("46000") || operator.equals("46002")|| operator.equals("46007")){
				msg="�й��ƶ�";//�й��ƶ�
			}else if(operator.equals("46001")){
				msg="�й���ͨ";//�й���ͨ
			}else if(operator.equals("46003")){
				msg="�й�����";//�й�����
			} }
		return msg; 
	}
	/**
	 * ��ȡ�豸��� 
	 * ��UUID�����豸��Ψһ���
	 * @param paramContext
	 * @return
	 */
	@SuppressWarnings("unused")
	public  static String getDeviceNum(Context paramContext){
		TelephonyManager tm = (TelephonyManager) ((ContextWrapper) paramContext).getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		String tmDevice = "" + tm.getDeviceId();    
		String tmSerial = "" + tm.getSimSerialNumber();   //TODO �����ǻ��ֻ��������,�����ֻ�������Ϊ�������û�??
		String androidId = "" + android.provider.Settings.Secure.getString(paramContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID); 
		if (null == tmDevice){
			Log.i(a,"[tmDevice]"+tmDevice);
			tmDevice = new String();
		}
		if (null == androidId){
			Log.i(a,"[androidId]"+androidId);
			androidId = new String();
		}		
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());   
		return deviceUuid.toString();}

	/**
	 * ���豸��Ϣ��װ��json
	 * @param paramContext
	 * @return
	 */
	/*public static String  getDeviceData(Context paramContext) {
		DeviceData deviceData=new DeviceData();
		deviceData.setDevModel(getDeviceModel());
		deviceData.setDevType(getDeviceType());
		deviceData.setResolution(getResolution(paramContext));
		deviceData.setOsVersion(getOSVersion());
		deviceData.setOperator(getOperator(paramContext));
		Gson gson = new Gson();
		String json=gson.toJson(deviceData);
		Log.i("MobclickAgent", json);
		return json;
	}	*/
}
