package com.smarthome.api;

import java.util.HashMap;

import org.json.JSONException;

import com.linktop.JsonObj.API;
import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.HttpResponse;
import com.smarthome.push.BaiduPushModel;

public class PushApi {

	private static final String HOST = ApiCommonParams.API_URL;
	private static final String BIND_BAIDU_PUSH = "/account/bind_push";

	public static void bindBaiduPush(final String account, final BaiduPushModel baiduPushModel,
			final RequestCallback<String> cb){
		ApiPoolExecutor.getInstance().execute(new Runnable(){

			@Override
			public void run() {
				String pushArgs = buildPushArgs(baiduPushModel);
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("account", account);
				dict.put("os", "and");
				dict.put("ver", "0.1");
				dict.put("args", pushArgs);
				HttpResponse pushReq = HttpMethods.httpGet(HOST+BIND_BAIDU_PUSH, dict, null);
				if(pushReq.isSuccess()){
					try {
						cb.onSuccess("ok");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError("fail");
				}
				
			}

			private String buildPushArgs(final BaiduPushModel baiduPushModel) {
				StringBuilder pushArgsStrBuilder = new StringBuilder();
				pushArgsStrBuilder.append("baidu");
				pushArgsStrBuilder.append(",");
				pushArgsStrBuilder.append(baiduPushModel.getAppId());
				pushArgsStrBuilder.append(",");
				pushArgsStrBuilder.append(baiduPushModel.getUserId());
				pushArgsStrBuilder.append(",");
				pushArgsStrBuilder.append(baiduPushModel.getChannelId());
				return pushArgsStrBuilder.toString();
			}
			
		});
	}
}
