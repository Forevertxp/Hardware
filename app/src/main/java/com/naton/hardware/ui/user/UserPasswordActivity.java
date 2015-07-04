package com.naton.hardware.ui.user;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.service.AuthService;
import com.naton.hardware.http.service.UserService;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserPasswordActivity extends BaseActivity {

    private EditText phoneET, codeET, passET, passAgainET;
    private Button submitBtn, codeBtn;

    private String user, code, pass, repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password);
        setTitleText("找回密码");

        phoneET = (EditText) findViewById(R.id.et_name);
        codeET = (EditText) findViewById(R.id.et_code);
        passET = (EditText) findViewById(R.id.et_password);
        passAgainET = (EditText) findViewById(R.id.et_password_again);

        codeBtn = (Button) findViewById(R.id.btn_code);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneET.getText().toString())) {
                    return;
                }
                getCode();
            }
        });
        submitBtn = (Button) findViewById(R.id.btn_submit);

        LinearLayout regLL = (LinearLayout) findViewById(R.id.regLL);
        regLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(phoneET.getWindowToken(), 0);
                phoneET.clearFocus();
                return false;
            }
        });

        phoneET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                user = phoneET.getText().toString().trim();
                if (user != null && (!user.equals(""))) {

                } else {
                    passET.setText("");
                    passAgainET.setText("");
                    codeET.setText("");
                }
                changeColor();
            }
        });

        codeET.addTextChangedListener(textWatcher);
        passET.addTextChangedListener(textWatcher);
        passAgainET.addTextChangedListener(textWatcher);


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            changeColor();
        }
    };

    // 改变登陆按钮文字颜色
    public void changeColor() {
        user = phoneET.getText().toString().trim();
        pass = passET.getText().toString().trim();
        code = codeET.getText().toString().trim();
        repass = passAgainET.getText().toString().trim();
        if ((user != null) && (!user.equals("")) && (code != null) && (!code.equals(""))
                && (pass != null) && (!pass.equals("")) && (repass != null) && (!repass.equals(""))) {
            submitBtn.setClickable(true);
            submitBtn.setBackgroundResource(R.drawable.button_selector);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reMakePass(user, code, pass, repass);
                }
            });
        } else {
            submitBtn.setClickable(false);
            submitBtn.setBackgroundResource(R.drawable.btn_normal);
        }
    }

    private void getCode() {
        AuthService authService = RestManager.getInstance().create(AuthService.class);
        codeBtn.setText("获取中...");
        codeBtn.setEnabled(false);
        authService.fetchVerifyCode(phoneET.getText().toString(), "2", new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1){
                    codeBtn.setText("获取验证码");
                    codeBtn.setEnabled(false);
                    SVProgressHUD.showInViewWithoutIndicator(UserPasswordActivity.this, "验证码已成功发送至手机", 2.0f);
                }
                else{
                    codeBtn.setText("重新获取");
                    codeBtn.setEnabled(true);
                    SVProgressHUD.showInViewWithoutIndicator(UserPasswordActivity.this, result.message, 2.0f);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                codeBtn.setText("重新获取");
                codeBtn.setEnabled(true);
                SVProgressHUD.showInViewWithoutIndicator(UserPasswordActivity.this, error.getMessage(), 2.0f);
            }
        });
    }

    private void reMakePass(String user, String code, String pass, String repass) {
        SVProgressHUD.showInView(this, "请稍后...", true);
        UserService userService = RestManager.getInstance().create(UserService.class);
        userService.resetPass(user, code, pass, new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                SVProgressHUD.dismiss(UserPasswordActivity.this);
                if (result.code == 1) {
                    finish();
                } else {
                    SVProgressHUD.showInViewWithoutIndicator(UserPasswordActivity.this, result.message, 2.0f);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(UserPasswordActivity.this);
                SVProgressHUD.showInViewWithoutIndicator(UserPasswordActivity.this, "网络异常", 2.0f);
            }
        });
    }
}

