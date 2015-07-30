package com.smarthome.ui.activity;

import android.os.Bundle;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.smarthome.R;
import com.smarthome.push.PushUtils;

public class MainContentAty extends BaseAty{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main_content);
		startBaiduPush();
	}
	
	private void startBaiduPush(){
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				PushUtils.getMetaValue(this, "api_key"));
	}

}
