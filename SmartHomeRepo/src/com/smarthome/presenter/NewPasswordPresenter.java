package com.smarthome.presenter;

import org.json.JSONException;

import com.smarthome.api.ChangePasswordApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.presenter.view.CustomView;
import com.smarthome.presenter.view.NewPasswordView;

public class NewPasswordPresenter {

	private ChangePasswordApi changePasswordApi;
	private NewPasswordView view;

	public NewPasswordPresenter(CustomView view) {
		changePasswordApi = new ChangePasswordApi();
		this.view = (NewPasswordView)view;
	}

	public void pwdLost(final String username){
		changePasswordApi.pwdLost(username, new RequestCallback<String>() {
			@Override
			public void onSuccess(String response) throws JSONException {
				view.onReqPwdLostSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
	
	
	public void pwdNew(String username, String pwd, String val){
		changePasswordApi.pwdNew(username, pwd, val, new RequestCallback<String>() {
			@Override
			public void onSuccess(String response) throws JSONException {
				view.onReqPwdNewSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				view.onError();
			}
		});
	}
}
