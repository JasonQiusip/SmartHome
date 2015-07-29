package com.smarthome.api;

import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.RequestCallback;

public class AccountApi {

	private static final String BIND_BAIDU_PUSH = "/bind_push";
	
	public static void bindBaiduPush(RequestCallback cb){
		
		ApiPoolExecutor.getInstance().execute(new Runnable(){

			@Override
			public void run() {
				
			}
			
		});
		
	}
}
