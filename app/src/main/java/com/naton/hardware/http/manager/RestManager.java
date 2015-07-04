package com.naton.hardware.http.manager;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.naton.hardware.app.NTConfig;
import com.naton.hardware.model.AccessToken;

import java.util.Date;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public class RestManager {


    private static RestManager s_instance;
    private RestAdapter _restAdapter;

    private RestManager() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError retrofitError) {
                if (retrofitError != null && retrofitError.getMessage() != null) {
                    Log.e("retrofit", retrofitError.getMessage());
                }
                return retrofitError;
            }
        };

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2141.0 Safari/537.36");
                request.addHeader("Content-type", "application/json");

                AccessToken accessToken = AuthManager.getInstance().getAccessToken();
                if (accessToken != null) {
                    String tokenValue = accessToken.getToken();
                    if (tokenValue != null) {
                        request.addHeader("Authorization", "Bearer " + tokenValue);
                    }
                }
            }
        };

        _restAdapter = new RestAdapter.Builder()
                .setEndpoint(NTConfig.getInstance().getAPIBaseURL())
                        //                           .setClient(client)
                .setErrorHandler(errorHandler)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    public static RestManager getInstance() {
        if (s_instance == null) {
            s_instance = new RestManager();
        }

        return s_instance;
    }

    public <T> T create(Class<T> service) {
        return _restAdapter.create(service);
    }
}

