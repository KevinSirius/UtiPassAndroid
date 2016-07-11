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
	 * ������Fragmentʱ��ϵͳ���ø÷��� ��ʵ�ִ�����, Ӧ����ʼ����Ҫ��Fragment�б��ֵı�Ҫ���,
	 * ��Fragment����ͣ����ֹͣ������ٴλָ���
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

	/** ��Activity��onCreate()����ִ����֮�󣬵�������ص������� */
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
					// ��ȡϵͳ��ǰʱ��
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String dfstr = df.format(new Date());
					System.out.println(df.format(new Date()));// new
																// Date()Ϊ��ȡ��ǰϵͳʱ��
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
					Toast.makeText(getActivity(), "δ�������磬���������ԣ�",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
			}
		});

        webview = mPullWebView.getRefreshableView();
      //��ʹ�û��棺
      //  webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview.getSettings().setJavaScriptEnabled(true); // ����JavaScript����
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webview.setWebViewClient(new WebViewClient()); // �������֪ͨ�������¼��������ʹ�øþ���룬��ʹ�����������������ҳ
		webview.setWebChromeClient(new WebChromeClient(){
			//��չ֧��alert�¼�
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("��ʾ��Ϣ").setMessage(message).setPositiveButton("ȷ��", null);
                builder.setCancelable(false);
               // builder.setIcon(R.drawable.yunwei_icon);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }
		}
		);	//����JavaScript�Ի���
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
				Toast.makeText(getActivity(), "�ÿͻ���û����Ȩ��", Toast.LENGTH_SHORT)
						.show();
			} else if (validto == null || validto.length() <= 5) {
				Toast.makeText(getActivity(), "�ÿͻ���û����Ȩ��", Toast.LENGTH_SHORT)
						.show();
			} else if (validto.compareTo(dfstr) < 0) {
				Toast.makeText(getActivity(), "��Ч���ѹ��ڣ�", Toast.LENGTH_SHORT)
						.show();
			} else {
				webview.loadUrl(s);

				webview.refreshDrawableState();
			}
		} else {
			webview.loadUrl("file:///android_asset/index.html");

			webview.refreshDrawableState();
			Toast.makeText(getActivity(), "δ�������磬���������ԣ�", Toast.LENGTH_SHORT)
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

	/** ����Fragment��������ͼ�����ڱ�ɾ��ʱ����������ص������� */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/** ����Activity�н��Fragment�Ĺ���ʱ����������ص������� */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/* ================================================================ */
	/**
	 * �û���Ҫ�뿪Fragmentʱ,ϵͳ�������������Ϊ��һ��ָʾ(Ȼ������������ζ��Fragment�������١�)
	 * �ڵ�ǰ�û��Ự����֮ǰ,ͨ��Ӧ���������ύ�κ�Ӧ�ó־û��ı仯(��Ϊ�û��п��ܲ��᷵��)��
	 */
	@Override
	public void onPause() {
		super.onPause();
	}

}