package com.ustb.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class DeviceId {

	private Context context;

	public DeviceId(Context context) {
		this.context = context;
	}

	public String getDeviceId() {
		// ��ʱ�����ͨ��ȡ��ROM�汾�������̡�CPU�ͺš��Լ�����Ӳ����Ϣ��ʵ����һ�㡣�������������ID����Ψһ�ģ���Ϊ��������ֻ�Ӧ����ͬ����Ӳ���Լ�Rom
		// ���񣩡���Ӧ�����׵��ǣ�������������Ŀ����Ի������Ժ��ԡ�
		String m_szDevIDShort = "35" + Build.BOARD.length() % 10
				+ Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
				+ Build.HOST.length() % 10 + Build.ID.length() % 10
				+ Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
				+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
				+ Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
																		// digits
		// The IMEI: ����ֻ��Android�ֻ���Ч:
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String m_szImei = telephonyManager.getDeviceId();
		// The Android ID , ͨ������Ϊ�����ţ���Ϊ����ʱΪnull
		String m_szAndroidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		String str = m_szDevIDShort + m_szImei + m_szAndroidID;// +m_szWLANMAC+m_szBTMAC;
		MD5 md5 = new MD5();
		String number_str = md5.getMD5(str.getBytes());
		return number_str;
	}
}
