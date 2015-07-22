package com.smarthomeloginreg.presenter;

import org.json.JSONException;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.smarthomeloginreg.api.LoginApi;
import com.smarthomeloginreg.api.RequestCallback;
import com.smarthomeloginreg.tools.LogUtil;
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
	
	public void loginWithCb(final String username, final String pwd){
		loginApi.loginWithCb(username, pwd, new RequestCallback() {
			
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
	
//	public void login(final String username, final String pwd){
//		view.showLoading();
//		Observable.create(new OnSubscribe<String>() {
//
//			@Override
//			public void call(Subscriber<? super String> subscriber) {
//				String loginResult = loginApi.login(username, pwd);
//				if(loginResult == null){
//					subscriber.onError(null);
//				}
//				else{
//					subscriber.onNext(loginResult);
//				}
//				subscriber.onCompleted();
//			}
//		})
//		.subscribeOn(Schedulers.newThread())
//		.observeOn(Schedulers.computation())
//		.subscribe(new Observer<String>(){
//
//			@Override
//			public void onCompleted() {
//				view.onError();
//			}
//
//			@Override
//			public void onError(Throwable arg0) {
//				view.onError();
//			}
//
//			@Override
//			public void onNext(String response) {
//				view.reqRegSuccess(response);
//			}
//			
//		});
//		
//	}
}
