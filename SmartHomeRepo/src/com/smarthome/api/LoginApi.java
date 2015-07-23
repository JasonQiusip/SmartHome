package com.smarthomeloginreg.api;

import java.util.HashMap;

import org.json.JSONException;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

import com.linktop.API.CSSHttpUtil;
import com.smarthomeloginreg.api.common.ApiCommonParams;
import com.smarthomeloginreg.api.common.HttpMethods;
import com.smarthomeloginreg.api.common.RequestCallback;
import com.smarthomeloginreg.api.model.LoginResult;
import com.smarthomeloginreg.tools.LogUtil;

public class LoginApi {

	private static final String GEN_TK = "/gen_tk";
	
	public void login(final String username, final String pwd, final RequestCallback cb){
		new Thread(new Runnable(){

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", ApiCommonParams.api_key);
				dict.put("username", username);
				dict.put("pwd", pwd);
				String[] httpGet = CSSHttpUtil.httpGet(ApiCommonParams.API_URL + GEN_TK, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					onSuceess(cb, httpGet);
				}else{
					cb.onError(null);
				}
			}

			void onSuceess(final RequestCallback cb, String[] httpGet) {
				try {
					cb.onSuccess(httpGet[1]);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	

	
}
