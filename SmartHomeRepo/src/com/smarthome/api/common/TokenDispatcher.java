package com.smarthome.api.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import com.linktop.oauth.OAuthUtil;
import com.smarthome.api.LoginApi;
import com.smarthome.api.model.DelegateHttpRequest;
import com.smarthome.api.model.HttpMethodType;
import com.smarthome.api.model.HttpResponse;
import com.smarthome.api.model.LoginResult;

public class TokenDispatcher {

	private static final String TOKEN2 = "token";
	private static final String PWD = "pwd";
	public static final String ACC = "acc";
	public static final String SEC = "sec";
	public static final String EXP = "exp";
	private static final Pattern tokenPattern = Pattern
			.compile("([^|]+:[^|]+)");

	public synchronized static HttpResponse delegateHttpWithToken(DelegateHttpRequest request) {
		HashMap<String, String> localTk = getAccInfoFromDisk();
		if (null == localTk) {
			return null;
		}
		String token = localTk.get(TOKEN2);
		String url = request.getUrlPath();
		HashMap<String, String> body = request.getBody();
		TreeMap<String, String> header = request.getHeader();
		String sign = OAuthUtil.getSignature(url, body, "GET", ApiCommonParams.api_key, ApiCommonParams.api_secret);
		body.put("_tk", token);
		body.put("_sign", sign);
		HttpResponse httpResponse;
		if (request.getType() == HttpMethodType.Get) {
			httpResponse = HttpMethods.httpGet(url, body, header);
		} else {
			httpResponse = HttpMethods.httpPost(url, body, header);
		}

		HashMap<String, String> decryptedResult = getDecryptedResult(token);
		String username = decryptedResult.get(ACC);
		String pwd = localTk.get(PWD);

		if (httpResponse.isAuthorizeFailed()) {
			HttpResponse loginResp = LoginAgain(pwd, username);
			if(loginResp.isSuccess()){
				LoginApi.storeAccToDisk(loginResp.getContent(), pwd);
				return delegateHttpWithToken(request);
			}else {
				return loginResp;
			} 
		}else if(httpResponse.isSuccess()){
			return httpResponse;
		}else{
			return httpResponse;
		}

	}

	public static HashMap<String, String> getAccInfoFromDisk() {
		HashMap<String, String> accInfoMap = new HashMap<String, String>();
		try {
			Snapshot snapshot = CacheUtil.openDiskLruCache(LoginApi.MISC).get(
					LoginApi.ACCOUNT_INFO);
			String token = snapshot
					.getString(ApiConstants.TOKEN_DISK_LRU_INDEX);
			String pwd = snapshot.getString(ApiConstants.PWD_DISK_LRU_INDEX);
			if (token == null)
				return null;
			if (pwd == null)
				return null;
			accInfoMap.put(TOKEN2, token);
			accInfoMap.put(PWD, token);
			return accInfoMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HttpResponse LoginAgain(String pwd, String username) {
		return LoginApi.LoginAsync(username, pwd);
	}

	private static HashMap<String, String> getDecryptedResult(String token) {
		byte[] decryptToken = decryptToken(token);
		return diceToken(decryptToken);
	}

	// exp:{expire}|sec:{secret}|acc:{username}|pkey:{apikey_head4}
	public static byte[] decryptToken(String token) {
		byte[] key = convertStringToBytes(ApiCommonParams.encryption_key);
		RC4 rc4 = new RC4(key);
		byte[] tokenBytes = convertHexToBytes(token);
		byte[] decryptTxt = rc4.decrypt(tokenBytes);
		return decryptTxt;

	}

	private static byte[] convertStringToBytes(String str) {
		return ConvertTool.charToByteArray(str.toCharArray());
	}

	static byte[] convertHexToBytes(String token) {
		return ConvertTool.hexStringToByteArray(token);
	}

	public static HashMap<String, String> diceToken(byte[] decryptTxt) {
		Matcher matcher = tokenPattern.matcher(ConvertTool.byteArrayToStr(
				decryptTxt).toString());
		HashMap<String, String> regexResult = new HashMap<String, String>();
		if (!matcher.find())
			return null;
		String matchedGroup = matcher.group();
		do {
			String value = matchedGroup.split(":")[1];
			if (matchedGroup.contains(EXP)) {
				regexResult.put(EXP, value);
			} else if (matchedGroup.contains(SEC)) {
				regexResult.put(SEC, value);
			} else if (matchedGroup.contains(ACC)) {
				regexResult.put(ACC, value);
			}
		} while (matcher.find());
		return regexResult;
	}
}
