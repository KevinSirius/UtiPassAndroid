package com.ericssonlabs;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.ustb.fx.FragmentMessage;
import com.ustb.fx.FragmentMine;
import com.ustb.fx.FragmentPatrol;
import com.ustb.fx.FragmentRepair;
//import com.ustb.fx.FragmentRepair;

public class BarCodeTestActivity extends FragmentActivity 
		 {

	private FragmentTabHost mTabHost;
	private RadioGroup mTabRg;
	private ViewPager mViewPage;
	TabsAdapter mTabsAdapter;
	private final Class[] fragments = { FragmentRepair.class, FragmentPatrol.class,
			FragmentMessage.class, FragmentMine.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_supportslip);
		// 自定义actionbar的布局
		setActionBarLayout(R.layout.actionbar_port_layout );
		initView();
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	private void initView() {

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager());
		mViewPage = (ViewPager) findViewById(R.id.pager);
		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPage, mTabRg);
		// 得到fragment的个数
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragments[i], null);

			mTabsAdapter.addTab(mTabHost.newTabSpec(i + "")
					.setIndicator(i + ""), fragments[i], null);
		}

		//定义滑动的监听
		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);

					break;
				case R.id.tab_rb_3:

					mTabHost.setCurrentTab(2);
					break;
				case R.id.tab_rb_4:

					mTabHost.setCurrentTab(3);
					break;

				default:
					break;
				}
			}
		});
		// mTabHost.setCurrentTab(0);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}


	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final RadioGroup mTabRg;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager, RadioGroup tabRg) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabRg = tabRg;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			((RadioButton) mTabRg.getChildAt(position)).setChecked(true);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
/*	*//**
	 * 设置ActionBar的布局
	 * @param layoutId 布局Id
	 * 
	 * *//*
*/	public void setActionBarLayout( int layoutId ){
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
/*
	*//**
	 * 实现onClick方法，在这里面监听actionbar中按钮的点击事件 
	 * 
	 * *//*
*/	public void onClick( View v ){
		if( v.getId( )==R.id.settingsBtnId ){
			
   		Intent intent = new Intent(this,Settings.class);
   		//intent.putExtra(name, nu)
   		//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
   		startActivity(intent);
   		//finish(); 		
		}
		
}

public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回键
	if(keyCode == KeyEvent.KEYCODE_BACK){
    	ConfirmExit();//按了返回键，但已经不能返回，则执行退出确认
    	return true; 
    }   
    return super.onKeyDown(keyCode, event);   
}	


public void ConfirmExit(){//退出确认
	AlertDialog.Builder ad=new AlertDialog.Builder(BarCodeTestActivity.this);
	ad.setTitle("退出");
	ad.setMessage("是否退出软件?");
	ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按钮
		@Override
		public void onClick(DialogInterface dialog, int i) {
			// TODO Auto-generated method stub
			BarCodeTestActivity.this.finish();//关闭activity

		}
	});
	ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int i) {
			//不退出不用执行任何操作
		}
	});
	ad.show();//显示对话框
}
}