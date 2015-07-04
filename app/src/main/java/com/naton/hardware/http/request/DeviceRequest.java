package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/7/1.
 */
public class DeviceRequest {
    final String userId;
    final String deviceId;

    public DeviceRequest(String userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }
}
