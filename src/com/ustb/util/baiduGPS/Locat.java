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
	
	//设置相关参数
	private  void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);				//打开gps
		option.setCoorType("bd09ll");		//设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);	//获取地址
		option.setAddrType("all");
		option.setScanSpan(5000);	//设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setPriority(LocationClientOption.NetWorkFirst);      //设置网络优先
		//option.setPriority(LocationClientOption.GpsFirst);        //不设置，默认是gps优先
		option.setPoiNumber(10);//获取是个  Poi
		option.disableCache(true);		
		mlc.setLocOption(option);
	}
	
	public void Toast(String txt){
		Toast.makeText(this.context, txt, 1000).show();
	}
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location==null) {
			Toast("定位失败!");
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
			Toast("定位失败!");
			return;
		}
		
	}
}

