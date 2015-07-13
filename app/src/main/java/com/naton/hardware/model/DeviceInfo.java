package com.naton.hardware.model;

import com.naton.hardware.http.result.ServiceResult;

/**
 * Created by tianxiaopeng on 15/7/4.
 */
public class DeviceInfo extends ServiceResult {
    private String image;
    private String temperate;
    private String humedity;
    private String lightStatue;
    private String dustStatue;

    public String getImage() {
        return image;
    }

    public String getTemperate() {
        return temperate;
    }

    public String getHumedity() {
        return humedity;
    }

    public String getLightStatue() {
        return lightStatue;
    }

    public String getDustStatue() {
        return dustStatue;
    }
}
