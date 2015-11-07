package com.naton.hardware.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naton.hardware.R;
import com.naton.hardware.app.NTConfig;
import com.naton.hardware.http.HttpCallBack;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.UserManager;
import com.naton.hardware.http.result.ServiceError;
import com.naton.hardware.model.AccessToken;
import com.naton.hardware.model.User;
import com.naton.hardware.ui.main_no_tab.NewMainActivity;
import com.naton.hardware.util.Utils;
import com.snail.svprogresshud.SVProgressHUD;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView backIV;
    private TextView regTV;
    private EditText userText, passText;
    private Button loginBtn;
    private TextView findPassBtn;
    private RelativeLayout regRL;

    private static int USER_REG = 0x0100;
    private final static String USER_CONFIG_FILENAME = "userInfo.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        backIV = (ImageView) findViewById(R.id.backIV);
        regTV = (TextView) findViewById(R.id.regText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        findPassBtn = (TextView) findViewById(R.id.findPassBtn);
        userText = (EditText) findViewById(R.id.usernameEdit);
        passText = (EditText) findViewById(R.id.passEdit);
        regRL = (RelativeLayout)findViewById(R.id.regRL);
        backIV.setOnClickListener(this);
        regTV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        findPassBtn.setOnClickListener(this);
        regRL.setOnClickListener(this);

        userText.addTextChangedListener(new TextWatcher() {

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
                String user = userText.getText().toString().trim();
                if (user != null && (!user.equals(""))) {

                } else {
                    passText.setText("");
                }
            }
        });

        // 点击屏幕其他地方，隐藏输入法
        RelativeLayout mainRL = (RelativeLayout) findViewById(R.id.main);
        mainRL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(passText.getWindowToken(), 0);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backIV:
                finish();
                break;
            case R.id.regRL:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, USER_REG);
                break;
            case R.id.loginBtn:
                String username = userText.getText().toString();
                String password = passText.getText().toString();
                if (!Utils.isMobileNO(username)) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                login(username, password);
                break;
            case R.id.findPassBtn:
                Intent passIntent = new Intent(this, UserPasswordActivity.class);
                startActivity(passIntent);
                break;
        }

    }

    private void login(String username, String password) {
        AuthManager.getInstance().authWithUsernamePassword(username, password, new HttpCallBack() {
            @Override
            public void success() {
//                Intent intent = new Intent("com.xpown.device.changed");
//                sendBroadcast(intent);
//                setResult(RESULT_OK);
//                finish();
                // 获取用户详情
                if (AuthManager.getInstance().isAuthenticated()){
                    AccessToken token = AuthManager.getInstance().getAccessToken();
                    fetchUserInfo(token.getUserId());
                }

                Intent intent = new Intent(LoginActivity.this, NewMainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(ServiceError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询用户基本信息
     */
    private void fetchUserInfo(String userId) {
        final UserManager userManager = UserManager.getInstance();
        userManager.fetchUserInfo(userId, new HttpCallBack() {
            @Override
            public void success() {
                User userInfo = userManager.getUserInfo();
                // 保存在本地
                save(userInfo);
            }

            @Override
            public void failure(ServiceError error) {

            }
        });
    }

    /**
     * 本地保持userInfo信息
     */
    private void save(User userInfo) {
        try {
            FileOutputStream fos = NTConfig.getInstance().getContext().openFileOutput(USER_CONFIG_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            if (userInfo != null)
                oos.writeObject(userInfo);
            oos.close();
            //发广播通知更新
            //NTAPP.getAppContext().sendBroadcast(new Intent(MainActivity.BROADCAST_ACTION_LOGIN_SUCCESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册后登录
        if (requestCode == USER_REG && resultCode == Activity.RESULT_OK) {
            String phone = data.getExtras().getString("username");
            String password = data.getExtras().getString("password");
            SVProgressHUD.showInView(LoginActivity.this, "登录中...", false);
            AuthManager.getInstance().authWithUsernamePassword(phone, password, new HttpCallBack() {
                @Override
                public void success() {
                    SVProgressHUD.dismiss(LoginActivity.this);
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void failure(ServiceError error) {
                    SVProgressHUD.dismiss(LoginActivity.this);
                }
            });
        }
    }
}
