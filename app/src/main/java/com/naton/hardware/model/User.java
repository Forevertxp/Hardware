package com.naton.hardware.model;


import java.io.Serializable;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class User implements Serializable {

    private String userId;
    private String name;
    private String email;
    private String sex;
    private String birthday;
    private String local;
    private String descript;
    private String avatar;
    private String wx;
    private String wb;
    private String QQ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocal() {
        return local;
    }

    public String getDescript() {
        return descript;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getSex() {
        return sex;
    }
}

