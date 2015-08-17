package com.naton.hardware.model;

import java.io.Serializable;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class Program implements Serializable {

    private String programId;
    private String programName;
    private int bind; // 0未绑定 1 绑定

    public String getProgramId() {
        return programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return bind;
    }
}
