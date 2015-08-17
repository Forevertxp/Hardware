package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class DeviceControlRequest {
    final String deviceId;
    final int cmd; //(1:喷雾，2：加热：3：灯光：4：手自动：5：风扇)
    final int status;


    public DeviceControlRequest(String deviceId, int cmd, int status) {
        this.deviceId = deviceId;
        this.cmd = cmd;
        this.status = status;
    }
}
