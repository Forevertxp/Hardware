package com.naton.hardware.app;

import android.content.Context;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public class NTConfig {


    private static NTConfig s_instance;

    private String _apiBaseURL;
    private Context _context;
    private String _versionText;
    private String _imageBaseURL;

    private NTConfig() {
    }

    public static NTConfig getInstance() {
        if (s_instance == null) {
            s_instance = new NTConfig();
        }
        return s_instance;
    }

    public void init(String apiBaseURL,String imageBaseURL, String versionText, Context context) {
        _apiBaseURL = apiBaseURL;
        _imageBaseURL = imageBaseURL;
        _versionText = versionText;
        _context = context;
    }

    public String getVersionText() {
        return _versionText;
    }

    public String getAPIBaseURL() {
        return _apiBaseURL;
    }

    public String getIMAGEBaseURL() {
        return _imageBaseURL;
    }

    public Context getContext() {
        return _context;
    }
}

