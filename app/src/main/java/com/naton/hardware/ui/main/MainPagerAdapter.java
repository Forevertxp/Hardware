package com.naton.hardware.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.naton.hardware.ui.device.DeviceFragment;
import com.naton.hardware.ui.data.DataBaseFragment;
import com.naton.hardware.ui.user.UserFragment;

/**
 * Created by tianxiaopeng on 15-1-2.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {


    private DataBaseFragment consultFragment;
    private DeviceFragment channelFragment;
    private UserFragment userFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        construct();
    }

    private void construct() {
        consultFragment = new DataBaseFragment();
        channelFragment = new DeviceFragment();
        userFragment = new UserFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return consultFragment;
            case 1:
                return channelFragment;
            case 2:
                return userFragment;
            default:
                return null;
        }
    }

}

