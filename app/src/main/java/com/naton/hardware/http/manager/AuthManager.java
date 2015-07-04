package com.naton.hardware.http.manager;

import android.content.Context;
import android.widget.Toast;

import com.naton.hardware.app.NTConfig;
import com.naton.hardware.http.HttpCallBack;
import com.naton.hardware.http.request.RegRequst;
import com.naton.hardware.http.result.AccessTokenResult;
import com.naton.hardware.http.result.ServiceError;
import com.naton.hardware.http.service.AuthService;
import com.naton.hardware.model.AccessToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public class AuthManager {

    private final static String AUTH_CONFIG_FILENAME = "usermanager.dat";

    private static AuthManager s_instance;
    private AuthService _authService;
    private AccessToken _accessToken;

    private AuthManager() {
        _authService = RestManager.getInstance().create(AuthService.class);
        load();
    }

    public static AuthManager getInstance() {
        if (s_instance == null) {
            s_instance = new AuthManager();
        }

        return s_instance;
    }

    public AccessToken getAccessToken() {
        return _accessToken;
    }

    public boolean isAuthenticated() {
        return _accessToken != null;
    }

    /**
     * 用户名 密码登录
     *
     * @param username
     * @param password
     * @param callback
     */
    public void authWithUsernamePassword(String username, String password, final HttpCallBack callback) {
        _authService.login(new RegRequst(username, password), new Callback<AccessTokenResult>() {
            @Override
            public void success(AccessTokenResult result, Response response) {
                if (result == null) {
                    if (callback != null) {
                        callback.failure(new ServiceError(-1, "服务器返回数据出错"));
                    }
                    return;
                }

                if (result.error != null) {
                    if (callback != null) {
                        callback.failure(result.error);
                    }
                    return;
                }
                if(result.code == 1){
                    AccessToken token = result.result_data;
                    if (token == null) {
                        if (callback != null) {
                            callback.failure(new ServiceError(-1, result.message));
                        }
                        return;
                    }
                    // 登录成功后返回用户信息，更新本地，通用只保存token即可，这里将用户对象保存至本地
                    updateAccessToken(token);
                    callback.success();
                }else{
                    callback.failure(new ServiceError(-1, result.message));
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (callback != null) {
                    callback.failure(new ServiceError(retrofitError));
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(NTConfig.getInstance().getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void clearAccessToken() {
        _accessToken = null;
    }

    /**
     * 服务端登录返回token
     *
     * @param accessToken
     */
    public void updateAccessToken(AccessToken accessToken) {
        if (accessToken == null) {
            return;
        }

        _accessToken = accessToken;
        save();
    }

    /**
     * 本地保持token信息
     */
    private void save() {
        try {
            FileOutputStream fos = NTConfig.getInstance().getContext().openFileOutput(AUTH_CONFIG_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            if (_accessToken != null)
                oos.writeObject(_accessToken);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            FileInputStream fis = NTConfig.getInstance().getContext().openFileInput(AUTH_CONFIG_FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object readObject = is.readObject();
            is.close();

            if (readObject != null && readObject instanceof AccessToken) {
                _accessToken = (AccessToken) readObject;
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * 登出
     */
    public void logout(){
        clearAccessToken();
        try {
            FileOutputStream fos = NTConfig.getInstance().getContext().openFileOutput(
                    AUTH_CONFIG_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
