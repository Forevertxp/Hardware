package com.naton.hardware.app;

import android.app.Application;

import com.naton.hardware.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by tianxiaopeng on 15-1-7.
 */
public class NTAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        NTThemer.getInstance().init(this);
        NTThemer.getInstance().setActionBarBackgroundColor(getResources().getColor(R.color.ActionBarBackgroundColor));
        NTThemer.getInstance().setActionBarForegroundColor(getResources().getColor(R.color.ActionBarForegroundColor));

        final String API_BASE_URL = "http://xc1942.xicp.net:9000";
        final String IMAGE_BASE_URL = "http://upload.orthoday.com";
        NTConfig.getInstance().init(API_BASE_URL,IMAGE_BASE_URL, "v1.0", this);

        /**
         * 开源框架 Image-Loader
         */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.ic_launcher) //
                .showImageOnFail(R.drawable.ic_launcher) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);

    }
}
