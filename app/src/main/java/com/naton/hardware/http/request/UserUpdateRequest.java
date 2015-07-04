package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/6/26.
 */
public class UserUpdateRequest {

    final String userId;
    final String name;
    final String email;
    final String sex;
    final String birthday;
    final String local;
    final String descript;
    final String avatar;
    final String QQ;

    public UserUpdateRequest(String userId, String name, String email, String sex,
                             String birthday, String local, String descript, String avatar, String QQ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.local = local;
        this.descript = descript;
        this.avatar = avatar;
        this.QQ = QQ;
    }


}
