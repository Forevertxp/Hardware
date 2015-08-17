package com.naton.hardware.model;

import java.io.Serializable;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class DeviceStatus implements Serializable {

    private int status0; //连接状态，（1：设备开启，0：设备关闭）
    private int status1;//喷雾状态，（1：打开，0：关闭）
    private int status2;//加热状态，（1：打开，0：关闭）
    private int status3;//灯光状态，（1：打开，0：关闭）
    private int status4;//手自动开关状态，（1：手动，0：自动）
    private int status5;//风扇状态，（1：打开，0：关闭）

    public int getStatus0() {
        return status0;
    }

    public int getStatus1() {
        return status1;
    }

    public int getStatus2() {
        return status2;
    }

    public int getStatus3() {
        return status3;
    }

    public int getStatus4() {
        return status4;
    }

    public int getStatus5() {
        return status5;
    }
}
