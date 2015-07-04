package com.naton.hardware.http.result;

import com.naton.hardware.model.Data;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/7/1.
 */
public class DataResult extends ServiceResult {
    public String message;
    public int code;
    public ArrayList<Data> result_data;
}
