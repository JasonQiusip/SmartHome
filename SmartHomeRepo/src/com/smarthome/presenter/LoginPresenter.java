package com.smarthome.presenter;

import com.smarthome.api.LoginApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.LoginResult;
import com.smarthome.presenter.view.CustomView;
import com.smarthome.presenter.view.LoginView;

public class LoginPresenter {

	private LoginApi loginApi;
	private final String TAG = this.getClass().getName();
	private LoginView view;
	
	public LoginPresenter(CustomView view){
		loginApi = new LoginApi();
		this.view = (LoginView)view;
	}
	
	public void login(final String username, final String pwd){
		loginApi.login(username, pwd, new RequestCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult response) {
				view.onLoginSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
	
	
}
