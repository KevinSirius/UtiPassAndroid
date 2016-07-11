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
		// The IMEI: ����ֻ��Android�ֻ���Ч:
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String m_szImei = telephonyManager.getDeviceId();
		return m_szImei;
	}

	public String getdevicrID() {
		// ��ʱ�����ͨ��ȡ��ROM�汾�������̡�CPU�ͺš��Լ�����Ӳ����Ϣ��ʵ����һ�㡣�������������ID����Ψһ�ģ���Ϊ��������ֻ�Ӧ����ͬ����Ӳ���Լ�Rom
		// ���񣩡���Ӧ�����׵��ǣ�������������Ŀ����Ի������Ժ��ԡ�
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
		// The Android ID , ͨ������Ϊ�����ţ���Ϊ����ʱΪnull
		String m_szAndroidID = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		return m_szAndroidID;
	}

	/*
	 * public String getWlanMac(){ //The WLAN MAC Address string,
	 * ����һ��ΨһID����������ҪΪ��Ĺ��̼���android.permission.ACCESS_WIFI_STATE Ȩ�ޣ����������ַ��Ϊnull
	 * WifiManager wm =(WifiManager)getSystemService(Context.WIFI_SERVICE);
	 * String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
	 * if(m_szWLANMAC.equals(null)) { return null; } else
	 * if(m_szWLANMAC.contains(":")){ m_szWLANMAC = m_szWLANMAC.replace(":",
	 * ""); } return m_szWLANMAC; }
	 * 
	 * public String getBtMac(){ //The BT MAC Address string,
	 * ֻ�����������豸�����С�����Ҫ����android.permission.BLUETOOTH Ȩ��. BluetoothAdapter
	 * m_BluetoothAdapter =
	 * null;//��ָ����ӦΪ�����û���������޷���ȡ��BluetoothAdapter.getDefaultAdapter();������ϲ�������
	 * m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); String
	 * m_szBTMAC = m_BluetoothAdapter.getAddress();
	 *//**
	 * �õ���ð�� �� ȡȥ��ð�ŵ�Mac��ַ
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
