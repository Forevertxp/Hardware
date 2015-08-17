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
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.request.ImageRequest;
import com.naton.hardware.http.request.PicDelRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.DeviceInfoResult;
import com.naton.hardware.http.result.ImageResult;
import com.naton.hardware.http.result.PicResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.DeviceInfo;
import com.naton.hardware.model.Image;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.snail.svprogresshud.SVProgressHUD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeviceDetailActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        ViewSwitcher.ViewFactory {

    private Button delBtn;
    private Gallery gallery;
    private ImageSwitcher mSwitcher;

    private String deviceId;
    private ArrayList<String> imageList = new ArrayList<String>();

    private RelativeLayout autoRL, manualRL;

    private Button takePic, deletePic;

    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        deviceId = getIntent().getStringExtra("deviceId");
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
            fetchDevicePic(deviceId,"");

        delBtn = (Button) findViewById(R.id.delBtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        autoRL = (RelativeLayout) findViewById(R.id.autoRL);
        manualRL = (RelativeLayout) findViewById(R.id.manualRL);

        autoRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceDetailActivity.this, AutoActivity.class);
                intent.putExtra("deviceId", deviceId);
                startActivity(intent);
            }
        });

        manualRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceDetailActivity.this, ManualActivity.class);
                intent.putExtra("deviceId", deviceId);
                startActivity(intent);
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

        takePic = (Button) findViewById(R.id.takeBtn);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePic(deviceId);
            }
        });

        deletePic = (Button) findViewById(R.id.delBtn);
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DeviceDetailActivity.this)
                        .setTitle("请确认")
                        .setMessage("确认删除吗？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
                                if (!TextUtils.isEmpty(url)){
                                    deviceService.delImage(new PicDelRequest(deviceId,url),new Callback<CommonResult>() {
                                        @Override
                                        public void success(CommonResult result, Response response) {
                                            if (result.code == 1) {
                                                fetchDevicePic(deviceId,"");
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    });
                                }
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
    }

    private void fetchDevicePic(String deviceId, final String newImgUrl) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        Calendar calendar = Calendar.getInstance();
        // 默认显示一周内数据
        String dateEnd = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
                .getTime()); // 当前日期
        calendar.add(Calendar.MONTH, -1); // 一个月前日期
        String dateBegin = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
                .getTime());
        deviceService.fetchImageList(new ImageRequest(deviceId, dateBegin, dateEnd, 0, 100), new Callback<ImageResult>() {
            @Override
            public void success(ImageResult result, Response response) {
                if (result.code == 1) {
                    if(imageList.size()>0){
                        imageList.clear();
                    }
                    ArrayList<Image> imgList = result.result_data;
                    if(!TextUtils.isEmpty(newImgUrl)){
                        imageList.add(newImgUrl);
                    }
                    if (imgList.size() > 0) {
                        for (Image image : imgList) {
                            imageList.add(image.getImage());
                        }
                    }
                    mSwitcher.setVisibility(View.VISIBLE);
                    gallery.setVisibility(View.VISIBLE);
                    gallery.setAdapter(new GalleryAdapter(DeviceDetailActivity.this, imageList));
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

    private void takePic(final String deviceId) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.takePic(new DeviceRequest("", deviceId), new Callback<PicResult>() {
            @Override
            public void success(PicResult result, Response response) {
                if (result.code == 1) {
                    fetchDevicePic(deviceId,result.result_data.getImage());
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
        url = imageList.get(position);
        ImageLoader.getInstance().loadImage(imageList.get(position), new ImageLoadingListener() {
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
