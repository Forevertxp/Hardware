package com.naton.hardware;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.model.AccessToken;
import com.naton.hardware.ui.main.MainActivity;
import com.naton.hardware.ui.main_no_tab.NewMainActivity;
import com.naton.hardware.ui.user.LoginActivity;


public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!AuthManager.getInstance().isAuthenticated()){
                    Intent loginIntent = new Intent(IndexActivity.this,
                            LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(IndexActivity.this,
                            NewMainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }


            }

        }, 500);
    }
}
