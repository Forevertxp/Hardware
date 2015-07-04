package com.naton.hardware.ui.device;


import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class DeviceFragment extends Fragment {


    private PullToRefreshListView deviceListView;
    private DeviceAdapter adapter;
    private ArrayList<Device> deviceList = new ArrayList<Device>();

    private MenuItem photoMenuItem;
    private final static int DEVICE_ADD = 0x9999;

    private boolean isRegister = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mMainView = inflater.inflate(R.layout.fragment_device, container, false);
        deviceListView = (PullToRefreshListView) mMainView.findViewById(R.id.channelListView);

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
                Intent intent = new Intent(getActivity(), DeviceDetailActivity.class);
                if (device == null)
                    return;
                intent.putExtra("deviceId", device.getDeviceId());
                startActivityForResult(intent, 0X1010);
            }
        });

        return mMainView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        photoMenuItem = menu.add(Menu.NONE, DEVICE_ADD, 2, "相册");
        photoMenuItem.setIcon(R.drawable.ic_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == DEVICE_ADD) {
            AuthManager authManager = AuthManager.getInstance();
            if (authManager.isAuthenticated()) {
                Intent intent = new Intent(getActivity(), DeviceAddActivity.class);
                startActivityForResult(intent, 0X1111);
            } else {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            if (deviceList!=null)
                deviceList.clear();
            adapter = new DeviceAdapter(getActivity(), deviceList);
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
                    adapter = new DeviceAdapter(getActivity(), deviceList);
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

    private void register(){
        if (!isRegister){
            IntentFilter intentFilter = new IntentFilter("com.xpown.device.changed");
            getActivity().registerReceiver(deviceBroidcast,intentFilter);
            isRegister = true;
        }
    }

    private void unregister(){
        if (isRegister){
            getActivity().unregisterReceiver(deviceBroidcast);
            isRegister = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }
}


