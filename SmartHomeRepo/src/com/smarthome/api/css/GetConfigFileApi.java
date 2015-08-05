package com.smarthome.api.css;

import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.linktop.oauth.OAuthUtil;
import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.common.TokenDispatcher;
import com.smarthome.api.model.HttpReqParam;
import com.smarthome.api.model.DevCtrlAction;
import com.smarthome.api.model.DeviceStatus;
import com.smarthome.api.model.HttpMethodType;
import com.smarthome.api.model.HttpResponse;

public class GetConfigFileApi extends CSSBaseApi{

	private static final String HOST = ApiCommonParams.API_URL;
	private static final String devCheckUrl = "/dev/check_dev_args";
	private static final String devChangeUrl = "/dev/change_dev_args";
	private static final String dispatchConf = "/dev/dispatch_conf";
	private static final String devCtrl = "/dev/dev_ctrl";
	
	public GetConfigFileApi() {
		super();
	}
	
	public void dispatchConf(final String pid, final String savedTime, final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				dict.put("savedTime", savedTime);
				HttpReqParam request = new HttpReqParam(HttpMethodType.Get, HOST + dispatchConf);
				request.setBody(dict);
				HttpResponse dispatchConfReq = TokenDispatcher.delegateHttpReqWithToken(request);
				if(dispatchConfReq.isSuccess()){
					try {
						cb.onSuccess(dispatchConfReq.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(dispatchConfReq.getCode()+"");
				}
				
			}
			
		});
	}
	
	
	public void devCtrl(final String pid, final DevCtrlAction action, final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				dict.put("action", action.name());
				HttpReqParam request = new HttpReqParam(HttpMethodType.Get, HOST + devCtrl);
				request.setBody(dict);
				HttpResponse devCtrlResp = TokenDispatcher.delegateHttpReqWithToken(request);
				if(devCtrlResp.isSuccess()){
					try {
						cb.onSuccess(devCtrlResp.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(devCtrlResp.getCode()+"");
				}
				
			}
			
		});
	}
	
	
	
	public void changeDevArgs(final DeviceStatus devStatus, final RequestCallback<String> cb) {

		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				if(devStatus == null){
					cb.onError("Device status cannot be NULL.");
					return;
				}
				JSONObject payloadObj = buildPayloadStr(devStatus);
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", devStatus.getPid());
				dict.put("payload", payloadObj.toString());
				HttpReqParam request = new HttpReqParam(HttpMethodType.Post, HOST + devChangeUrl);
				request.setBody(dict);
				HttpResponse checkDevResp = TokenDispatcher.delegateHttpReqWithToken(request);
				if(checkDevResp.isSuccess()){
					try {
						cb.onSuccess(checkDevResp.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(checkDevResp.getCode()+"");
				}
			}
			
			private JSONObject buildPayloadStr(DeviceStatus devStatus) {
				JSONObject payloadObj = new JSONObject();
				try {
					payloadObj.put("bluetooth", devStatus.getBluetooth());
					payloadObj.put("wifi", devStatus.getWifi());
					payloadObj.put("volumn", devStatus.getVolumn());
					payloadObj.put("brightness", devStatus.getBrightness());
					payloadObj.put("date", devStatus.getDate());
					payloadObj.put("im", devStatus.getIm());
					payloadObj.put("cellular", devStatus.getCellular());
					payloadObj.put("power_up", devStatus.getPowerUpTime());
					payloadObj.put("power_off", devStatus.getPowerOffTime());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return payloadObj;
			}
			
		});

	}
	
	public void checkDevArgs(final String pid,final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				HttpReqParam request = new HttpReqParam(HttpMethodType.Get, HOST + devCheckUrl);
				request.setBody(dict);
				HttpResponse checkDevResp = TokenDispatcher.delegateHttpReqWithToken(request);
				if(checkDevResp.isSuccess()){
					try {
						cb.onSuccess(checkDevResp.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(checkDevResp.getCode()+"");
				}
				
			}
			
		});
	}
		

}
