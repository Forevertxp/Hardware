package com.naton.hardware.ui.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.DeviceInfoResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.DeviceInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeviceDetailActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        ViewSwitcher.ViewFactory {

    private Button delBtn;
    private Gallery gallery;
    private ImageSwitcher mSwitcher;

    private DeviceInfo deviceInfo;
    private ArrayList<String> imageList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        final String deviceId = getIntent().getStringExtra("deviceId");
        setTitleText("设备" + deviceId);
        setRightImage(R.drawable.ic_delete);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DeviceDetailActivity.this)
                        .setTitle("请确认")
                        .setMessage("确认删除当前设备吗？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDevice(deviceId);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

            }
        });

        if (!TextUtils.isEmpty(deviceId))
            fetchDeviceInfo(deviceId);

        delBtn = (Button) findViewById(R.id.delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setOnItemSelectedListener(this);
    }

    private void fetchDeviceInfo(String deviceId) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        deviceService.fetchDeviceInfo(new DeviceRequest(userId, deviceId), new Callback<DeviceInfoResult>() {
            @Override
            public void success(DeviceInfoResult result, Response response) {
                if (result.code == 1) {
                    deviceInfo = result.result_data;
                    if (!TextUtils.isEmpty(deviceInfo.getImage())){
                        mSwitcher.setVisibility(View.VISIBLE);
                        gallery.setVisibility(View.VISIBLE);
                        imageList.add(deviceInfo.getImage());
                        gallery.setAdapter(new GalleryAdapter(DeviceDetailActivity.this,imageList));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void deleteDevice(String deviceId) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        deviceService.deleteDevice(new DeviceRequest(userId, deviceId), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1) {
                    Intent intent = new Intent("com.xpown.device.changed");
                    sendBroadcast(intent);
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapter, View v, int position,
                               long id) {
        ImageLoader.getInstance().loadImage(imageList.get(position),new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(bitmap);
                mSwitcher.setImageDrawable(drawable);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return i;
    }



    private class GalleryAdapter extends BaseAdapter {
        private Activity activity;
        private ArrayList<String> data;

        public GalleryAdapter(Activity act, ArrayList<String> dat) {
            activity = act;
            data = dat;
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView vi = new ImageView(activity);

            DisplayImageOptions options = new DisplayImageOptions.Builder()//
                    .cacheInMemory(true)//
                    .cacheOnDisk(true)//
                    .bitmapConfig(Bitmap.Config.RGB_565)//
                    .build();
            ImageLoader.getInstance().displayImage(data.get(position), vi, options);

            vi.setAdjustViewBounds(true);
            vi.setLayoutParams((new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));
            vi.setScaleType(ImageView.ScaleType.FIT_XY);
            return vi;
        }
    }
}
