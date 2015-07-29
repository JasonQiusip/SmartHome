package com.smarthome.api;

import java.util.HashMap;

import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.RequestCallback;

public class PushApi {

	private static final String BIND_BAIDU_PUSH = "/bind_push";
	// account=hxy@163.com&os=and&ver=0.1&args=baidu,"
	//+ appid + "," + userId + "," + channelId;
	public static void bindBaiduPush(String appid, String userId, String channelId, RequestCallback cb){
		ApiPoolExecutor.getInstance().execute(new Runnable(){

			@Override
			public void run() {
				String args = "baidu,";
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("account", null);
				dict.put("os", "and");
				dict.put("args", args);
				
			}
			
		});
	}
}
