package com.smarthome.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.rey.material.widget.Button;
import com.smarthome.R;
import com.smarthome.push.PushUtils;
import com.smarthome.ui.adapter.MainPagerAdapter;
import com.smarthome.ui.fragment.AlbumFragment;
import com.smarthome.ui.fragment.JiashuFragment;
import com.smarthome.ui.fragment.MyProfileFragment;

public class MainContentAty extends BaseAty implements OnClickListener{
	
	private ViewPager viewPager;
	private MainPagerAdapter mainPagerAdapter;
	private Fragment[] fragments = new Fragment[]{new JiashuFragment(), new AlbumFragment(), new MyProfileFragment()};
	private Toolbar toolbar;
	private Button mainHomeTabBtn;
	private Button mainMyProfileTabBtn;
	private Button mainAlbumTabBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main_content);
		startBaiduPush();
		
		setupToolBar();
		setupViewPager();
		setupTabs();
		setupOnClickeListener();
	}

	private void startBaiduPush(){
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				PushUtils.getMetaValue(this, "api_key"));
	}
	
	private void setupToolBar() {
		toolbar = (Toolbar)findViewById(R.id.mainContentToolbar);
	}

	private void setupTabs() {
		mainHomeTabBtn = (Button)findViewById(R.id.mainHomeTabBtn);
		mainAlbumTabBtn = (Button)findViewById(R.id.mainAlbumTabBtn);
		mainMyProfileTabBtn = (Button)findViewById(R.id.mainMyProfileTabBtn);
	}
	
	private void setupOnClickeListener() {
		mainHomeTabBtn.setOnClickListener(this);
		mainAlbumTabBtn.setOnClickListener(this);
		mainMyProfileTabBtn.setOnClickListener(this);
		
	}
	
	@SuppressWarnings("deprecation")
	private void setupViewPager() {
		viewPager = (ViewPager)findViewById(R.id.mainContentViewPager);
		mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mainPagerAdapter.setFragments(fragments);
		viewPager.setAdapter(mainPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.mainHomeTabBtn:
			viewPager.setCurrentItem(0);
			break;
		case R.id.mainAlbumTabBtn:
			viewPager.setCurrentItem(1);
			break;
		case R.id.mainMyProfileTabBtn:
			viewPager.setCurrentItem(2);
			break;
		}
	}
	
	

}
