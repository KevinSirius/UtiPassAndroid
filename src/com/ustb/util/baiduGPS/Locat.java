package com.ustb.util.baiduGPS;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import android.content.Context;
import android.webkit.WebView;
import android.widget.Toast;

public class Locat implements BDLocationListener {
	
	public LocationClient mlc=null;
	public GeofenceClient mgc=null;
	public Locations locations=new Locations();
	private Context context=null;
	private WebView webv=null;
	
	public Locat(Context c,WebView webv){
		this.context=c;
		this.webv=webv;
		onCreate();
		setLocationOption();
	}
	public Locat(){}
	
	//
	
	
	public void onCreate() {
		mlc = new LocationClient(context);
		mlc.setAK("6kR5zERGOry6sradwRdYbFAM");//baidu -key
		mlc.registerLocationListener(this);
		mgc=new GeofenceClient(context);
	}
	
	
	public void start(){
		if (mlc != null && !mlc.isStarted()){
			mlc.start();
		}

		if (mlc != null && mlc.isStarted()){
			mlc.requestLocation();
		} 
	}
	
	public void stop(){
		if (mlc != null && mlc.isStarted()){
			mlc.stop();
		}
		
	}
	
	//������ز���
	private  void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);				//��gps
		option.setCoorType("bd09ll");		//������������
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);	//��ȡ��ַ
		option.setAddrType("all");
		option.setScanSpan(5000);	//���ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
		option.setPriority(LocationClientOption.NetWorkFirst);      //������������
		//option.setPriority(LocationClientOption.GpsFirst);        //�����ã�Ĭ����gps����
		option.setPoiNumber(10);//��ȡ�Ǹ�  Poi
		option.disableCache(true);		
		mlc.setLocOption(option);
	}
	
	public void Toast(String txt){
		Toast.makeText(this.context, txt, 1000).show();
	}
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location==null) {
			Toast("��λʧ��!");
			return;
		}
		locations.setAddr(location.getAddrStr());
/*		locations.setLatitude(location.getLatitude());
		locations.setLontitude(location.getLongitude());
		locations.setRadius(location.getRadius());
		locations.setSpeed(location.getSpeed());
		locations.setTime(location.getTime());*/
		webv.loadUrl("javascript:aa()");
	}

	@Override
	public void onReceivePoi(BDLocation location) {
		// TODO Auto-generated method stub
		if (location==null) {
			Toast("��λʧ��!");
			return;
		}
		
	}
}

