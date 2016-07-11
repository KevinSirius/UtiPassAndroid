package com.ustb.utils;

import com.ericssonlabs.BarCodeTestActivity;

import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
//import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
 
public class DeviceUuidFactory extends Activity {
  
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		String str = getIMIE() + getdevicrID() + getAndroidID();// +m_szWLANMAC+m_szBTMAC;
		Intent intent = new Intent();
		intent.putExtra(str, str);
		intent.setClass(DeviceUuidFactory.this, BarCodeTestActivity.class);
		startActivityForResult(intent, 100);
	}

	public String getIMIE() {
		// The IMEI: 仅仅只对Android手机有效:
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String m_szImei = telephonyManager.getDeviceId();
		return m_szImei;
	}

	public String getdevicrID() {
		// 这时你可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。这样计算出来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom
		// 镜像）。但应当明白的是，出现类似情况的可能性基本可以忽略。
		String m_szDevIDShort = "35"
				+ // we make this look like a valid IMEI
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10
				+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
				+ Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
				+ Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; // 13 digits
		return m_szDevIDShort;
	}

	public String getAndroidID() {
		// The Android ID , 通常被认为不可信，因为它有时为null
		String m_szAndroidID = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		return m_szAndroidID;
	}

	/*
	 * public String getWlanMac(){ //The WLAN MAC Address string,
	 * 是另一个唯一ID。但是你需要为你的工程加入android.permission.ACCESS_WIFI_STATE 权限，否则这个地址会为null
	 * WifiManager wm =(WifiManager)getSystemService(Context.WIFI_SERVICE);
	 * String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
	 * if(m_szWLANMAC.equals(null)) { return null; } else
	 * if(m_szWLANMAC.contains(":")){ m_szWLANMAC = m_szWLANMAC.replace(":",
	 * ""); } return m_szWLANMAC; }
	 * 
	 * public String getBtMac(){ //The BT MAC Address string,
	 * 只在有蓝牙的设备上运行。并且要加入android.permission.BLUETOOTH 权限. BluetoothAdapter
	 * m_BluetoothAdapter =
	 * null;//空指针是应为虚拟机没有蓝牙，无法获取到BluetoothAdapter.getDefaultAdapter();到真机上测试正常
	 * m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); String
	 * m_szBTMAC = m_BluetoothAdapter.getAddress();
	 *//**
	 * 得到加冒号 或 取去掉冒号的Mac地址
	 * 
	 * @author YOLANDA
	 * @param mac
	 * @return
	 */
	/*
	 * 
	 * if(m_szBTMAC.equals(" ")) { return null; } else
	 * if(m_szBTMAC.contains(":")){ m_szBTMAC = m_szBTMAC.replace(":", ""); }
	 * 
	 * return m_szBTMAC; }
	 */

}
