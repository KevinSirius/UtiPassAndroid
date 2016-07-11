package com.ustb.utils;

import android.app.Application;  

public class IpData extends Application{  
    private String selected;  
      
    public String getB(){  
        return this.selected;  
    }  
    public void setB(String c){  
        this.selected= c;  
    }  
    @Override  
    public void onCreate(){  
    	selected = "t";  
        super.onCreate();  
    }  
}  