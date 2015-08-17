package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class ImageRequest {

    final String deviceId;
    final String dateBegin;
    final String dateEnd;
    final int start;
    final int limit;


    public ImageRequest(String deviceId, String dateBegin, String dateEnd, int start, int limit) {
        this.deviceId = deviceId;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.start = start;
        this.limit = limit;
    }
}
