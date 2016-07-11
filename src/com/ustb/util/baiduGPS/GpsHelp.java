package com.ustb.util.baiduGPS;

import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class GpsHelp {
	public Context context;
	public Locat locat= null;
	public Gson gson=new Gson();
	
	public GpsHelp(){}
	
	public GpsHelp(Context c,WebView web){
		this.context=c;
		locat=new Locat(context,web);
		locat.start();
	}
	
	//停止 定位
	@JavascriptInterface
	public void stop(){
		locat.stop();
	}
	@JavascriptInterface
	public void start(){
		locat.start();
	}
	//返回所以信息
	@JavascriptInterface
	public String getInfo(){
		//locat.start();
		
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
		}
		Log.i("TMP_URL", locat.locations.getAddr());
		return locat.locations.getAddr();
	}
	
	//返回地址
	@JavascriptInterface
	public String getAdds(){
		//locat.start();
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
			return locat.locations.getAddr();
		}else{			
			return "false";
		}
	}
	
	//返回坐标
	@JavascriptInterface
	public String getCoordinate(){
		//locat.start();
		if(locat.locations.getAddr()!="" && locat.locations.getAddr()!=null){
			//locat.stop();
			return locat.locations.getAddr();
			//locat.locations.getLontitude()+","+locat.locations.getLatitude();
		}else{			
			return "false";
		}
		
	}
	@JavascriptInterface
	public void Toast(String txt){
		locat.Toast(txt);
	}
}

