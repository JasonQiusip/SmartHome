package com.smarthome.presenter;

import org.json.JSONException;

import android.util.Log;

import com.smarthome.api.AccountCheckApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.presenter.view.AccountCheckView;
import com.smarthome.presenter.view.CustomView;

public class AccountCheckPresenter {

	private AccountCheckApi mobileCheckApi;
	private AccountCheckView view;

	public AccountCheckPresenter(CustomView view){
		mobileCheckApi = new AccountCheckApi();
		this.view = (AccountCheckView)view;
		
	}
	
	public void startMobileCheck(String account){
		checkAccount(account);
	}
	
	
	private void checkAccount(String account) {
		view.showLoading();
		mobileCheckApi.checkAccount(account, new RequestCallback(){
			@Override
			public void onSuccess(String response) throws JSONException {
				view.hideLoading();
				view.onReqSuccess(response);
			}
			@Override
			public void onError(String errorMsg) {
				view.hideLoading();
				view.onReqError();
			}
			
		});
	}
}
