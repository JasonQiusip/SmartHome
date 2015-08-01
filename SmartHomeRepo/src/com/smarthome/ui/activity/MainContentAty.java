package com.smarthome.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.smarthome.R;
import com.smarthome.push.PushUtils;
import com.smarthome.ui.adapter.MainPagerAdapter;
import com.smarthome.ui.fragment.AlbumFragment;
import com.smarthome.ui.fragment.JiashuFragment;
import com.smarthome.ui.fragment.MyProfileFragment;

public class MainContentAty extends BaseAty{
	
	private ViewPager viewPager;
	private MainPagerAdapter mainPagerAdapter;
	private Fragment[] fragments = new Fragment[]{new JiashuFragment(), new AlbumFragment(), new MyProfileFragment()};
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main_content);
		startBaiduPush();
		
		setupToolBar();
		setupViewPager();
		
	}
	
	private void startBaiduPush(){
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				PushUtils.getMetaValue(this, "api_key"));
	}
	
	private void setupToolBar() {
		toolbar = (Toolbar)findViewById(R.id.mainContentToolbar);
	}

	private void setupViewPager() {
		viewPager = (ViewPager)findViewById(R.id.mainContentViewPager);
		mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mainPagerAdapter.setFragments(fragments);
		viewPager.setAdapter(mainPagerAdapter);
	}
	
	

}
