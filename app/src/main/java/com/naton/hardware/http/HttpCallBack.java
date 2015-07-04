package com.naton.hardware.http;


import com.naton.hardware.http.result.ServiceError;

/**
 * Created by tianxiaopeng on 15-1-5.
 */
public interface HttpCallBack {
    public void success();

    public void failure(ServiceError error);
}

