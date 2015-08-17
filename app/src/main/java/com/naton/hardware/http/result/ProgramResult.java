package com.naton.hardware.http.result;

import com.naton.hardware.model.Program;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class ProgramResult extends ServiceResult {

    public int code;
    public String message;
    public ArrayList<Program> result_data;
}

