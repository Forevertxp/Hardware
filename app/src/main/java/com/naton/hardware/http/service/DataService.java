package com.naton.hardware.http.service;

import com.naton.hardware.http.request.DeviceDataRequest;
import com.naton.hardware.http.result.DataResult;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 15/7/1.
 */
public interface DataService {

    /**
     * 查询用户信息
     *
     * @param cb
     */
    @POST("/jadmin/device/data.app")
    public void fetchDeviceData(@Body DeviceDataRequest body,
                              Callback<DataResult> cb);
}
