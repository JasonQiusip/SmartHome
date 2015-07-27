package com.smarthome.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.smarthome.api.RegisterApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.presenter.view.CustomView;
import com.smarthome.presenter.view.RegisterView;

public class RegisterPresenter {

	private RegisterApi registerApi;
	private RegisterView view;
	private final String TAG = this.getClass().getName();

	public RegisterPresenter(CustomView view){
		registerApi = new RegisterApi();
		this.view = (RegisterView) view;
	}
	
	public void reqRegValCode(String mobile){
		reqValCode(mobile);
	}
	
	public void finishReg(String account, String pwd, String val){
		register(account, pwd, val);
	}
	

	
	
	private void reqValCode(String mobile) {
		this.view.showLoading();
		registerApi.reqValCode(mobile, new RequestCallback(){
			@Override
			public void onSuccess(String response) throws JSONException {
				Log.e(TAG, response);
				view.hideLoading();
				view.onReqRegSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				Log.e(TAG, errorMsg);
				view.hideLoading();
			}
			
		});
	}
	
	private void register(String account, String pwd, String val) {
		this.view.showLoading();
		registerApi.register(account, pwd, val, new RequestCallback(){
			@Override
			public void onSuccess(String response) throws JSONException {
				Log.e(TAG, response);
				view.hideLoading();
				view.onFinishRegSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				Log.e(TAG, errorMsg);
				view.hideLoading();
				
			}
			
		});
	}

	//for test only
	public void getValCode(String mobile) {
		this.view.showLoading();
		registerApi.getValcode(mobile, new RequestCallback(){
			@Override
			public void onSuccess(String response) throws JSONException {
				Log.e(TAG, response);
				view.hideLoading();
				view.onGetValCode(response);
			}
			@Override
			public void onError(String errorMsg) {
				Log.e(TAG, errorMsg);
				view.hideLoading();
			}
		});
	}

}
