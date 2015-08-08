package com.smarthome.api.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import com.linktop.oauth.OAuthUtil;
import com.smarthome.api.LoginApi;
import com.smarthome.api.model.HttpReqParam;
import com.smarthome.api.model.HttpMethodType;
import com.smarthome.api.model.HttpResponse;

public class TokenDispatcher {

	private static final boolean STOP_TRYING = false;
	private static final String TOKEN2 = "token";
	private static final String PWD = "pwd";
	public static final String ACC = "acc";
	public static final String SEC = "sec";
	public static final String EXP = "exp";
	private static final Pattern tokenPattern = Pattern.compile("([^|]+:[^|]+){1,3}");

	public synchronized static HttpResponse delegateHttpReqWithToken(HttpReqParam request){
		return delegateHttpWithToken(request, true);
	}
	
	private synchronized static HttpResponse delegateHttpWithToken(HttpReqParam request, boolean tryAgain) {
		HashMap<String, String> localTk = getAccInfoFromDisk();
		HttpResponse httpResponse = new HttpResponse();
		if (null == localTk) {
			return httpResponse;
		}
		String token = localTk.get(TOKEN2);
		String url = request.getUrlPath();
		TreeMap<String, String> header = request.getHeader();
		HashMap<String, String> body = request.getBody();

		String sign = OAuthUtil.getSignature(url, body, "GET", ApiCommonParams.api_key, ApiCommonParams.api_secret);
		
		body.put("_tk", token);
		body.put("_sign", sign);
		
		if (request.getType() == HttpMethodType.Get) {
			httpResponse = HttpMethods.httpGet(url, body, header);
		} else {
			httpResponse = HttpMethods.httpPost(url, body, header);
		}

		if (httpResponse.isAuthorizeFailed()) {
			HashMap<String, String> decryptedResult = getDecryptedResult(token);
			String username = decryptedResult.get(ACC);
			String pwd = localTk.get(PWD);
			HttpResponse loginResp = LoginAgain(pwd, username);
			
			if(loginResp.isSuccess() && tryAgain){
				CacheUtil.storeAccToDisk(loginResp.getContent(), pwd);
				return delegateHttpWithToken(request, STOP_TRYING);
			}else {
				return loginResp;
			} 
		}else{
			return httpResponse;
		}

	}

	public static HashMap<String, String> getAccInfoFromDisk() {
		HashMap<String, String> accInfoMap = new HashMap<String, String>();
		try {
			Snapshot snapshot = CacheUtil.openDiskLruCache(CacheUtil.MISC).get(
					CacheUtil.ACCOUNT_INFO);
			String token = snapshot.getString(ApiConstants.TOKEN_DISK_LRU_INDEX);
			String pwd = snapshot.getString(ApiConstants.PWD_DISK_LRU_INDEX);
			if (token == null)
				return accInfoMap;
			if (pwd == null)
				return accInfoMap;
			accInfoMap.put(TOKEN2, token);
			accInfoMap.put(PWD, pwd);
			return accInfoMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static HashMap<String, String> getDecryptedResult(String token) {
		byte[] decryptToken = decryptToken(token);
		return diceToken(decryptToken);
	}

	// exp:{expire}|sec:{secret}|acc:{username}|pkey:{apikey_head4}
	public static byte[] decryptToken(String token) {
		byte[] key = ConvertTool.charToByteArray(ApiCommonParams.encryption_key.toCharArray());
		RC4 rc4 = new RC4(key);
		byte[] tokenBytes = ConvertTool.hexStringToByteArray(token);
		byte[] decryptTxt = rc4.decrypt(tokenBytes);
		return decryptTxt;

	}

	public static HashMap<String, String> diceToken(byte[] decryptTxt) {
		
		Matcher matcher = tokenPattern.matcher(ConvertTool.byteArrayToStr(decryptTxt).toString());
		HashMap<String, String> regexResult = new HashMap<String, String>();
		if(!matcher.find())
			return null;
		do{
			String matchedGroup = matcher.group();
			String value = matchedGroup.split(":")[1];
			if (matchedGroup.contains(EXP)) {
				regexResult.put(EXP, value);
			} else if (matchedGroup.contains(SEC)) {
				regexResult.put(SEC, value);
			} else if (matchedGroup.contains(ACC)) {
				regexResult.put(ACC, value);
			}
		}while(matcher.find());
		return regexResult;
	}
	
	private static HttpResponse LoginAgain(String pwd, String username) {
		return LoginApi.LoginAsync(username, pwd);
	}

	
	
}
