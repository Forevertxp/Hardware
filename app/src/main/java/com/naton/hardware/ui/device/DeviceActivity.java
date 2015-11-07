package com.naton.hardware.ui.device;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.result.DeviceResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Device;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeviceActivity extends BaseActivity {


    private PullToRefreshListView deviceListView;
    private DeviceAdapter adapter;
    private ArrayList<Device> deviceList = new ArrayList<Device>();

    private MenuItem photoMenuItem;
    private final static int DEVICE_ADD = 0x9999;

    private boolean isRegister = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        setTitleText("设备列表");

        setRightImage(R.drawable.ic_add);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthManager authManager = AuthManager.getInstance();
                if (authManager.isAuthenticated()) {
                    Intent intent = new Intent(DeviceActivity.this, DeviceAddActivity.class);
                    startActivityForResult(intent, 0X1111);
                } else {
                    Toast.makeText(DeviceActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                }
            }
        });

        deviceListView = (PullToRefreshListView) findViewById(R.id.channelListView);
        deviceListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        initDeviceList();

        register();

        // 下拉刷新 及 加载更多
        deviceListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initDeviceList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device device = (Device) adapter.getItem(position - 1);
                Intent intent = new Intent(DeviceActivity.this, DeviceDetailActivity.class);
                if (device == null)
                    return;
                intent.putExtra("deviceId", device.getDeviceId());
                startActivityForResult(intent, 0X1010);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 通过广播实现刷新
            // initDeviceList();
        }
    }

    public void initDeviceList() {
        if (!AuthManager.getInstance().isAuthenticated()) {
            if (deviceList != null)
                deviceList.clear();
            adapter = new DeviceAdapter(this, deviceList);
            deviceListView.onRefreshComplete();
            return;
        }
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.listAllDevice(new UserSelectRequest(userId), new Callback<DeviceResult>() {
            @Override
            public void success(DeviceResult result, Response response) {
                if (result.code == 1) {
                    if (deviceList != null)
                        deviceList.clear();
                    deviceList = result.result_data;
                    adapter = new DeviceAdapter(DeviceActivity.this, deviceList);
                    deviceListView.setAdapter(adapter);
                }
                deviceListView.onRefreshComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                deviceListView.onRefreshComplete();
            }
        });
    }

    private BroadcastReceiver deviceBroidcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initDeviceList();
        }
    };

    private void register() {
        if (!isRegister) {
            IntentFilter intentFilter = new IntentFilter("com.xpown.device.changed");
            registerReceiver(deviceBroidcast, intentFilter);
            isRegister = true;
        }
    }

    private void unregister() {
        if (isRegister) {
            unregisterReceiver(deviceBroidcast);
            isRegister = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }
}



