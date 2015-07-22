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
import com.smarthomeloginreg.api.model.LoginResult;
import com.smarthomeloginreg.tools.NetUtil;
import com.smarthomeloginreg.tools.LogUtil;

public class LoginApi {

	public static final String API_URL_LOGIN = "http://192.168.2.168:8400";
	public static final String GEN_TK = "/gen_tk";
	public static final String PWD_LOST = "/pwd_lost";
	public static final String PWD_NEW = "/pwd_new";
	
	private static final String api_key = "53c4ad5aacec40b9865cf6697ab75b96";
	
	public void login(final String username, final String pwd, final RequestCallback cb){
		new Thread(new Runnable(){

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", api_key);
				dict.put("username", username);
				dict.put("pwd", pwd);
				String[] httpGet = CSSHttpUtil.httpGet(API_URL_LOGIN + GEN_TK, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					LogUtil.showError("loginWithCb", httpGet[1]+"");
					try {
						cb.onSuccess(httpGet[1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					LogUtil.showError("loginWithCb", httpGet[0]+"");
					cb.onError(null);
				}
			}
			
		}).start();
	}
	
	public void pwdLost(final String username, final RequestCallback cb){
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", api_key);
				dict.put("account", username);
				String[] httpGet = NetUtil.httpPost(API_URL_LOGIN + PWD_LOST, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					LogUtil.showError("pwdLostWithCb", httpGet[1]+"");
					try {
						cb.onSuccess(httpGet[1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					LogUtil.showError("pwdLostWithCb", httpGet[0]+"");
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
				String[] httpGet = NetUtil.httpPost(API_URL_LOGIN + PWD_NEW, dict, null);
				if(httpGet[0] != null && httpGet[0].equals("200")){
					LogUtil.showError("pwdLostWithCb", httpGet[1]+"");
					try {
						cb.onSuccess(httpGet[1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					LogUtil.showError("pwdLostWithCb", httpGet[0]+"");
					cb.onError(null);
				}
			}
			
		}).start();
	}
	
	
}
