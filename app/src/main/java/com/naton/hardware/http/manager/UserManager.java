package com.naton.hardware.http.manager;

import android.widget.Toast;

import com.naton.hardware.app.NTConfig;
import com.naton.hardware.http.HttpCallBack;
import com.naton.hardware.http.request.UserSelectRequest;
import com.naton.hardware.http.request.UserUpdateRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.ServiceError;
import com.naton.hardware.http.result.UserResult;
import com.naton.hardware.http.service.UserService;
import com.naton.hardware.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15/6/8.
 */
public class UserManager {
    private static UserManager s_instance;
    private UserService userService;
    private User userInfo;
    private final static String USER_CONFIG_FILENAME = "userInfo.dat";

    private UserManager() {
        userService = RestManager.getInstance().create(UserService.class);
    }

    public static UserManager getInstance() {
        if (s_instance == null) {
            s_instance = new UserManager();
        }
        return s_instance;
    }

    public User getUserInfo() {
        return userInfo;
    }

    private UserService getUserService() {
        return userService;
    }


    /**
     * 加载本地用户信息
     */
    public User loadLocalUserInfo() {
        try {
            FileInputStream fis = NTConfig.getInstance().getContext().openFileInput(USER_CONFIG_FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object readObject = is.readObject();
            is.close();

            if (readObject != null && readObject instanceof User) {
                return (User) readObject;
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @param callback
     */
    public void fetchUserInfo(String userId, final HttpCallBack callback) {
        getUserService().fetchUserInfo(new UserSelectRequest(userId), new Callback<UserResult>() {
            @Override
            public void success(UserResult result, Response response) {
                userInfo = result.result_data;
                if (result.code == 1) {
                    callback.success();
                } else {
                    callback.failure(null);
                    showToast(result.message);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(NTConfig.getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新用户信息
     *
     * @param callback
     */
    public void updateUserInfo(String name, String email, String sex, String birthday, String local, String descript,
                               String avatar, String QQ, final HttpCallBack callback) {
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        getUserService().updateUserInfo(new UserUpdateRequest(userId, name, email, sex, birthday, local, descript, avatar, QQ), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1) {
                    callback.success();
                } else {
                    callback.failure(null);
                    showToast(result.message);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }

}
