package com.naton.hardware.model;

import java.io.Serializable;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class Image implements Serializable {
    private String datetime;
    private String image;

    public String getDatetime() {
        return datetime;
    }

    public String getImage() {
        return image;
    }
}
