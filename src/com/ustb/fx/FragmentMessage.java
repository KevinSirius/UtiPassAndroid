package com.ustb.fx;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ericssonlabs.R;
import com.ustb.pullrefresh.ui.PullToRefreshBase;
import com.ustb.pullrefresh.ui.PullToRefreshWebView;
import com.ustb.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.ustb.utils.Admin;
import com.ustb.utils.DeviceId;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.webkit.JsResult;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
@SuppressLint("SimpleDateFormat") public class FragmentMessage extends Fragment {
	private WebView webview;
	private PullToRefreshWebView mPullWebView;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private String s, number_str, minestr;
	private Admin admin;
	private Boolean state;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	/**
	 * 当创建Fragment时，系统调用该方法 在实现代码中, 应当初始化想要在Fragment中保持的必要组件,
	 * 当Fragment被暂停或者停止后可以再次恢复。
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View view=inflater.inflate(R.layout.fragment_message, container,
		// false);

		return inflater.inflate(R.layout.fragment_message, container, false);
	}

	/** 当Activity的onCreate()方法执行完之后，调用这个回调方法。 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		DeviceId deviceid = new DeviceId(getActivity());
		number_str = deviceid.getDeviceId();
		s = "http://t.tipass.com/index.php/Chat/chatapi?c=" + number_str;
		// String s = "http://www.tipass.com/index.php/Index/index";
		minestr = "http://t.tipass.com/index.php/License/viewlicenseapi?c="
				+ number_str;
		admin = new Admin();
		state = admin.isNetworkAvailable(getActivity());

		mPullWebView = (PullToRefreshWebView) getView().findViewById(
				R.id.webview_message);// new PullToRefreshWebView(this);
		mPullWebView.setOnRefreshListener(new OnRefreshListener<WebView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<WebView> refreshView) {

				if (state == true) {
					String jsonstr = admin.getJson(minestr);
					String validfrom = admin.parseJsonValidfrom(jsonstr);
					String validto = admin.parseJsonValidto(jsonstr);
					// 获取系统当前时间
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dfstr = df.format(new Date());
					System.out.println(df.format(new Date()));// new
																// Date()为获取当前系统时间
					if (validfrom == null || validfrom.length() <= 5) {
						webview.loadUrl("file:///android_asset/index.html");
					} else if (validto == null || validto.length() <= 5) {
						webview.loadUrl("file:///android_asset/index.html");
					} else if (validto.compareTo(dfstr) < 0) {
						webview.loadUrl("file:///android_asset/index.html");
					} else {
						webview.loadUrl(s);

						webview.refreshDrawableState();
					}
				} else {
					webview.loadUrl("file:///android_asset/index.html");
					webview.refreshDrawableState();
					Toast.makeText(getActivity(), "未连接网络，请联网重试！",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
			}
		});

        webview = mPullWebView.getRefreshableView();
      //不使用缓存：
      //  webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.getSettings().setJavaScriptEnabled(true); // 设置JavaScript可用
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webview.setWebViewClient(new WebViewClient()); // 处理各种通知和请求事件，如果不使用该句代码，将使用内置浏览器访问网页
		webview.setWebChromeClient(new WebChromeClient(){
			//扩展支持alert事件
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示信息").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
               // builder.setIcon(R.drawable.yunwei_icon);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }
		}
		);	//处理JavaScript对话框
		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			public void onPageFinished(WebView view, String url) {
				mPullWebView.onPullDownRefreshComplete();
				setLastUpdateTime();
			}
		});

		if (state == true) {
			String jsonstr = admin.getJson(minestr);
			String validfrom = admin.parseJsonValidfrom(jsonstr);
			String validto = admin.parseJsonValidto(jsonstr);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dfstr = df.format(new Date());
			if (validfrom == null || validfrom.length() <= 5) {
				Toast.makeText(getActivity(), "该客户端没有授权！", Toast.LENGTH_SHORT)
						.show();
			} else if (validto == null || validto.length() <= 5) {
				Toast.makeText(getActivity(), "该客户端没有授权！", Toast.LENGTH_SHORT)
						.show();
			} else if (validto.compareTo(dfstr) < 0) {
				Toast.makeText(getActivity(), "有效期已过期！", Toast.LENGTH_SHORT)
						.show();
			} else {
				webview.loadUrl(s);

				webview.refreshDrawableState();
			}
		} else {
			webview.loadUrl("file:///android_asset/index.html");

			webview.refreshDrawableState();
			Toast.makeText(getActivity(), "未连接网络，请联网重试！", Toast.LENGTH_SHORT)
					.show();
		}

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
	}

}