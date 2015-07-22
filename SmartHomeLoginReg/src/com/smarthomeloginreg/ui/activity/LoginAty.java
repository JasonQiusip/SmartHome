package com.smarthomeloginreg.ui.activity;

import com.smarthomeloginreg.R;
import com.smarthomeloginreg.presenter.LoginPresenter;
import com.smarthomeloginreg.tools.LogUtil;
import com.smarthomeloginreg.view.LoginView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAty extends BaseAty implements LoginView, OnClickListener {

	private final String TAG = this.getClass().getName();
	private LoginPresenter loginPresenter;
	private EditText edtLoginUsername;
	private EditText edtLoginPwd;
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginPresenter = new LoginPresenter(this);
		edtLoginUsername = (EditText)findViewById(R.id.edtLoginUsername);
		edtLoginPwd = (EditText)findViewById(R.id.edtLoginPwd);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void reqRegSuccess(String response) {
		LogUtil.showError(TAG, response);
	}

	@Override
	public void onError() {
		LogUtil.showError(TAG, "Login Error");
		Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnLogin:
			String username = edtLoginUsername.getText().toString();
			String pwd = edtLoginPwd.getText().toString();
			loginPresenter.loginWithCb(username,pwd);
			break;
		}
	}


}
