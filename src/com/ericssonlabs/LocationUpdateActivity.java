package com.ericssonlabs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationUpdateActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// 调用父类方法
        setContentView(R.layout.location);// 应用布局文件
        
		// 自定义actionbar的布局
        setActionBarLayout( R.layout.gps_port_layout );
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
        
        StringBuilder sb = new StringBuilder();// 使用StringBuilder保存数据
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);// 获得位置服务
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 2, new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
            }
        });
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            sb.append("纬度：" + location.getLatitude() + "\n");
            sb.append("经度：" + location.getLongitude());
        } else {
            sb.append("当前GPS位置为空< ( _ _ ) >");
        }
        TextView text = (TextView) findViewById(R.id.gps_location);// 获得文本框控件
        text.setText(sb.toString());// 显示位置源列表
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
}