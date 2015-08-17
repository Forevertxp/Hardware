package com.naton.hardware.http.result;

import com.naton.hardware.model.Image;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class ImageResult extends ServiceResult {
    public int code;
    public String message;
    public ArrayList<Image> result_data;
}
