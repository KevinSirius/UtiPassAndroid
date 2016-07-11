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
		// 这时你可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。这样计算出来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom
		// 镜像）。但应当明白的是，出现类似情况的可能性基本可以忽略。
		String m_szDevIDShort = "35" + Build.BOARD.length() % 10
				+ Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
				+ Build.HOST.length() % 10 + Build.ID.length() % 10
				+ Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
				+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
				+ Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
																		// digits
		// The IMEI: 仅仅只对Android手机有效:
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String m_szImei = telephonyManager.getDeviceId();
		// The Android ID , 通常被认为不可信，因为它有时为null
		String m_szAndroidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		String str = m_szDevIDShort + m_szImei + m_szAndroidID;// +m_szWLANMAC+m_szBTMAC;
		MD5 md5 = new MD5();
		String number_str = md5.getMD5(str.getBytes());
		return number_str;
	}
}
