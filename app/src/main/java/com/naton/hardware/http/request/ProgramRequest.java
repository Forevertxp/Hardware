package com.naton.hardware.http.request;

/**
 * Created by tianxiaopeng on 15/8/1.
 */
public class ProgramRequest {
    final String programId;
    final String deviceId;

    public ProgramRequest(String deviceId, String programId) {
        this.programId = programId;
        this.deviceId = deviceId;
    }
}
