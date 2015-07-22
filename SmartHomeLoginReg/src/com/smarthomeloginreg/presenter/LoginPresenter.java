package com.smarthomeloginreg.presenter;

import org.json.JSONException;

import com.smarthomeloginreg.api.LoginApi;
import com.smarthomeloginreg.api.RequestCallback;
import com.smarthomeloginreg.view.CustomView;
import com.smarthomeloginreg.view.LoginView;

public class LoginPresenter {

	private LoginApi loginApi;
	private final String TAG = this.getClass().getName();
	private LoginView view;
	
	public LoginPresenter(CustomView view){
		loginApi = new LoginApi();
		this.view = (LoginView)view;
	}
	
	public void login(final String username, final String pwd){
		loginApi.login(username, pwd, new RequestCallback() {
			
			@Override
			public void onSuccess(String response) throws JSONException {
				view.reqRegSuccess(response);
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
	
	public void pwdLost(final String username){
		loginApi.pwdLost(username, new RequestCallback() {
			
			@Override
			public void onSuccess(String response) throws JSONException {
				view.reqRegSuccess(response);
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
	
	
	public void pwdNew(String username, String pwd, String val){
		loginApi.pwdNew(username, pwd, val, new RequestCallback() {
			
			@Override
			public void onSuccess(String response) throws JSONException {
				view.reqRegSuccess(response);
			}
			
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
}
