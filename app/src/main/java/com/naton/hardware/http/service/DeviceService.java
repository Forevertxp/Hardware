package com.naton.hardware.http.service;


import com.naton.hardware.http.request.DeviceControlRequest;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.request.ImageRequest;
import com.naton.hardware.http.request.PicDelRequest;
import com.naton.hardware.http.request.ProgramRequest;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.DeviceInfoResult;
import com.naton.hardware.http.result.DeviceResult;
import com.naton.hardware.http.result.DeviceStatusResult;
import com.naton.hardware.http.result.ImageResult;
import com.naton.hardware.http.result.PicResult;
import com.naton.hardware.http.result.ProgramResult;

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

    /**
     * 获取设备程序列表
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/program/list.app")
    public void listProgram(@Body DeviceRequest body,
                            Callback<ProgramResult> cb);

    /**
     * 设备绑定程序
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/program/bind.app")
    public void bindProgram(@Body ProgramRequest body,
                            Callback<CommonResult> cb);

    /**
     * 获取设备自动控制状态
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/status.app")
    public void getDeviceStatus(@Body DeviceRequest body,
                                Callback<DeviceStatusResult> cb);


    /**
     * 手动控制设备状态
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/ctl.app")
    public void changeDeviceStatus(@Body DeviceControlRequest body,
                                   Callback<CommonResult> cb);

    /**
     * 设备快照
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/takePic.app")
    public void takePic(@Body DeviceRequest body,
                        Callback<PicResult> cb);

    /**
     * 设备所有快照图片
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/images.app")
    public void fetchImageList(@Body ImageRequest body,
                               Callback<ImageResult> cb);

    /**
     * 设备所有快照图片
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/device/delImage.app")
    public void delImage(@Body PicDelRequest body,
                               Callback<CommonResult> cb);
}
