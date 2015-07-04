package com.naton.hardware.http.service;

import com.naton.hardware.http.request.ModifyPassRequest;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.request.UserUpdateRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.UserResult;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public interface UserService {


    /**
     * 查询用户信息
     *
     * @param cb
     */
    @POST("/jadmin/user/select.app")
    public void fetchUserInfo(@Body UserSelectRequest body,
                              Callback<UserResult> cb);

    /**
     * 重置密码
     *
     * @param mobile
     * @param cb
     */
    @FormUrlEncoded
    @POST("/SD/user/resetPwd")
    public void resetPass(@Field("mobile") String mobile,
                          @Field("identityCode") String identityCode,
                          @Field("newPwd") String newPwd,
                          Callback<CommonResult> cb);

    /**
     * 修改密码
     *
     * @param cb
     */
    @POST("/jadmin/user/changePwd.app")
    public void modiftyPass(@Body ModifyPassRequest body,
                            Callback<CommonResult> cb);

    /**
     * 更新用户信息
     *
     * @param body
     */
    @POST("/jadmin/user/update.app")
    public void updateUserInfo(@Body UserUpdateRequest body,
                               Callback<CommonResult> cb);

//    /**
//     * 查询地区信息
//     *
//     * @param cb
//     */
//    @FormUrlEncoded
//    @POST("/SD/common/areaSelect")
//    public void fetchAreaInfo(@Field("code") String code,
//                              @Field("level") int level,
//                              Callback<AreaResult> cb);

}
