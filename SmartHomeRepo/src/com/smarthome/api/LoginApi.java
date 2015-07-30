package com.smarthome.api;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

import android.util.Log;

import com.linktop.API.CSSHttpUtil;
import com.linktop.oauth.RsyncUtils;
import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.ConvertTool;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RC4;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.HttpResponse;
import com.smarthome.api.model.LoginResult;
import com.smarthome.tools.LogUtil;

public class LoginApi {

	private static final String GEN_TK = "/gen_tk";
	private static final Pattern tokenPattern = Pattern.compile("([^|]+:[^|]+)");
	public void login(final String username, final String pwd, final RequestCallback<LoginResult> cb){
		ApiPoolExecutor.getInstance().execute(new Runnable(){

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("api_key", ApiCommonParams.api_key);
				dict.put("username", username);
				dict.put("pwd", pwd);
				HttpResponse login = HttpMethods.httpGet(ApiCommonParams.AUTHORIZE_URL + GEN_TK, dict, null);
				if(login.isSuccess()){
					
					onLoginSuceess(cb, login);
				}else{
					cb.onError(null);
				}
			}

		});
	}
	
	void onLoginSuceess(final RequestCallback<LoginResult> cb, HttpResponse loginResult) {
		try {
			cb.onSuccess(decryptToken(loginResult.getContent()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//exp:{expire}|sec:{secret}|acc:{username}|pkey:{apikey_head4}
	static LoginResult decryptToken(String token) {
		byte[] key = convertStringToBytes(ApiCommonParams.encryption_key);
		RC4 rc4 = new RC4(key);
		byte[] tokenBytes = convertHexToBytes(token);
		byte[] decryptTxt = rc4.decrypt(tokenBytes);
		return decryptToken(decryptTxt);
		
	}

	private static byte[] convertStringToBytes(String str) {
		return ConvertTool.charToByteArray(str.toCharArray());
	}
	
	static byte[] convertHexToBytes(String token){
		return ConvertTool.hexStrToByteArray(token);
	}

	private static LoginResult decryptToken(byte[] decryptTxt) {
		
		Matcher matcher = tokenPattern.matcher(new String(decryptTxt));
		LoginResult loginResult = new LoginResult();
		while(matcher.find()){
			String matchedGroup = matcher.group();
			String value = matchedGroup.split(":")[1];
			if(matchedGroup.contains("exp")){
				loginResult.setExpireTimestamp(value);
			}else if(matchedGroup.contains("sec")){
				loginResult.setSecret(value);
			}else if(matchedGroup.contains("acc")){
				loginResult.setAccount(value);
			}
		}
		return loginResult;
	}

	
}
