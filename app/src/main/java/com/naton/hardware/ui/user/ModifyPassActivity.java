package com.naton.hardware.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.ModifyPassRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.service.UserService;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ModifyPassActivity extends BaseActivity {

    private EditText oldPassEdit, newPassEdit, newPassAgain;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass);
        setTitleText("修改密码");

        oldPassEdit = (EditText) findViewById(R.id.pass_old);
        newPassEdit = (EditText) findViewById(R.id.pass_new);
        newPassAgain = (EditText) findViewById(R.id.pass_new_again);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPass();
            }
        });
    }

    private void modifyPass() {
        if (TextUtils.isEmpty(oldPassEdit.getText().toString())) {
            SVProgressHUD.showInViewWithoutIndicator(ModifyPassActivity.this, "请输入原始密码", 2.0f);
            return;
        }
        if (!newPassEdit.getText().toString().equals(newPassAgain.getText().toString())) {
            SVProgressHUD.showInViewWithoutIndicator(ModifyPassActivity.this, "两次输入的密码不一致", 2.0f);
            return;
        }
        UserService userService = RestManager.getInstance().create(UserService.class);
        userService.modiftyPass(new ModifyPassRequest(AuthManager.getInstance().getAccessToken().getUserId(),
                oldPassEdit.getText().toString(), newPassEdit.getText().toString()), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1)
                    finish();
                else
                    SVProgressHUD.showInViewWithoutIndicator(ModifyPassActivity.this, result.message, 2.0f);
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.showInViewWithoutIndicator(ModifyPassActivity.this, error.getMessage(), 2.0f);
            }
        });
    }

}
