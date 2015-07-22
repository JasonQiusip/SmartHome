package com.smarthomeloginreg.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.smarthomeloginreg.api.RegisterApi;
import com.smarthomeloginreg.api.RegCallback;
import com.smarthomeloginreg.view.CustomView;
import com.smarthomeloginreg.view.RegisterView;

public class RegisterPresenter {

	private RegisterApi loginRegApi;
	private RegisterView view;
	private final String TAG = this.getClass().getName();

	public RegisterPresenter(CustomView view){
		loginRegApi = new RegisterApi();
		this.view = (RegisterView) view;
	}
	
	public void startRegster(String account){
		reqRegister(account);
	}
	
	public void reqValCode(String mobile){
		reqRegCodeMobile(mobile);
	}
	
	public void finishReg(String account, String pwd, String val){
		register(account, pwd, val);
	}
	

	private void reqRegister(String account) {
		this.view.showLoading();
		loginRegApi.reqRegstration(account, new RegCallback(){


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
	
	private void reqRegCodeMobile(String mobile) {
		this.view.showLoading();
		loginRegApi.reqRegCodeMobile(mobile, new RegCallback(){
			
			private String val_code;
			
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
		loginRegApi.register(account, pwd, val, new RegCallback(){
			
			
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

	public void getValCode(String mobile) {
		this.view.showLoading();
		loginRegApi.getValcode(mobile, new RegCallback(){
			
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
