package com.naton.hardware.ui.data;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.result.DeviceResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Device;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-26.
 */


public class DataBaseFragment extends Fragment {
    private DataPagerAdapter mAdapter;
    private ViewPager mPager;

    private ArrayList<Device> deviceList = new ArrayList<Device>();
    private boolean isRegister = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_data_base, container, false);
        mPager = (ViewPager) v.findViewById(R.id.pager);

        register();

        if (AuthManager.getInstance().isAuthenticated()) {
            initDeviceList(AuthManager.getInstance().getAccessToken().getUserId());
        } else {
            mAdapter = new DataPagerAdapter(getFragmentManager(), 1);
            mPager.setAdapter(mAdapter);
        }

        return v;
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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