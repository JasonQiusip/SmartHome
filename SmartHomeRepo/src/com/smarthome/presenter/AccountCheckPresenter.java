package com.smarthome.presenter;

import org.json.JSONException;

import android.util.Log;

import com.smarthome.api.AccountCheckApi;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.view.CustomView;
import com.smarthome.view.MobileCheckView;

public class AccountCheckPresenter {

	private AccountCheckApi mobileCheckApi;
	private MobileCheckView view;

	public AccountCheckPresenter(CustomView view){
		mobileCheckApi = new AccountCheckApi();
		this.view = (MobileCheckView)view;
		
	}
	
	public void startMobileCheck(String account){
		checkAccount(account);
	}
	
	
	private void checkAccount(String account) {
		this.view.showLoading();
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
