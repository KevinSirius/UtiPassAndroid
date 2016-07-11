package com.ustb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

public class Admin {
    //private int msg_code, id;
	private String idstr, clientno, validfrom, validto, userid, description;
	public String getJson(String s){
		   StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		   StrictMode.setThreadPolicy(policy);
		   StringBuilder json = new StringBuilder();   
		    
		    
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
		    return Strjson;
	}
	//获取ID
	public String parseJsonId(String strResult){
		try { 
			
			JSONObject jsonObject = new JSONObject(strResult);   
			JSONObject dataJSON = jsonObject.getJSONObject("data");  
		    int id= dataJSON.getInt("ID");  
		    idstr=""+id;
		} catch (JSONException e) { 
			System.out.println("Json parse error"); 
			e.printStackTrace(); 
		} 
		return idstr;
	}
	//获取clientno
	public String parseJsonClientno(String strResult){
		try { 
			
			JSONObject jsonObject = new JSONObject(strResult);   
			JSONObject dataJSON = jsonObject.getJSONObject("data");  
			clientno = dataJSON.getString("ClientNo");  
		} catch (JSONException e) { 
			System.out.println("Json parse error"); 
			e.printStackTrace(); 
		} 
		return clientno;
	}
	
	//获取validfrom
	public String parseJsonValidfrom(String strResult){
		try { 
			
			JSONObject jsonObject = new JSONObject(strResult);  
			JSONObject dataJSON = jsonObject.getJSONObject("data");  
			validfrom = dataJSON.getString("ValidFrom"); 
		} catch (JSONException e) { 
			System.out.println("Json parse error"); 
			e.printStackTrace(); 
		} 
		return validfrom;
	}
	
	//获取validto
	public String parseJsonValidto(String strResult){
		try { 
			
			JSONObject jsonObject = new JSONObject(strResult);   
			JSONObject dataJSON = jsonObject.getJSONObject("data");   
			validto = dataJSON.getString("Validto"); 

		} catch (JSONException e) { 
			System.out.println("Json parse error"); 
			e.printStackTrace(); 
		} 
		return validto;
	}
	
	//
	
	public String parseJsonUserid(String strResult){
		try {

			JSONObject jsonObject = new JSONObject(strResult);
			JSONObject dataJSON = jsonObject.getJSONObject("data");
			userid = dataJSON.getString("UserID");

		} catch (JSONException e) {
			System.out.println("Json parse error");
			e.printStackTrace();
		}
		return userid;
	}
	
	//获取description
	public String parseJsonDescription(String strResult){
		try {

			JSONObject jsonObject = new JSONObject(strResult);
			JSONObject dataJSON = jsonObject.getJSONObject("data");
			description = dataJSON.getString("Description");
		} catch (JSONException e) {
			System.out.println("Json parse error");
			e.printStackTrace();
		}
		return description;
	}
	
   //检测网络是否可用
	public boolean isNetworkAvailable(Context context) {       
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			// return (info!=null && info.isConnected());//
			if (info != null) {
				return info.isConnected();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
