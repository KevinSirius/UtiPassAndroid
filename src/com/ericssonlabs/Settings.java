package com.ericssonlabs;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Settings extends Activity {
	//define an array[] to save "cities"
	private static final String[] countriesStr = {"t",
		"pa"};
	private ArrayAdapter<String> adapter;
	private Spinner spinner = null;
	public String selected;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		// 自定义actionbar的布局
		setActionBarLayout( R.layout.settings_port_layout );
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
		
		RelativeLayout gpsTv=(RelativeLayout)findViewById(R.id.re_youxi);
		gpsTv.setClickable(true);
		gpsTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//鎵撳紑鎵弿鐣岄潰鎵弿鏉″舰鐮佹垨浜岀淮鐮�
				Intent locationIntent = new Intent(Settings.this,LocationUpdateActivity.class);
				startActivity(locationIntent);
				
			}
		});
		
		RelativeLayout versionTv=(RelativeLayout)findViewById(R.id.re_gouwu);
		versionTv.setClickable(true);
		versionTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdateManager manager = new UpdateManager(Settings.this);
				// 妫�鏌ヨ蒋浠舵洿鏂�
				manager.checkUpdate();
			}
		});
		
		RelativeLayout lbsTv=(RelativeLayout)findViewById(R.id.re_piaoliuping);
		lbsTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent lbsLocationIntent = new Intent(Settings.this,LbsLocation.class);
				startActivity(lbsLocationIntent);
			}
		});
		
		RelativeLayout ipTv=(RelativeLayout)findViewById(R.id.re_ip);
		spinner = (Spinner) findViewById(R.id.spinner_City);
		List<String> list = new ArrayList<String>();
		list.add("pa");
		list.add("t");
		//调用ArrayAdapter的构造函数来创建ArrayAdapter对象
		//第一个参数是指上下文对象
		//第二个参数指定了下拉菜单当中每一个条目的样式
		//第三个参数指定了TextView控件的ID
		//第四个参数为整个列表提供数据
		ArrayAdapter adapter = new ArrayAdapter(this,R.layout.item,R.id.textViewId,list);
		//通过createFromResource方法创建一个ArrayAdapter对象
		//第一个参数是指上下文对象
		//第二参数引用了在strings.xml文件当中定义的String数组
		//第三个参数是用来指定Spinner的样式，是一个布局文件ID，该布局文件由Android系统提供，也可替换为自己定义的布局文件
		/*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.planets_array,
				android.R.layout.simple_spinner_item);
*/		//设置Spinner当中每个条目的样式，同样是引用一个Android系统提供的布局文件
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinner.setAdapter(adapter);
		spinner.setPrompt("测试");
		//为spinner对象绑定监听器
		spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());

	}

	//这个监听器主要用来监听用户选择列表的动作
	class SpinnerOnSelectedListener implements OnItemSelectedListener{
		
		//当用户选定了一个条目时，就会调用该方法
		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int position,
				long id) {
		    selected = adapterView.getItemAtPosition(position).toString();
			System.out.println(selected);
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
			System.out.println("nothingSelected");
		}
		
	}
/*	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(this, BarCodeTestActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.finish();
			return true;

		default:
			break;
		}
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}*/
	
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
	
/*	*//**
	 * 实现onClick方法，在这里面监听actionbar中按钮的点击事件 
	 * 
	 * *//*
	public void onClick( View v ){
		if( v.getId( )==R.id.iv_back ){
			
    		Intent intent = new Intent(this,BarCodeTestActivity.class);
    		//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    		startActivity(intent);
    		//finish(); 		
		}
	}*/
}
    

