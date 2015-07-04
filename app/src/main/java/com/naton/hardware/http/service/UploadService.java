package com.naton.hardware.http.service;

import com.naton.hardware.http.result.CommonResult;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Administrator on 2015/3/26.
 */
public interface UploadService {

    @Multipart
    @POST("/SDpic/common/picUpload")
    public void uploadImage(@Part("imgData") TypedFile imgData,
                            @Part("imgAngle") int imgAngle,
                            @Part("width") int width,
                            @Part("height") int height,
                            @Part("picType") String picType,
                            Callback<CommonResult> cb);
}
