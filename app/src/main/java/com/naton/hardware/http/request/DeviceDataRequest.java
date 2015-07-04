package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/7/1.
 */
public class DeviceDataRequest {
    final String deviceId;
    final String dateBegin;
    final String dateEnd;

    public DeviceDataRequest(String deviceId, String dateBegin, String dateEnd) {
        this.deviceId = deviceId;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }
}
