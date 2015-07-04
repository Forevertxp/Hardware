package com.naton.hardware.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.RegRequst;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.service.AuthService;
import com.naton.hardware.util.Utils;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends BaseActivity {

    private Button codeBtn, regBtn;

    private EditText phoneEdit, codeEdit, passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitleText("注 册");

        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        codeEdit = (EditText) findViewById(R.id.codeEdit);
        passEdit = (EditText) findViewById(R.id.passEdit);

        codeBtn = (Button) findViewById(R.id.codeBtn);
        regBtn = (Button) findViewById(R.id.regBtn);

        final AuthService authService = RestManager.getInstance().create(AuthService.class);

        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneEdit.getText().toString().equals("")) {
                    SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "请输入正确的手机号", 2.0f);
                    return;
                }
                codeBtn.setEnabled(false);
                codeBtn.setText("获取中...");
                authService.fetchVerifyCode(phoneEdit.getText().toString(), "1", new Callback<CommonResult>() {
                    @Override
                    public void success(CommonResult result, Response response) {
                        SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "验证码已成功发送至手机", 2.0f);
                        codeBtn.setEnabled(true);
                        codeBtn.setText("获取验证码");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "验证码获取失败", 2.0f);
                        codeBtn.setEnabled(true);
                        codeBtn.setText("重新获取");
                    }
                });
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isMobileNO(phoneEdit.getText().toString())) {
                    SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "请输入正确的手机号", 2.0f);
                    return;
                }
//                if (codeEdit.getText().toString().equals("")) {
//                    SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "请输入验证码", 2.0f);
//                    return;
//                }
                if (passEdit.getText().toString().equals("")) {
                    SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, "请输入密码", 2.0f);
                    return;
                }
                authService.registUser(new RegRequst(phoneEdit.getText().toString(), codeEdit.getText().toString()), new Callback<CommonResult>() {
                            @Override
                            public void success(CommonResult result, Response response) {
                                if (result.code == 1)
                                    new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("提示")
                                            .setMessage("注册成功")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = getIntent();
                                                    intent.putExtra("username", phoneEdit.getText().toString());
                                                    intent.putExtra("password", passEdit.getText().toString());
                                                    setResult(Activity.RESULT_OK, intent);
                                                    finish();
                                                }
                                            })
                                            .show();
                                else
                                    SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, result.message, 2.0f);

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                SVProgressHUD.showInViewWithoutIndicator(RegisterActivity.this, error.getMessage(), 2.0f);
                            }
                        }
                );

            }
        });
    }


}
