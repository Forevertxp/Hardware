package com.naton.hardware.model;

import java.io.Serializable;

public class AccessToken implements Serializable
{
    private String userId;
    private String token;
    private String tokenendtime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenendtime() {
        return tokenendtime;
    }

    public void setTokenendtime(String tokenendtime) {
        this.tokenendtime = tokenendtime;
    }
}
