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

	  /**��Activity��onCreate()����ִ����֮�󣬵�������ص�������*/
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    //��ȡ fragment_mine��Ӧ���
	    tvid=(TextView)getView().findViewById(R.id.tv_id);
	    tvclient=(TextView)getView().findViewById(R.id.tv_clientNo);
	    tvfrom=(TextView)getView().findViewById(R.id.tv_validfrom);
	    tvto=(TextView)getView().findViewById(R.id.tv_validto);
	    tvuser=(TextView)getView().findViewById(R.id.tv_userid);
	    tvdesc=(TextView)getView().findViewById(R.id.tv_description);

	    //��ȡ�豸Ψһ��ʶ  	
	    DeviceId deviceid = new DeviceId(getActivity());
		String number_str = deviceid.getDeviceId();
		String s = "http://t.tipass.com/index.php/License/viewlicenseapi?c="+number_str;
		Admin admin = new Admin(); 
	    Boolean state = admin.isNetworkAvailable(getActivity());
	    if(state==true){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		StringBuilder json = new StringBuilder();   
		    
		    //����URL����ָ����ַ��ȡJson����
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
		    System.out.println("ԭʼ����:");
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
            Toast.makeText(getActivity(), "δ�������磬���������ԣ�", Toast.LENGTH_SHORT).show();
	    }

	  }

		// ��ͨJson���ݽ��� 
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

	  
	  /**����Fragment��������ͼ�����ڱ�ɾ��ʱ����������ص�������*/
	  @Override
	  public void onDestroyView() {
	    super.onDestroyView();
	  }
	  
	  /**����Activity�н��Fragment�Ĺ���ʱ����������ص�������*/
	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	  }

	  /*================================================================*/
	  /**�û���Ҫ�뿪Fragmentʱ,ϵͳ�������������Ϊ��һ��ָʾ(Ȼ������������ζ��Fragment�������١�) 
	  �ڵ�ǰ�û��Ự����֮ǰ,ͨ��Ӧ���������ύ�κ�Ӧ�ó־û��ı仯(��Ϊ�û��п��ܲ��᷵��)��*/
	  @Override
	  public void onPause() {
	    super.onPause();
	  }



}

