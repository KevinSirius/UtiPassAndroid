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
        super.onCreate(savedInstanceState);// ���ø��෽��
        setContentView(R.layout.location);// Ӧ�ò����ļ�
        
		// �Զ���actionbar�Ĳ���
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
        
        StringBuilder sb = new StringBuilder();// ʹ��StringBuilder��������
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);// ���λ�÷���
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
            sb.append("γ�ȣ�" + location.getLatitude() + "\n");
            sb.append("���ȣ�" + location.getLongitude());
        } else {
            sb.append("��ǰGPSλ��Ϊ��< ( _ _ ) >");
        }
        TextView text = (TextView) findViewById(R.id.gps_location);// ����ı���ؼ�
        text.setText(sb.toString());// ��ʾλ��Դ�б�
    }

	/**
	 * ����ActionBar�Ĳ���
	 * @param layoutId ����Id
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