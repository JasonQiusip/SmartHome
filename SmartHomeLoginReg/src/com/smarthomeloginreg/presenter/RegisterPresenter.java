package com.smarthomeloginreg.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.smarthomeloginreg.api.RegisterApi;
import com.smarthomeloginreg.api.common.RequestCallback;
import com.smarthomeloginreg.view.CustomView;
import com.smarthomeloginreg.view.RegisterView;

public class RegisterPresenter {

	private RegisterApi registerApi;
	private RegisterView view;
	private final String TAG = this.getClass().getName();

	public RegisterPresenter(CustomView view){
		registerApi = new RegisterApi();
		this.view = (RegisterView) view;
	}
	
	public void startRegister(String account){
		checkAccount(account);
	}
	
	public void reqRegValCode(String mobile){
		reqValCode(mobile);
	}
	
	public void finishReg(String account, String pwd, String val){
		register(account, pwd, val);
	}
	

	private void checkAccount(String account) {
		this.view.showLoading();
		registerApi.checkAccount(account, new RequestCallback(){


			@Override
			public void onSuccess(String response) throws JSONException {
				view.hideLoading();
				Log.e(TAG, response);
				view.reqRegSuccess(response);
			}

			@Override
			public void onError(String errorMsg) {
				Log.e(TAG, errorMsg);
				view.hideLoading();
			}
			
		});
	}
	
	private void reqValCode(String mobile) {
		this.view.showLoading();
		registerApi.reqValCode(mobile, new RequestCallback(){
			
			@Override
			public void onSuccess(String response) throws JSONException {
				Log.e(TAG, response);
				view.hideLoading();
				view.reqRegSuccess(response);
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
				view.reqRegSuccess(response);
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
