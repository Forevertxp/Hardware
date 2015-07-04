package com.naton.hardware;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.naton.hardware.ui.main.MainActivity;


public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(IndexActivity.this,
                        MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, 500);
    }
}
