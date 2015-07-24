package com.smarthome.ui.activity;

import com.smarthome.R;
import com.smarthome.presenter.LoginPresenter;
import com.smarthome.tools.LogUtil;
import com.smarthome.view.LoginView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginAty extends BaseAty implements LoginView, OnClickListener {

	private final String TAG = this.getClass().getName();
	private LoginPresenter loginPresenter;
	private EditText edtLoginPwd;
	private Button btnLogin;
	private Button btnForgetPwd;
	private EditText edtLoginVal;
	private Button btnPwdNew;
	private String mobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginPresenter = new LoginPresenter(this);
		edtLoginPwd = (EditText)findViewById(R.id.edtLoginPwd);
		edtLoginVal = (EditText)findViewById(R.id.edtLoginVal);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnForgetPwd = (Button)findViewById(R.id.btnForgetPwd);
		btnPwdNew = (Button)findViewById(R.id.btnPwdNew);
		btnLogin.setOnClickListener(this);
		btnForgetPwd.setOnClickListener(this);
		btnPwdNew.setOnClickListener(this);
		Intent intent = getIntent();
		mobile = intent.getStringExtra(AccountCheckAty.MOBILE_EXTRA_KEY);
		
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
//		Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		String username = mobile+AccountCheckAty.ACCOUNT_SUFFIX;
		String pwd = edtLoginPwd.getText().toString();
		String val = edtLoginVal.getText().toString();
		switch(v.getId()){
		case R.id.btnLogin:
			loginPresenter.login(username,pwd);
			break;
		case R.id.btnForgetPwd:
			loginPresenter.pwdLost(username);
			break;
		case R.id.btnPwdNew:
			loginPresenter.pwdNew(username, pwd, val);
			break;
		}
	}


}
