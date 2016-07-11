package com.ericssonlabs;

/**
 * ���԰ٶȶ�λSDK
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
		
		// �Զ���actionbar�Ĳ���
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

		/**������������������������������������������������������������������������������������������������������������������������������������
		 * �����AK��Ӧ��ǩ�������󶨣����ʹ�����Լ��Ĺ�������Ҫ�滻Ϊ�Լ������Key
		 * ������������������������������������������������������������������������������������������������������������������������������������
		 */
//		mLocationClient.setAK("697f50541f8d4779124896681cb6584d");	 
//		mLocationClient.setAK("z4nqERrqxnhNzT5VOGNVRt80");
		mLocationClient.registerLocationListener( myListener );

		setLocationOption();//�趨��λ����
		
		mLocationClient.start();//��ʼ��λ
		
		// ���¶�λ
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

	//������ز���
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);//��ֹ���û��涨λ
		option.setPoiNumber(5);    //��෵��POI����   
		option.setPoiDistance(1000); //poi��ѯ����        
		option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ        
		mLocationClient.setLocOption(option);
		
	} 

	@Override
	public void onDestroy() {
		mLocationClient.stop();//ֹͣ��λ
		mTv = null;
		super.onDestroy();
	}

    /**
     * �ٶȻ�վ��λ���󷵻���
     */
//  61 �� GPS��λ���
//  62 �� ɨ�����϶�λ����ʧ�ܡ���ʱ��λ�����Ч��
//  63 �� �����쳣��û�гɹ���������������󡣴�ʱ��λ�����Ч��
//  65 �� ��λ����Ľ����
//  66 �� ���߶�λ�����ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ��
//  67 �� ���߶�λʧ�ܡ�ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ��
//  68 �� ��������ʧ��ʱ�����ұ������߶�λʱ��Ӧ�ķ��ؽ��
//  161�� ��ʾ���綨λ���
//  162~167�� ����˶�λʧ�ܡ�

	/**
	 * �����������и���λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		//����λ����Ϣ
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("��ǰʱ�� : ");
			sb.append(location.getTime());
			//sb.append("\nerror code : ");
			//sb.append(location.getLocType());
			sb.append("\nά�� : ");
			sb.append(location.getLatitude());
			sb.append("\n���� : ");
			sb.append(location.getLongitude());
			//sb.append("\nradius : ");
			//sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\n�ٶ� : ");
				sb.append(location.getSpeed());
				sb.append("\n���� : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				/**
				 * ��ʽ����ʾ��ַ��Ϣ
				 */
				sb.append("\n��ǰ��ַ : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nSDK�汾 : ");
			sb.append(mLocationClient.getVersion());
			//sb.append("\nisCellChangeFlag : ");
			//sb.append(location.isCellChangeFlag());
			mTv.setText(sb.toString());
			Log.i(TAG, sb.toString());
		}
		//����POI��Ϣ�������Ҳ���ҪPOI��������û��������
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}


}