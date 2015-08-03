package com.smarthome;

import com.baidu.frontia.FrontiaApplication;

public class SmartHomeApplication extends FrontiaApplication {
	
	private static SmartHomeApplication application;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}
	
	public static SmartHomeApplication getApplication(){
		return application;
	}
	
}
