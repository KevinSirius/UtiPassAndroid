package com.ustb.utils;

import android.R.bool;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.telephony.TelephonyManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class Util {
	public Context context;
	public Util(){}
	public Util(Context c){
		context=c;
	}
	
	
	//获取手机 imei
	public String getImei(){
		TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
		return imei;
	}
	
	//toast
	@JavascriptInterface
	public void Toast(String txt){
		Toast.makeText(this.context, txt,1000).show();
	}
	//打开闪光灯
	@JavascriptInterface
	public void isStartLamp(String bo){
		Camera camera= Camera.open();
		camera.startPreview();
		
		Parameters parame=camera.getParameters();
		if (bo.equals("true"))   
			parame.setFlashMode(parame.FLASH_MODE_TORCH);
			
		else
			parame.setFlashMode(parame.FLASH_MODE_OFF);
			
		camera.setParameters(parame);
	}
}
