package com.smarthomeloginreg.api;

import org.json.JSONException;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import com.smarthomeloginreg.api.model.LoginResult;
import com.smarthomeloginreg.tools.LogUtil;

public class LoginApi {

	public static final String API_URL_LOGIN = "http://192.168.2.168:8400";
	private static final String api_key = "53c4ad5aacec40b9865cf6697ab75b96";
	private LoginService loginService;
	
	public LoginApi(){
		initRestAdapter();
	}
	
	private void initRestAdapter(){
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(API_URL_LOGIN)
				.setErrorHandler(new ErrorHandler(){

					@Override
					public Throwable handleError(RetrofitError cause) {
						if(cause != null){
							int status = cause.getResponse().getStatus();
							String body = cause.getResponse().getReason();
							LogUtil.showError("ErrorHandler", status+"" + body+"\n  " );
						}
						else{
							LogUtil.showError("ErrorHandler", cause+"");
							return new Throwable("Response is NULL");
						}
						return cause;
					}
					
				})
				.build();
		loginService = restAdapter.create(LoginService.class);
	}
	
//	public String login(String username, String pwd){
//		LoginResult loginResult = loginService.login(api_key, username, pwd);
//		if(loginResult == null)
//			return null;
//		return loginResult.getToken();
//	}
	
	public void loginWithCb(String username, String pwd, final RequestCallback cb){
		loginService.loginWithCb(api_key, username, pwd, new Callback<LoginResult>() {

			@Override
			public void failure(RetrofitError errorMsg) {
				cb.onError(errorMsg.toString());				
			}

			@Override
			public void success(LoginResult result, Response arg1) {
				try {
					cb.onSuccess(result.getToken());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public interface LoginService {

		@GET("/gen_tk")
		LoginResult login(@Query("api_key") String api_key, @Query("username") String username,
				@Query("pwd") String pwd);
		
		@GET("/gen_tk")
		void loginWithCb(@Query("api_key") String api_key, @Query("username") String username,
				@Query("pwd") String pwd, Callback<LoginResult> cb);
		
	}
}
