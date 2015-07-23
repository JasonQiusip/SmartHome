package com.smarthome.presenter;

import org.json.JSONException;

import com.smarthome.api.ChangePasswordApi;
import com.smarthome.api.LoginApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.view.CustomView;
import com.smarthome.view.LoginView;

public class LoginPresenter {

	private LoginApi loginApi;
	private ChangePasswordApi changePasswordApi;
	private final String TAG = this.getClass().getName();
	private LoginView view;
	
	public LoginPresenter(CustomView view){
		loginApi = new LoginApi();
		changePasswordApi = new ChangePasswordApi();
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
		changePasswordApi.pwdLost(username, new RequestCallback() {
			
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
		changePasswordApi.pwdNew(username, pwd, val, new RequestCallback() {
			
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
