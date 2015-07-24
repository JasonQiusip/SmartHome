package com.smarthome.api;

import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.HttpMethods;

public class GetConfigFileApi {

	private static final String HOST = ApiCommonParams.API_URL;
	private static final String devCheckUrl = "/dev/check_dev_args";
	private static final String devChangeUrl = "/dev/change_dev_args";
	
	public static void checkDevArgs(final String pid){
		 
		 new Thread(new Runnable(){

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				TreeMap<String, String> header = new TreeMap<String, String>();
				dict.put("Username", "hxy@163.com");
				HttpMethods.httpPost(HOST+devCheckUrl, dict, header);
			}
			 
		 }).start();
		 
		 
		 
		 
	}
	
	public static void changeDevArgs() throws JSONException{
		JSONObject obj = new JSONObject();
		 obj.put("bluetooth", 1);
		 obj.put("wifi", 1);
		 obj.put("volumn", 50);
		 obj.put("brightness", 50);
		 obj.put("date", "2015-07-12 15:00:00");
		 obj.put("im", "sougou");
		 obj.put("cellular", "lte");
		 obj.put("power_up", "15:00:00");
		 obj.put("power_off", "24:00:00");
	}
	
}
