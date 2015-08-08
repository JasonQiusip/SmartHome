package com.smarthome.api;

import org.json.JSONException;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Query;

import com.smarthome.api.RegisterApi.RegService;
import com.smarthome.api.common.ApiCommonParams;
import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.RegResult;
import com.smarthome.tools.LogUtil;

public class AccountCheckApi {

	
	private MobileCheckService mobileCheckService;

	public AccountCheckApi(){
		initRestAdapter();
	}
	
	private void initRestAdapter(){
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(ApiCommonParams.AUTHORIZE_URL)
				.build();
		mobileCheckService = restAdapter.create(MobileCheckService.class);
	}
	
	public void checkAccount(String account, final RequestCallback<String> cb){
		mobileCheckService.checkAccount(account, new Callback<RegResult>(){

				@Override
				public void failure(RetrofitError error) {
					LogUtil.showError("Handle ERROR", error +" ----- status ");
					
					cb.onError(error.getMessage());
				}

				@Override
				public void success(RegResult result, Response arg1) {
					LogUtil.showError("Handle success", result.getState()+" ----- success ");
					try {
						cb.onSuccess(result.getState()+"");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			});
	}
	
	public interface MobileCheckService{
		
		@POST("/valid_acc_new")
		void checkAccount(@Query("account") String account, Callback<RegResult> cb);
	}
}
