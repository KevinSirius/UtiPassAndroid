package com.ustb.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptinterface {

	private Context mContext;

	/** Instantiate the interface and set the context */
	public JavaScriptinterface(Context c) {
		mContext = c;
	}

	/** Show a toast from the web page */
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
		
	}
	
	@JavascriptInterface 
	public String toJson(){
		
		return "{name:123,pwd:abc}";
	}
}

