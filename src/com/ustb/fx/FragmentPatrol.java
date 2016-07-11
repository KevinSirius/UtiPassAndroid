package com.ustb.fx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.device.ScanDevice;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ericssonlabs.NFCActivity;
import com.ericssonlabs.R;

import com.ustb.pullrefresh.ui.PullToRefreshBase;
import com.ustb.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.ustb.pullrefresh.ui.PullToRefreshWebView;
import com.ustb.util.baiduGPS.GpsHelp;
import com.ustb.utils.Admin;
import com.ustb.utils.DeviceId;
import com.ustb.utils.ImageTools;
import com.ustb.utils.IpData;
import com.ustb.utils.Util;
import com.zxing.activity.CaptureActivity;

import android.device.ScanDevice;

/**
 * 联系人列表页
 * 
 */
@SuppressLint("InflateParams")
public class FragmentPatrol extends Fragment {
	//CARIBE
/*	ScanDevice sm;
	 private final static String SCAN_ACTION = "scan.rcv.message";
	 private String barcodeStr;
	 private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

	        @Override
	        public void onReceive(Context context, Intent intent) {
	            // TODO Auto-generated method stub

	            byte[] barocode = intent.getByteArrayExtra("barocode");
	            int barocodelen = intent.getIntExtra("length", 0);
	            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
	            android.util.Log.i("debug", "----codetype--" + temp);
	            barcodeStr = new String(barocode, 0, barocodelen);
	            showScanResult.append("广播输出：");
	            showScanResult.append(barcodeStr);
	            showScanResult.append("\n");
	     //       showScanResult.setText(barcodeStr);
				if (state == true) {
					String jsonstr = admin.getJson(minestr);
					String validfrom = admin.parseJsonValidfrom(jsonstr);
					String validto = admin.parseJsonValidto(jsonstr);

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dfstr = df.format(new Date());
					if (validfrom == null || validfrom.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto == null || validto.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto.compareTo(dfstr) < 0) {
						Toast.makeText(getActivity(), "有效期已过期！",
								Toast.LENGTH_SHORT).show();
					} else {
					    mWebView.loadUrl(barcodeStr+s);
					}
				} else {
					Toast.makeText(getActivity(), "未连接网络，请联网重试！",
							Toast.LENGTH_SHORT).show();
				}
	            sm.stopScan();
	        }

	    };*/
	 //
	private static String s = null;
	private String patrolresult;
	private String scanResult;
    private static final int CAPTURE_RESULT_OK = 10;
    private int NFC_RESULT_OK=7;
	/** Called when the activity is first created. */
	private DeviceId deviceid;
	private Boolean state;
	private String number_str, minestr;
	private Admin admin;
	private int flag = 0;
	private RelativeLayout scangroup,scangroupnfc;
	private WebView mWebView;
	private PullToRefreshWebView mPullWebView;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private String TMP_URL =
	 "http://laodongjianguan.chanlytech.com:8088/laodongjianguan/index.php/daohang/daohang/f";
	private ValueCallback<Uri> mUploadMessage;// 琛ㄥ崟鐨勬暟鎹俊鎭�
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private final static int FILECHOOSER_RESULTCODE = 123;// 琛ㄥ崟鐨勭粨鏋滃洖璋�</span>
	private Uri imageUri,imageUri1;
	
	private static final int SCALE = 5;//照片缩小比例
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// view=inflater.inflate(R.layout.main, container, false);
		return inflater.inflate(R.layout.main, container, false);
	}

	/** 当Activity的onCreate()方法执行完之后，调用这个回调方法。 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//sm = new ScanDevice();
		//sm.openScan();
		
		admin = new Admin();
		deviceid = new DeviceId(getActivity());
		number_str = deviceid.getDeviceId();
		//final IpData app = (IpData)getApplication(); 
		minestr = "http://"+"t"+".tipass.com/index.php/License/viewlicenseapi?c="
				+ number_str;
		s = "&type=product&c=" + number_str;
		// String minestr
		// ="http://182.92.220.78/index.php/Product/viewbyqr?"+number_str;
		state = admin.isNetworkAvailable(getActivity());
        mPullWebView = (PullToRefreshWebView) getView().findViewById(R.id.webview_patrol);//new PullToRefreshWebView(this);
        mPullWebView.setOnRefreshListener(new OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
            	
        		if (state == true) {
        			String jsonstr = admin.getJson(minestr);
        			String validfrom = admin.parseJsonValidfrom(jsonstr);
        			String validto = admin.parseJsonValidto(jsonstr);
        			// 获取系统当前时间
        			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        			String dfstr = df.format(new Date());
        			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        			if (validfrom == null || validfrom.length() <= 5) {
        				mWebView.loadUrl("file:///android_asset/index.html");
        			} else if (validto == null || validto.length() <= 5) {
        				mWebView.loadUrl("file:///android_asset/index.html");
        			} else if (validto.compareTo(dfstr) < 0) {
        				mWebView.loadUrl("file:///android_asset/index.html");
        			} else {
        				mWebView.loadUrl(patrolresult);
        				setLastUpdateTime();
        				mWebView.refreshDrawableState();
        			}
        		} else {
        			mWebView.loadUrl("file:///android_asset/index.html");
        			mWebView.refreshDrawableState();
        			Toast.makeText(getActivity(), "未连接网络，请联网重试！", Toast.LENGTH_SHORT)
        					.show();
        		}
        		
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
            }
        });
        //clearCookies(getActivity());
        mWebView = mPullWebView.getRefreshableView();
        mWebView.setDownloadListener(new DownloadListener() {  
        	  
            @Override  
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {  
                // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载  
  
                Uri uri = Uri.parse(url);  
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
                startActivity(intent);  
            }  
        });
		mWebView.getSettings().setJavaScriptEnabled(true);
		WebSettings settings = mWebView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setSupportZoom(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new Util(getActivity()),"util");
		mWebView.addJavascriptInterface(new GpsHelp(getActivity(),mWebView),"gps");
		mWebView.setWebViewClient(new WebViewClient(){
	        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，  
	        // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。 
	        public boolean shouldOverviewUrlLoading(WebView view, String url) {  
	            //Log.i("shouldOverviewUrlLoading");  
	            view.loadUrl(url);  
	            return true;  
	        }
		}); // 处理各种通知和请求事件，如果不使用该句代码，将使用内置浏览器访问网页
		//mWebView.setWebChromeClient(new WebChromeClient());	//处理JavaScript对话框
		//settings.setJavaScriptCanOpenWindowsAutomatically(true);
	      //不使用缓存：
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onShowFileChooser(WebView webView,
					ValueCallback<Uri[]> filePathCallback,
					FileChooserParams fileChooserParams) {
				mUploadCallbackAboveL=filePathCallback;
				Log.i(TMP_URL, "onShowFileChooser");
				take();
				return true;
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage=uploadMsg;
				Log.i(TMP_URL, "ValueCallback<Uri> uploadMsg");
				take();				
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
				mUploadMessage=uploadMsg;
				Log.i(TMP_URL, "ValueCallback<Uri> uploadMsg,String acceptType");
				take();
				
			}
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
				mUploadMessage=uploadMsg;
				Log.i(TMP_URL, "ValueCallback<Uri> uploadMsg,String acceptType, String capture");
				take();
	
				}
			
			//扩展支持alert事件
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示信息").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
                //builder.setIcon(R.drawable.yunwei_icon);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }
		});
		scangroup = (RelativeLayout) getView().findViewById(R.id.re_patrolscan);
		scangroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == true) {
					String jsonstr = admin.getJson(minestr);
					String validfrom = admin.parseJsonValidfrom(jsonstr);
					String validto = admin.parseJsonValidto(jsonstr);

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dfstr = df.format(new Date());
					if (validfrom == null || validfrom.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto == null || validto.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto.compareTo(dfstr) < 0) {
						Toast.makeText(getActivity(), "有效期已过期！",
								Toast.LENGTH_SHORT).show();
					} else {
						Intent openCameraIntent = new Intent(getActivity(),
								CaptureActivity.class);
						startActivityForResult(openCameraIntent, 0);
						flag = 0;
					}
				} else {
					Toast.makeText(getActivity(), "未连接网络，请联网重试！",
							Toast.LENGTH_SHORT).show();
				}

			}

		});
		
		scangroupnfc = (RelativeLayout) getView().findViewById(R.id.re_patrolnfc);
		scangroupnfc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (state == true) {
					String jsonstr = admin.getJson(minestr);
					String validfrom = admin.parseJsonValidfrom(jsonstr);
					String validto = admin.parseJsonValidto(jsonstr);

					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dfstr = df.format(new Date());
					if (validfrom == null || validfrom.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto == null || validto.length() <= 5) {
						Toast.makeText(getActivity(), "该客户端没有授权！",
								Toast.LENGTH_SHORT).show();
					} else if (validto.compareTo(dfstr) < 0) {
						Toast.makeText(getActivity(), "有效期已过期！",
								Toast.LENGTH_SHORT).show();
					} else {
						Intent openCameraIntent1 = new Intent(getActivity(),
								NFCActivity.class);
						startActivityForResult(openCameraIntent1, 0);
						flag = 0;
					}
				} else {
					Toast.makeText(getActivity(), "未连接网络，请联网重试！",
							Toast.LENGTH_SHORT).show();
				}

			}

		});
		
	}
	
	@SuppressWarnings({ "null", "resource", "unused" })
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		// 澶勭悊鎵弿缁撴灉锛堝湪鐣岄潰涓婃樉绀猴級
		if (resultCode == CAPTURE_RESULT_OK) {
			Log.i(TMP_URL, " CAPTURE_RESULT_OK");
			try {
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("result");
				patrolresult = scanResult + s;
			    patrolopenBrowser(patrolresult);
   				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
      //  String url = "http://t.tipass.com/index.php/Product/viewbynfc?nfc="+cardID+"&type=product&c=4d99f9640198e1c9";
		
		if (resultCode == NFC_RESULT_OK) {
			Log.i(TMP_URL, " CAPTURE_RESULT_OK");
			try {
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("result");
				patrolresult = "http://t.tipass.com/index.php/Product/viewbynfc?nfc="+scanResult +s;
			    patrolopenBrowser(patrolresult);
   				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
		
        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
            	Log.e("result",result+"");
				File imageStorageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"MyApp");
				Bitmap smallpic=getSmallBitmap(imageUri.getPath());
				if (null==smallpic||imageUri.getPath()==null){
					File deletefile = new File(imageUri.getPath());
					deletefile.delete();
					}
				
				if (!imageStorageDir.exists()) {
					imageStorageDir.mkdirs();
				}		
				File file11 = new File(imageStorageDir + File.separator + "IMG_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg");
				FileOutputStream out;
            	if(result==null){
//            		mUploadMessage.onReceiveValue(imageUri);
					try {
						out = new FileOutputStream(file11);
						if(null==smallpic){
							out.close();
							}else{
						smallpic.compress(Bitmap.CompressFormat.JPEG, 90, out);
						Log.e("result",out+"");
						   out.flush();
						   out.close();}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					File deletefile = new File(imageUri.getPath());
					deletefile.delete();
					imageUri1 = Uri.fromFile(file11);
            		mUploadMessage.onReceiveValue(imageUri1);
                    mUploadMessage = null;	
                   
                    Log.e("imageUri",imageUri+"");
            	}else {
            		mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;	
				}
            		
               
            }
        }
		
/*		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (resultCode == Activity.RESULT_OK) {
			Log.i(TMP_URL, "FILECHOOSER_RESULTCODE");
			 if (null == mUploadMessage && null == mUploadCallbackAboveL)
				 {return;}
			Uri result = data == null || resultCode !=Activity.RESULT_OK ? null
					: data.getData();
			if (mUploadCallbackAboveL != null) {
				onActivityResultAboveL(requestCode, resultCode, data);
			} else if (mUploadMessage != null) {
				Log.e("result", result + "");
				if (result == null) {   
					File imageStorageDir = new File(
							Environment
									.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
							"MyApp");
					Bitmap smallpic=getSmallBitmap(imageUri.getPath());
					if(smallpic==null){
						return;
					}
					if (!imageStorageDir.exists()) {
						imageStorageDir.mkdirs();
					}		
					File file = new File(imageStorageDir + File.separator + "IMG_"
							+ String.valueOf(System.currentTimeMillis()) + ".jpg");
					FileOutputStream out;
					try {
						out = new FileOutputStream(file);
						smallpic.compress(Bitmap.CompressFormat.JPEG, 90, out);
						   out.flush();
						   out.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					File deletefile = new File(imageUri.getPath());
					deletefile.delete();
					imageUri1 = Uri.fromFile(file);
					mUploadMessage.onReceiveValue(imageUri);
					mUploadMessage = null;
					Log.e("imageUri", imageUri1 + "");
				} else {
					//Log.i(TMP_URL, "mUploadMessage.onReceiveValue(result);");
					mUploadMessage.onReceiveValue(result);
					mUploadMessage = null;
				}
			}
			Log.i(TMP_URL, "Activity");
		}
		Log.i(TMP_URL, "Activity欧克");}*/
		
	}
	
    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        mPullWebView.setLastUpdatedLabel(text);
    }
    
    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        
        return mDateFormat.format(new Date(time));
    }

	private void patrolopenBrowser(final String patrolResult) {
		// TODO Auto-generated method stub
		mWebView.loadUrl(patrolResult);
		Toast.makeText(getActivity(), "正在加载中，请稍后",
				Toast.LENGTH_SHORT).show();
	}

		@SuppressWarnings("null")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void onActivityResultAboveL(int requestCode, int resultCode,
			Intent data) {
		if (requestCode != FILECHOOSER_RESULTCODE
				|| mUploadCallbackAboveL == null) {
			return;
		}

		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK) {
			if (data == null) {
				results = new Uri[] { imageUri };
				File imageStorageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"MyApp");
				Bitmap smallpic=getSmallBitmap(imageUri.getPath());
				if (!imageStorageDir.exists()) {
					imageStorageDir.mkdirs();
				}		
				File file = new File(imageStorageDir + File.separator + "IMG_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg");
				FileOutputStream out;
				try {
					out = new FileOutputStream(file);
/*					if(smallpic==null){
						return;
					}*/
					smallpic.compress(Bitmap.CompressFormat.JPEG, 90, out);
					   out.flush();
					   out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageUri1 = Uri.fromFile(file);
				File deletefile = new File(imageUri.getPath());
				deletefile.delete();
				results = new Uri[] { imageUri1 };
			} else {
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}

				if (dataString != null)
					results = new Uri[] { Uri.parse(dataString) };
				
				
			}
		}
		if (results != null) {
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		} else {
			results = new Uri[] { imageUri };
			mUploadCallbackAboveL.onReceiveValue(results);
			mUploadCallbackAboveL = null;
		}

		return;
	}


	private void take() {
		Log.i(TMP_URL, "take()");
		File imageStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyApp");
		// Create the storage directory if it does not exist
		if (!imageStorageDir.exists()) {
			imageStorageDir.mkdirs();
		}
		File file = new File(imageStorageDir + File.separator + "IMG_"
				+ "zx-temp" + ".jpg");
		imageUri = Uri.fromFile(file);
		
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getActivity().getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
				captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent i = new Intent(captureIntent);
			i.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));
			i.setPackage(packageName);
			i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			cameraIntents.add(i);
			Log.i(TMP_URL, "listCam");
		}
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
				cameraIntents.toArray(new Parcelable[] {}));
		this.startActivityForResult(chooserIntent,
				FILECHOOSER_RESULTCODE);
		Log.i(TMP_URL, "Image Chooser");
	}

	/** 当跟Fragment关联的视图层正在被删除时，调用这个回调方法。 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/** 当从Activity中解除Fragment的关联时，调用这个回调方法。 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/* ================================================================ */
	/**
	 * 用户将要离开Fragment时,系统调用这个方法作为第一个指示(然而它不总是意味着Fragment将被销毁。)
	 * 在当前用户会话结束之前,通常应当在这里提交任何应该持久化的变化(因为用户有可能不会返回)。
	 */
	@Override
	public void onPause() {
		super.onPause();
/*        if(sm != null) {
        	sm.stopScan();
        }
        getActivity().unregisterReceiver(mScanReceiver);*/
	}
	@Override
	public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
/*        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        getActivity().registerReceiver(mScanReceiver, filter);*/
    }
	
	 public static void clearCookies(Context context) {
	        // Edge case: an illegal state exception is thrown if an instance of
	        // CookieSyncManager has not be created.  CookieSyncManager is normally
	        // created by a WebKit view, but this might happen if you start the
	        // app, restore saved state, and click logout before running a UI
	        // dialog in a WebView -- in which case the app crashes
	        @SuppressWarnings("unused")
	        CookieSyncManager cookieSyncMngr =
	            CookieSyncManager.createInstance(context);
	        CookieManager cookieManager = CookieManager.getInstance();
	        cookieManager.removeAllCookie();
	    }
/*	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	         if((keyCode==KeyEvent.KEYCODE_BACK)&&mWebView.canGoBack()){  
	        	 mWebView.goBack();  
	         return true;  
	        }  
	        return false;  
	    }*/ 
	    
		/**
		 * Resize the bitmap 压缩图片
		 * 
		 * @param bitmap
		 * @param width
		 * @param height
		 * @return
		 */
		public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) width / w);
			float scaleHeight = ((float) height / h);
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
			return newbmp;
		}
		
		//计算图片的缩放值
		public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    int inSampleSize = 1;

		    if (height > reqHeight || width > reqWidth) {
		             final int heightRatio = Math.round((float) height/ (float) reqHeight);
		             final int widthRatio = Math.round((float) width / (float) reqWidth);
		             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		    }
		        return inSampleSize;
		}
		
		// 根据路径获得图片并压缩，返回bitmap用于显示
		public static Bitmap getSmallBitmap(String filePath) {
		        final BitmapFactory.Options options = new BitmapFactory.Options();
		        options.inJustDecodeBounds = false;
		        BitmapFactory.decodeFile(filePath, options);
 
		        // Calculate inSampleSize
		       options.inSampleSize = calculateInSampleSize(options, 480, 800);

		        // Decode bitmap with inSampleSize set
		       options.inJustDecodeBounds = false;
		       return BitmapFactory.decodeFile(filePath, options);
		    }
		
		//把bitmap转换成String
		public static String bitmapToString( Bitmap bm ) {

		        //Bitmap bm = getSmallBitmap(filePath);
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		        byte[] b = baos.toByteArray();
		        return Base64.encodeToString(b, Base64.DEFAULT);
		    }
}
