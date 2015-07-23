package com.smarthome.api;

import org.json.JSONException;

import com.smarthome.api.common.RequestCallback;
import com.smarthome.api.model.RegResult;
import com.smarthome.api.model.RegValCode;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public class RegisterApi {

	public static final String API_URL_LOGIN = "http://192.168.2.168:8400";
	private LoginRegService loginRegService;
	
	public RegisterApi(){
		initRestAdapter();
	}
	
	private void initRestAdapter(){
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(API_URL_LOGIN)
				.build();
		loginRegService = restAdapter.create(LoginRegService.class);
	}
	
	public void checkAccount(String account, final RequestCallback cb){
		 loginRegService.checkAccount(account, new Callback<RegResult>(){

			@Override
			public void failure(RetrofitError error) {
				cb.onError(error.getMessage());
			}

			@Override
			public void success(RegResult result, Response arg1) {
				try {
					cb.onSuccess(result.getState()+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public void reqValCode(String account, final RequestCallback cb){
		 loginRegService.reqValCode(account, new Callback<RegResult>(){

			@Override
			public void failure(RetrofitError error) {
				cb.onError(error.getMessage());
			}

			@Override
			public void success(RegResult result, Response arg1) {
				try {
					cb.onSuccess(result.getState()+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public void register(String account, String pwd, String val, final RequestCallback cb){
		loginRegService.register(account, pwd, val, new Callback<RegResult>(){
			
			@Override
			public void failure(RetrofitError error) {
				cb.onError(error.getMessage());
			}
			
			@Override
			public void success(RegResult result, Response arg1) {
				try {
					cb.onSuccess(result.getState()+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	//for Test only
	public void getValcode(String mobile, final RequestCallback cb){
		loginRegService.getValCode(mobile, new Callback<RegValCode>(){
			
			@Override
			public void failure(RetrofitError error) {
				cb.onError(error.getMessage());
			}
			
			@Override
			public void success(RegValCode result, Response arg1) {
				try {
					cb.onSuccess(result.getVal_code()+"");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
	
	public interface LoginRegService{
		
		@POST("/valid_acc_new")
		void checkAccount(@Query("account") String account, Callback<RegResult> cb);
		
		@POST("/req_reg_val_code")
		void reqValCode(@Query("account") String account, Callback<RegResult> cb);
		
		@GET("/get_reg_val_code")
		void getValCode(@Query("mobile") String mobile, Callback<RegValCode> cb);
		
		@POST("/check_reg_val_code")
		void register(@Query("account") String account, @Query("pwd") String pwd,
				@Query("val") String val, Callback<RegResult> cb);
		
	}
	
}
