package com.smarthome.api;

import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RequestCallback;

public class GetConfigFileApi {

	private static final String HOST = ApiCommonParams.API_URL;
	private static final String devCheckUrl = "/dev/check_dev_args";
	private static final String devChangeUrl = "/dev/change_dev_args";

	public static void checkDevArgs(final String pid, final RequestCallback cb) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject payloadObj = buildPayloadStr();
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				dict.put("payload", payloadObj.toString());
				TreeMap<String, String> header = new TreeMap<String, String>();
				dict.put("Username", "hxy@163.com");
				String[] checkDevResp = HttpMethods.httpPost(
						HOST + devCheckUrl, dict, header);
				if(checkDevResp[0] != null && checkDevResp[0].equals("200")){
					onLoginSuceess(cb, checkDevResp);
				}else{
					cb.onError(null);
				}
			}
			
		}).start();

	}
	
	private static JSONObject buildPayloadStr() {
		JSONObject payloadObj = new JSONObject();
		try {
			payloadObj.put("bluetooth", 1);
			payloadObj.put("wifi", 1);
			payloadObj.put("volumn", 50);
			payloadObj.put("brightness", 50);
			payloadObj.put("date", "2015-07-12 15:00:00");
			payloadObj.put("im", "sougou");
			payloadObj.put("cellular", "lte");
			payloadObj.put("power_up", "15:00:00");
			payloadObj.put("power_off", "24:00:00");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return payloadObj;
	}
	
	static void onLoginSuceess(final RequestCallback cb, String[] httpGet) {
		try {
			cb.onSuccess(httpGet[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	public static void changeDevArgs() {
		
	}
		

}
