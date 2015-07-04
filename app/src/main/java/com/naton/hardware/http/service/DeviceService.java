package com.naton.hardware.http.service;


import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.DeviceInfoResult;
import com.naton.hardware.http.result.DeviceResult;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 15-1-17.
 */
public interface DeviceService {

    /**
     * 获取当前用户设备
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/listAllDevice.app")
    public void listAllDevice(@Body UserSelectRequest body,
                      Callback<DeviceResult> cb);

    /**
     * 设备信息
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/detail.app")
    public void fetchDeviceInfo(@Body DeviceRequest body,
                          Callback<DeviceInfoResult> cb);

    /**
     * 添加设备
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/add.app")
    public void addDevice(@Body DeviceRequest body,
                              Callback<CommonResult> cb);

    /**
     * 删除设备
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/delete.app")
    public void deleteDevice(@Body DeviceRequest body,
                          Callback<CommonResult> cb);

}
