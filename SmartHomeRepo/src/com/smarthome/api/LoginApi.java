package com.smarthome.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Editor;
import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.CacheUtil;
import com.smarthome.api.common.ApiConstants;
import com.smarthome.api.common.ConvertTool;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RC4;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.common.TokenDispatcher;
import com.smarthome.api.model.HttpResponse;
import com.smarthome.api.model.LoginResult;

public class LoginApi {

	public static final String ACCOUNT_INFO = "acc_related";
	public static final String MISC = "misc";
	private static final String GEN_TK = "/gen_tk";

	public synchronized static void login(final String username, final String pwd,
			final RequestCallback<LoginResult> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HttpResponse login = LoginAsync(username, pwd);
				if (login.isSuccess()) {
					storeAccToDisk(login.getContent(), pwd);
					onLoginSuceess(cb, login);
				} else {
					cb.onError(null);
				}
			}


		});
	}

	public static HttpResponse LoginAsync(final String username,
			final String pwd) {
		HashMap<String, String> dict = new HashMap<String, String>();
		dict.put("api_key", ApiCommonParams.api_key);
		dict.put("username", username);
		dict.put("pwd", pwd);
		HttpResponse login = HttpMethods.httpGet(
				ApiCommonParams.AUTHORIZE_URL + GEN_TK, dict, null);
		return login;
	}
	
	public static void storeAccToDisk(String content, String pwd) {
		try {
			DiskLruCache diskLruCache = CacheUtil.openDiskLruCache(MISC);
			Editor editor = diskLruCache.edit(ACCOUNT_INFO);
			editor.set(ApiConstants.TOKEN_DISK_LRU_INDEX, content);
			editor.set(ApiConstants.PWD_DISK_LRU_INDEX, pwd);
			editor.commit();
			// OutputStream newOutputStream = editor.newOutputStream(0);
			// newOutputStream.write(ConvertTool.charToByteArray(content.toCharArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void onLoginSuceess(final RequestCallback<LoginResult> cb,
			HttpResponse loginResult) {
		try {
			byte[] decryptedToken = TokenDispatcher.decryptToken(loginResult.getContent());
			if(decryptedToken == null){
				cb.onError("token decrypt fail");
				return;
			}
			cb.onSuccess(getLoginResult(decryptedToken));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static LoginResult getLoginResult(byte[] decryptTxt) {
		HashMap<String, String> dicedToken = TokenDispatcher.diceToken(decryptTxt);
		if(dicedToken == null)
			return null;
		LoginResult loginResult = new LoginResult();
		loginResult.setExpireTimestamp(dicedToken.get(TokenDispatcher.EXP));
		loginResult.setSecret(dicedToken.get(TokenDispatcher.SEC));
		loginResult.setAccount(dicedToken.get(TokenDispatcher.ACC));
		return loginResult;
	}

}
