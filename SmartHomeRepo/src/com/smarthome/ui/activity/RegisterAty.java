package com.smarthome.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.smarthome.R;
import com.smarthome.presenter.RegisterPresenter;
import com.smarthome.presenter.view.RegisterView;
import com.smarthome.tools.MiscUtil;
import com.smarthome.view.common.Constants.RegisterState;
import com.smarthome.view.common.Constants.ReqValCodeState;

public class RegisterAty extends BaseAty implements RegisterView, OnClickListener{


	private EditText edtValCode;
	private EditText edtPwd;

	private Button btnReqRegCode;
	private Button btnReg;
	private Button btnGetCode;
	private RegisterPresenter registerPresenter;
	private String val;
	private String mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		edtValCode = (EditText) findViewById(R.id.edtValCode);
		edtPwd = (EditText) findViewById(R.id.edtPwd);
		btnReqRegCode = (Button) findViewById(R.id.btnReqValCode);
		btnReg = (Button) findViewById(R.id.btnReg);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		registerPresenter = new RegisterPresenter(this);
		btnReqRegCode.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnGetCode.setOnClickListener(this);
		Intent intent = getIntent();
		mobile = intent.getStringExtra(AccountCheckAty.MOBILE_EXTRA_KEY);
		
	}
	
	private void startLoginAty() {
		Intent intent = new Intent();
		intent.setClass(this, LoginAty.class);
		intent.putExtra(AccountCheckAty.MOBILE_EXTRA_KEY, mobile);
		startActivity(intent);
	}
	
	public void reqValCode(){
		registerPresenter.reqRegValCode(mobile+AccountCheckAty.ACCOUNT_SUFFIX);
	}

	public void getValCode(){
		registerPresenter.getValCode(MiscUtil.getNumber(mobile));
	}
	
	public void finishReg(){
		registerPresenter.finishReg(mobile+AccountCheckAty.ACCOUNT_SUFFIX, 
				edtPwd.getText().toString(), val);
	}
	
	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void onReqRegSuccess(String response) {
		edtValCode.setText(response);
		 if(ReqValCodeState.ACCOUNT_FORMAT_ERROR.equals(response)){
			 showSnackBar(getString(R.string.account_format_error));
		 }else if(ReqValCodeState.ACCOUNT_NOT_MOBILE.equals(response)){
			 showSnackBar(getString(R.string.account_not_mobile));
		 }
		
	}

	@Override
	public void onFinishRegSuccess(String response) {
		edtValCode.setText(response);
		if(RegisterState.SUCCESS.equals(response)){
			startLoginAty();
			finish();
		}else if(RegisterState.ACCOUNT_FORMAT_ERROR.equals(response)){
			showSnackBar(getString(R.string.account_format_error));
		}else if(RegisterState.ACCOUNT_NOT_MOBILE.equals(response)){
			showSnackBar(getString(R.string.account_not_mobile));
		}else if(RegisterState.VAL_CODE_FORMAT_ERROR.equals(response)){
			showSnackBar(getString(R.string.val_code_format_error));
		}else if(RegisterState.VAL_CODE_INVALID_OR_NOT_EXIST.equals(response)){
			showSnackBar(getString(R.string.val_code_empty_or_error));
		}else if(RegisterState.ACCOUNT_EXIST.equals(response)){
			showSnackBar(getString(R.string.account_exist));
		}
	}

	@Override
	public void onGetValCode(String response){
		this.val = response;
		edtValCode.setText(response);
	}


	@Override
	public void onError(String errorMsg) {
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnReqValCode:
			reqValCode();
			break;
		case R.id.btnReg:
			finishReg();
			break;
		case R.id.btnGetCode:
			getValCode();
			break;
		
		}
	}

}
