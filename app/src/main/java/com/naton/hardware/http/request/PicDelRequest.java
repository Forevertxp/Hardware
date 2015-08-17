package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/8/16.
 */
public class PicDelRequest {
    final String deviceId;
    final String url;

    public PicDelRequest(String deviceId, String url) {
        this.deviceId = deviceId;
        this.url = url;
    }
}
