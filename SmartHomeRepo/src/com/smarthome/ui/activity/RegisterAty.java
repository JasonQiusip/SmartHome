package com.smarthomeloginreg.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import butterknife.InjectView;
import butterknife.OnClick;

import com.smarthomeloginreg.R;
import com.smarthomeloginreg.presenter.RegisterPresenter;
import com.smarthomeloginreg.tools.MiscUtil;
import com.smarthomeloginreg.view.RegisterView;

public class RegisterAty extends BaseAty implements RegisterView, OnClickListener{

	private EditText edtMobile;
	private EditText edtValCode;
	private EditText edtPwd;
	private Button btnReqReg;
	private Button btnReqRegCode;
	private Button btnReg;
	private Button btnGetCode;
	private Button btnLoginTest;
	private RegisterPresenter registerPresenter;
	private String val;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		edtMobile = (EditText) findViewById(R.id.edtMobile);
		edtValCode = (EditText) findViewById(R.id.edtValCode);
		edtPwd = (EditText) findViewById(R.id.edtPwd);
		
		btnReqReg = (Button) findViewById(R.id.btnReqReg);
		btnReqRegCode = (Button) findViewById(R.id.btnReqValCode);
		btnReg = (Button) findViewById(R.id.btnReg);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnLoginTest = (Button) findViewById(R.id.btnLoginTest);
		registerPresenter = new RegisterPresenter(this);
		btnReqReg.setOnClickListener(this);
		btnReqRegCode.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		btnGetCode.setOnClickListener(this);
		btnLoginTest.setOnClickListener(this);
	}
	
	public void reqReg(){
		registerPresenter.startRegister(edtMobile.getText().toString());
	}
	
	public void reqValCode(){
		registerPresenter.reqRegValCode(edtMobile.getText().toString());
	}

	public void getValCode(){
		registerPresenter.getValCode(MiscUtil.getNumber(edtMobile.getText().toString()));
	}
	
	public void finishReg(){
		registerPresenter.finishReg(edtMobile.getText().toString(), 
				edtPwd.getText().toString(), val);
	}
	
	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void reqRegSuccess(String response) {
		edtValCode.setText(response);
	}
	
	@Override
	public void onGetValCode(String response){
		this.val = response;
		edtValCode.setText(response);
	}

	private void startLoginAty(){
		Intent intent = new Intent();
		intent.setClass(this, LoginAty.class);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnReqReg:
			reqReg();
			break;
		case R.id.btnReqValCode:
			reqValCode();
			break;
		case R.id.btnReg:
			finishReg();
			break;
		case R.id.btnGetCode:
			getValCode();
			break;
		case R.id.btnLoginTest:
			startLoginAty();
			break;
		
		}
	}

}
