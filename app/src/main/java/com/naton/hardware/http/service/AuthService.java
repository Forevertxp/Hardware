package com.naton.hardware.http.service;

import com.naton.hardware.http.request.RegRequst;
import com.naton.hardware.http.result.AccessTokenResult;
import com.naton.hardware.http.result.CommonResult;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public interface AuthService {


    /**
     * 获取验证码
     *
     * @param mobile
     * @param type   短信验证类别（1-注册/2-找回密码
     * @param cb
     */
    @FormUrlEncoded
    @POST("/SD/user/identityCodeValidate")
    public void fetchVerifyCode(@Field("mobile") String mobile,
                                @Field("type") String type,
                                Callback<CommonResult> cb);

    /**
     * 注册
     *
     * @param body //json串
     * @param cb
     */
    @POST("/jadmin/user/regist.app")
    public void registUser(@Body RegRequst body,
                           Callback<CommonResult> cb);


    /**
     * 用户名密码登录
     *
     * @param body
     * @param cb
     */
    @POST("/jadmin/user/login.app")
    public void login(@Body RegRequst body,
                      Callback<AccessTokenResult> cb);
}

