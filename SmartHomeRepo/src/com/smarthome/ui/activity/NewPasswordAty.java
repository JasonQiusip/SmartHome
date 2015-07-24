package com.smarthome.ui.activity;

import com.smarthome.R;
import com.smarthome.presenter.LoginPresenter;
import com.smarthome.presenter.NewPasswordPresenter;
import com.smarthome.view.NewPasswordView;
import com.smarthome.view.common.Constants.PwdNewState;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewPasswordAty extends BaseAty implements OnClickListener, NewPasswordView{

	private final String TAG = this.getClass().getName();
	private NewPasswordPresenter newPasswordPresenter;
	private String mobile;
	private EditText edtNewPwdAtyPwd;
	private EditText edtNewPwdVal;
	private Button btnReqValCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newpwd);
		edtNewPwdAtyPwd = (EditText) findViewById(R.id.edtNewPwdAtyPwd);
		edtNewPwdVal = (EditText) findViewById(R.id.edtNewPwdVal);
		btnReqValCode = (Button) findViewById(R.id.btnReqValCode);
		
		Intent intent = getIntent();
		mobile = intent.getStringExtra(AccountCheckAty.MOBILE_EXTRA_KEY);
		newPasswordPresenter = new NewPasswordPresenter(this);
		newPasswordPresenter.pwdLost(mobile+AccountCheckAty.ACCOUNT_SUFFIX);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnReqValCode:
			String val = edtNewPwdVal.getText().toString();
			String pwd = edtNewPwdAtyPwd.getText().toString();
			newPasswordPresenter.pwdNew(mobile+AccountCheckAty.ACCOUNT_SUFFIX, pwd, val);
			break;
		}
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void onReqPwdLostSuccess(String response) {
	}

	@Override
	public void onReqPwdNewSuccess(String response) {
		if(PwdNewState.SUCCESS.equals(response)){
			showSnackBar("密码修改成功");
		}else if(PwdNewState.ACCOUNT_MISS.equals(response)){
			showSnackBar("账号缺失");
		}else if(PwdNewState.PWD_MISS.equals(response)){
			showSnackBar("密码缺失");
		}else if(PwdNewState.NO_VAL.equals(response)){
			showSnackBar("服务器未生成验证码");
		}else if(PwdNewState.VAL_CODE_ERROR.equals(response)){
			showSnackBar("验证码错误");
		}else{
			showSnackBar("网络异常");
		}
	}

	@Override
	public void onError() {
		
	}

}
