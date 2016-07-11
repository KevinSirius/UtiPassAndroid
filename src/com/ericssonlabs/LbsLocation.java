package com.ericssonlabs;

/**
 * 尝试百度定位SDK
 * @author harvic
 * @date 2013-12-28
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LbsLocation extends Activity {
	private TextView mTv = null;
	public LocationClient mLocationClient = null; 
	public MyLocationListenner myListener = new MyLocationListenner();
	public Button ReLBSButton=null;
	public static String TAG = "msg";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lbs_layout);
		
		// 自定义actionbar的布局
		setActionBarLayout(R.layout.lbs_port_layout );
		ImageView imgBtn=(ImageView)findViewById(R.id.iv_back);
		imgBtn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					finish(); 	
				}
				return true;
			}
		});
		
		mTv = (TextView)findViewById(R.id.textview);
		ReLBSButton=(Button)findViewById(R.id.ReLBS_button);
		
		mLocationClient = new LocationClient( this );

		/**——————————————————————————————————————————————————————————————————
		 * 这里的AK和应用签名包名绑定，如果使用在自己的工程中需要替换为自己申请的Key
		 * ——————————————————————————————————————————————————————————————————
		 */
//		mLocationClient.setAK("697f50541f8d4779124896681cb6584d");	 
//		mLocationClient.setAK("z4nqERrqxnhNzT5VOGNVRt80");
		mLocationClient.registerLocationListener( myListener );

		setLocationOption();//设定定位参数
		
		mLocationClient.start();//开始定位
		
		// 重新定位
		ReLBSButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();
				else
					Log.d("msg", "locClient is null or not started");
			}
		});
		
	} 
	
	/**
	 * 设置ActionBar的布局
	 * @param layoutId 布局Id
	 * 
	 * */
	public void setActionBarLayout( int layoutId ){
		ActionBar actionBar = getActionBar( );
		actionBar.setTitle("");
		if( null != actionBar ){
			actionBar.setDisplayShowHomeEnabled( false );
			actionBar.setDisplayShowCustomEnabled(true);

			LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v,layout);
		}
	}

	//设置相关参数
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		option.setPoiNumber(5);    //最多返回POI个数   
		option.setPoiDistance(1000); //poi查询距离        
		option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息        
		mLocationClient.setLocOption(option);
		
	} 

	@Override
	public void onDestroy() {
		mLocationClient.stop();//停止定位
		mTv = null;
		super.onDestroy();
	}

    /**
     * 百度基站定位错误返回码
     */
//  61 ： GPS定位结果
//  62 ： 扫描整合定位依据失败。此时定位结果无效。
//  63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
//  65 ： 定位缓存的结果。
//  66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
//  67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
//  68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
//  161： 表示网络定位结果
//  162~167： 服务端定位失败。

	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		//接收位置信息
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("当前时间 : ");
			sb.append(location.getTime());
			//sb.append("\nerror code : ");
			//sb.append(location.getLocType());
			sb.append("\n维度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			//sb.append("\nradius : ");
			//sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\n速度 : ");
				sb.append(location.getSpeed());
				sb.append("\n卫星 : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				/**
				 * 格式化显示地址信息
				 */
				sb.append("\n当前地址 : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nSDK版本 : ");
			sb.append(mLocationClient.getVersion());
			//sb.append("\nisCellChangeFlag : ");
			//sb.append(location.isCellChangeFlag());
			mTv.setText(sb.toString());
			Log.i(TAG, sb.toString());
		}
		//接收POI信息函数，我不需要POI，所以我没有做处理
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}


}