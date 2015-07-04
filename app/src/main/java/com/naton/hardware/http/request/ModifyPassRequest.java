package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/6/26.
 */
public class ModifyPassRequest {

    final String userId;
    final String oldPwd;
    final String newPwd;

    public ModifyPassRequest(String userId, String oldPwd, String newPwd) {
        this.userId = userId;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }
}
