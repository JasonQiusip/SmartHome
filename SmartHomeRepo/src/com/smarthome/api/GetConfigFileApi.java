package com.smarthome.api;

import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.ApiPoolExecutor;
import com.smarthome.api.common.HttpMethods;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.DevCtrlAction;
import com.smarthome.api.model.DeviceStatus;
import com.smarthome.api.model.HttpResponse;

public class GetConfigFileApi {

	private static final String HOST = ApiCommonParams.API_URL;
	private static final String devCheckUrl = "/dev/check_dev_args";
	private static final String devChangeUrl = "/dev/change_dev_args";
	private static final String dispatchConf = "/dev/dispatch_conf";
	private static final String devCtrl = "/dev/dev_ctrl";
	
	public static void dispatchConf(final String pid, final String savedTime, final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				dict.put("savedTime", savedTime);
				HttpResponse dispatchConfReq = HttpMethods.httpPost(HOST + dispatchConf, dict, null);
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
	
	
	public static void devCtrl(final String pid, final DevCtrlAction action, final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", pid);
				dict.put("action", action.name());
				HttpResponse devCtrlResp = HttpMethods.httpPost(HOST + devCtrl, dict, null);
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
	
	
	
	public static void checkDevArgs(final DeviceStatus devStatus, final RequestCallback<String> cb) {

		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				JSONObject payloadObj = buildPayloadStr(devStatus);
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("pid", devStatus.getPid());
				dict.put("payload", payloadObj.toString());
				TreeMap<String, String> header = new TreeMap<String, String>();
				dict.put("Username", "hxy@163.com");
				HttpResponse checkDevResp = HttpMethods.httpPost(
						HOST + devCheckUrl, dict, header);
				if(checkDevResp.isSuccess()){
					try {
						cb.onSuccess(checkDevResp.getContent());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					cb.onError(null);
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
	
	public static void changeDevArgs(final String cmd, final int value, final RequestCallback<String> cb) {
		ApiPoolExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				TreeMap<String, String> header = new TreeMap<String, String>();
				header.put("Content-Type", "application/json; charset=utf-8");
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put(cmd, String.valueOf(value));
				HttpResponse checkDevResp = HttpMethods.httpPost(HOST + devChangeUrl, dict, header);
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
