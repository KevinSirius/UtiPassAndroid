package com.ustb.fx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;


import com.ericssonlabs.R;
import com.ustb.utils.Admin;
import com.ustb.utils.Data;
import com.ustb.utils.DeviceId;

import com.ustb.utils.Person;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMine extends Fragment {
        private TextView tvid,tvclient,tvfrom,tvto,tvuser,tvdesc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 //View view=inflater.inflate(R.layout.fragment_mine, container, false);

		return inflater.inflate(R.layout.fragment_mine, container, false);
	}

	  /**当Activity的onCreate()方法执行完之后，调用这个回调方法。*/
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    //获取 fragment_mine相应组件
	    tvid=(TextView)getView().findViewById(R.id.tv_id);
	    tvclient=(TextView)getView().findViewById(R.id.tv_clientNo);
	    tvfrom=(TextView)getView().findViewById(R.id.tv_validfrom);
	    tvto=(TextView)getView().findViewById(R.id.tv_validto);
	    tvuser=(TextView)getView().findViewById(R.id.tv_userid);
	    tvdesc=(TextView)getView().findViewById(R.id.tv_description);

	    //获取设备唯一标识  	
	    DeviceId deviceid = new DeviceId(getActivity());
		String number_str = deviceid.getDeviceId();
		String s = "http://t.tipass.com/index.php/License/viewlicenseapi?c="+number_str;
		Admin admin = new Admin(); 
	    Boolean state = admin.isNetworkAvailable(getActivity());
	    if(state==true){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		StringBuilder json = new StringBuilder();   
		    
		    //连接URL，从指定网址获取Json数据
		    URL oracle=null;
			try {
				oracle = new URL(s);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    URLConnection yc=null;
			try {
				yc = oracle.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    BufferedReader in=null;
			try {
				in = new BufferedReader(new InputStreamReader(yc.getInputStream(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    String inputLine = null; 
		    try {
				while ( (inputLine = in.readLine()) != null){ 
				  json.append(inputLine); 
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    String Strjson=json.toString();
		    System.out.println("原始数据:");
		    System.out.println(Strjson.toString()); 
		    //tvclient.setText(Strjson);
            parseJson(Strjson);
	    }else{
	    	String snull=" ";
			tvid.setText(snull);
            tvclient.setText(snull);
            tvfrom.setText(snull);
            tvto.setText(snull);
            tvuser.setText(snull);
            tvdesc.setText(snull);
            Toast.makeText(getActivity(), "未连接网络，请联网重试！", Toast.LENGTH_SHORT).show();
	    }

	  }

		// 普通Json数据解析 
		private void parseJson(String strResult) { 
			try { 
				
				JSONObject jsonObject = new JSONObject(strResult);
				int msg_code = jsonObject.getInt("msg_code");
				String msg = jsonObject.getString("msg");    
				JSONObject dataJSON = jsonObject.getJSONObject("data");  
			    int id= dataJSON.getInt("ID");  
			    String idstr=""+id;
				String clientno = dataJSON.getString("ClientNo");  
				String validfrom = dataJSON.getString("ValidFrom"); 
				String validto = dataJSON.getString("Validto"); 
				String userid = dataJSON.getString("UserID");
				String description = dataJSON.getString("Description");
				Data data = new Data(id, clientno, validfrom, validto, userid, description);  
				Person person = new Person(msg_code, msg, data);  
				System.out.println(person);  
				tvid.setText(idstr);
	            tvclient.setText(clientno);
	            tvfrom.setText(validfrom);
	            tvto.setText(validto);
	            tvuser.setText(userid);
	            tvdesc.setText(description);
			} catch (JSONException e) { 
				System.out.println("Json parse error"); 
				e.printStackTrace(); 
			} 
		}

	  
	  /**当跟Fragment关联的视图层正在被删除时，调用这个回调方法。*/
	  @Override
	  public void onDestroyView() {
	    super.onDestroyView();
	  }
	  
	  /**当从Activity中解除Fragment的关联时，调用这个回调方法。*/
	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

	  /*================================================================*/
	  /**用户将要离开Fragment时,系统调用这个方法作为第一个指示(然而它不总是意味着Fragment将被销毁。) 
	  在当前用户会话结束之前,通常应当在这里提交任何应该持久化的变化(因为用户有可能不会返回)。*/
	  @Override
	  public void onPause() {
	    super.onPause();
	  }



}

