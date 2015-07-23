package com.smarthomeloginreg.api;

import java.util.HashMap;
import org.json.JSONException;
import com.smarthomeloginreg.api.common.ApiCommonParams;
import com.smarthomeloginreg.api.common.HttpMethods;
import com.smarthomeloginreg.api.common.RequestCallback;

public class ChangePasswordApi {
	
	private static final String PWD_LOST = "/pwd_lost";
	private static final String PWD_NEW = "/pwd_new";
	public void pwdLost(final String username, final RequestCallback cb){
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", ApiCommonParams.api_key);
				dict.put("account", username);
				String[] httpGet = HttpMethods.httpPost(ApiCommonParams.API_URL + PWD_LOST, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					try {
						cb.onSuccess(httpGet[1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(null);
				}
			}
			
		}).start();
	}
	
	public void pwdNew(final String username,final String pwd, final String val, final RequestCallback cb){
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pwd", pwd);
				dict.put("account", username);
				dict.put("val", val);
				String[] httpGet = HttpMethods.httpPost(ApiCommonParams.API_URL + PWD_NEW, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					try {
						cb.onSuccess(httpGet[1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(null);
				}
			}
			
		}).start();
	}
	
}
