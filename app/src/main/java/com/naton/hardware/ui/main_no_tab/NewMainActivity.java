package com.naton.hardware.ui.main_no_tab;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.result.DeviceResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Device;
import com.naton.hardware.ui.data.DataFragment;
import com.naton.hardware.ui.device.DeviceActivity;
import com.naton.hardware.ui.user.LoginActivity;
import com.naton.hardware.ui.user.UserEditActivity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewMainActivity extends BaseActivity {


    private DataPagerAdapter mAdapter;
    private ViewPager mPager;

    private ArrayList<Device> deviceList = new ArrayList<Device>();
    private boolean isRegister = false;

    private static int USER_EDIT_CODE = 0x11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        setTitleText("设备");
        setRightImage(R.drawable.ic_list);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });

        setLeftImage(R.drawable.ic_user_white);
        setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this, UserEditActivity.class);
                startActivityForResult(intent, USER_EDIT_CODE);
            }
        });


        mPager = (ViewPager) findViewById(R.id.pager);

        register();

        if (AuthManager.getInstance().isAuthenticated()) {
            initDeviceList(AuthManager.getInstance().getAccessToken().getUserId());
        } else {
            mAdapter = new DataPagerAdapter(getFragmentManager(), 1);
            mPager.setAdapter(mAdapter);
        }
    }


    private BroadcastReceiver deviceBroidcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (deviceList != null)
                deviceList.clear();
            if (AuthManager.getInstance().isAuthenticated()) {
                initDeviceList(AuthManager.getInstance().getAccessToken().getUserId());
            } else {
                mAdapter = new DataPagerAdapter(getFragmentManager(), 1);
                mPager.setAdapter(mAdapter);
            }
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

    public void initDeviceList(String userId) {
        final DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.listAllDevice(new UserSelectRequest(userId), new Callback<DeviceResult>() {
            @Override
            public void success(DeviceResult result, Response response) {
                if (result.code == 1) {
                    deviceList = result.result_data;
                    mAdapter = new DataPagerAdapter(getFragmentManager(), deviceList.size() > 0 ? deviceList.size() : 1);
                    mPager.setAdapter(mAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_EDIT_CODE && resultCode == RESULT_OK) {
            Intent intent = new Intent(NewMainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }

    public void updateFragment(int type) {
        mPager.setCurrentItem(type);
    }


    private class DataPagerAdapter extends FragmentStatePagerAdapter {

        private int mCount;

        public DataPagerAdapter(FragmentManager fm, int pagerNum) {
            super(fm);
            this.mCount = pagerNum;
        }

        @Override
        public Fragment getItem(int position) {
            if (deviceList.size() > 0) {
                return DataFragment.newInstance(deviceList.get(position).getDeviceId());
            } else {
                return DataFragment.newInstance("123");
            }
        }

        @Override
        public int getCount() {
            return mCount;
        }

    }

}
