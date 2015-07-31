package com.smarthome.api;

import java.util.HashMap;
import org.json.JSONException;

import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.HttpResponse;

public class ChangePasswordApi {
	
	private static final String PWD_LOST = "/pwd_lost";
	private static final String PWD_NEW = "/pwd_new";
	public void pwdLost(final String username, final RequestCallback<String> cb){
		ApiPoolExecutor.getInstance().execute(new Runnable(){
			
			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", ApiCommonParams.api_key);
				dict.put("account", username);
				HttpResponse pwdLostReq = HttpMethods.httpPost(ApiCommonParams.AUTHORIZE_URL + PWD_LOST, dict, null);
				if(pwdLostReq.isSuccess()){
					try {
						cb.onSuccess(pwdLostReq.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(null);
				}
			}
			
		});
	}
	
	public void pwdNew(final String username,final String pwd, final String val, final RequestCallback<String> cb){
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pwd", pwd);
				dict.put("account", username);
				dict.put("val", val);
				HttpResponse pwdNewReq = HttpMethods.httpPost(ApiCommonParams.AUTHORIZE_URL + PWD_NEW, dict, null);
				if(pwdNewReq.isSuccess()){
					try {
						cb.onSuccess(pwdNewReq.getContent());
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
