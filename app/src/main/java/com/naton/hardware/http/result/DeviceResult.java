package com.naton.hardware.http.result;

import com.naton.hardware.model.Device;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/6/30.
 */
public class DeviceResult extends ServiceResult {
    public String message;
    public int code;
    public ArrayList<Device> result_data;
}
