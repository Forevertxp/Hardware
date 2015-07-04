package com.naton.hardware.ui.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.service.DeviceService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeviceAddActivity extends BaseActivity {

    private Button addBtn;
    private EditText deviceET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        setTitleText("添加设备");

        deviceET = (EditText) findViewById(R.id.deviceET);

        addBtn = (Button) findViewById(R.id.addDevice);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(deviceET.getText().toString())) {
                    addDevice(deviceET.getText().toString());
                }
            }
        });
    }

    private void addDevice(String deviceId) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        deviceService.addDevice(new DeviceRequest(userId, deviceId), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1) {
                    Intent intent = new Intent("com.xpown.device.changed");
                    sendBroadcast(intent);
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

}
