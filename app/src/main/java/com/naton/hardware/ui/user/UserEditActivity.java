package com.naton.hardware.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.app.NTConfig;
import com.naton.hardware.http.HttpCallBack;
import com.naton.hardware.http.manager.UserManager;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.ServiceError;
import com.naton.hardware.http.service.UploadService;
import com.naton.hardware.model.User;
import com.naton.hardware.util.FileUtils;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class UserEditActivity extends BaseActivity implements View.OnClickListener {

    private User userInfo;
    private RoundedImageView avatarImage;
    private RelativeLayout avatarRL, nameRL, sexRL, localRL, hospitalRL, jobRL, signatureRL;
    private TextView nameText, sexText, localText, hospitalText, jobText, signatureText;
    private Button updateBtn;
    private RadioButton male, female;

    private String cityCode = "CN11";

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        setTitleText("个人资料");

       userInfo = UserManager.getInstance().loadLocalUserInfo();
       initViews();
        if (userInfo != null)
            initData();
    }

    private void initViews() {
        avatarImage = (RoundedImageView) findViewById(R.id.avatarImage);

        nameText = (TextView) findViewById(R.id.nameText);
        sexText = (TextView) findViewById(R.id.sexText);
        localText = (TextView) findViewById(R.id.localText);
        hospitalText = (TextView) findViewById(R.id.hospitalText);
        jobText = (TextView) findViewById(R.id.jobText);
        signatureText = (TextView) findViewById(R.id.signatureText);

        avatarRL = (RelativeLayout) findViewById(R.id.avatarRL);
        nameRL = (RelativeLayout) findViewById(R.id.nameRL);
        sexRL = (RelativeLayout) findViewById(R.id.sexRL);
        localRL = (RelativeLayout) findViewById(R.id.localRL);
        hospitalRL = (RelativeLayout) findViewById(R.id.hospitalRL);
        jobRL = (RelativeLayout) findViewById(R.id.jobRL);
        signatureRL = (RelativeLayout) findViewById(R.id.signatureRL);

        updateBtn = (Button) findViewById(R.id.updateBtn);

        avatarRL.setOnClickListener(this);
        nameRL.setOnClickListener(this);
        sexRL.setOnClickListener(this);
        localRL.setOnClickListener(this);
        hospitalRL.setOnClickListener(this);
        jobRL.setOnClickListener(this);
        signatureRL.setOnClickListener(this);
        updateBtn.setOnClickListener(this);

    }

    private void initData() {
        Picasso.with(this)
                .load(NTConfig.getInstance().getIMAGEBaseURL() + "/SDpic/common/picSelect?gid=" + "1cbd07c5-c5e5-41aa-b02d-abb240e417f1")
                .error(R.drawable.ic_avatar_default)
                .into(avatarImage);
        nameText.setText(userInfo.getName());
        if (userInfo.getSex()!=null&&userInfo.getSex().equals("1")) {
            sexText.setText("男");
        } else {
            sexText.setText("女");
        }
        localText.setText(userInfo.getLocal());
        signatureText.setText(userInfo.getDescript());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatarRL:
//                showDialog();
                break;
            case R.id.sexRL:
                editSex();
                break;
            case R.id.nameRL:
                editName();
                break;
            case R.id.localRL:
                cityCode = "";
//                AreaPopupWindow areaPopupWindow = new AreaPopupWindow(this);
//                areaPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//                areaPopupWindow.setOutsideTouchable(true);
//                areaPopupWindow.setFocusable(true);
//                areaPopupWindow.setOnPopupWindowClickListener(new AreaPopupWindow.OnPopupWindowClickListener() {
//                    @Override
//                    public void onPopupWindowOkClick(String cityCode, String areaName) {
//                        UserEditActivity.this.cityCode = cityCode;
//                        localText.setText(areaName);
//                    }
//                });
//                areaPopupWindow.showAtLocation(UserEditActivity.this.findViewById(R.id.main),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                break;
            case R.id.signatureRL:
                editSignature();
                break;
            case R.id.updateBtn:
                if (mImageUri == null) {
                    updateUserInfo(userInfo.getAvatar());
                } else {
                    uploadImage(mImageUri);
                }
                break;
        }

    }

    private void editName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("编辑姓名");

        final EditText editText = new EditText(this);
        if (userInfo != null)
            editText.setText(userInfo.getDescript());
        editText.setLines(5);
        editText.setGravity(Gravity.TOP | Gravity.LEFT);
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.setSelection(editText.getText().length());
                editText.requestFocus();
            }
        });
        alert.setView(editText);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = editText.getText().toString();
                        nameText.setText(name);
                    }
                });

        alert.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    private void editSex() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("编辑性别");

        View view = View.inflate(this, R.layout.view_sex_choose, null);
        male = (RadioButton) view.findViewById(R.id.maleRadio);
        female = (RadioButton) view.findViewById(R.id.maleRadio);
        if (sexText.getText().toString().equals("男")) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        alert.setView(view);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (male.isChecked()) {
                            sexText.setText("男");
                        } else {
                            sexText.setText("女");
                        }
                    }
                });

        alert.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    private void editSignature() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("编辑个性签名");

        final EditText editText = new EditText(this);
        if (userInfo != null)
            editText.setText(userInfo.getDescript());
        editText.setLines(5);
        editText.setGravity(Gravity.TOP | Gravity.LEFT);
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.setSelection(editText.getText().length());
                editText.requestFocus();
            }
        });
        alert.setView(editText);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String updatedSignature = editText.getText().toString();
                        signatureText.setText(updatedSignature);
                    }
                });

        alert.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    public void uploadImage(Uri uri) {
        SVProgressHUD.showInView(UserEditActivity.this, "头像上传中...", false);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://upload.orthoday.com")
                .build();
        UploadService service = restAdapter.create(UploadService.class);

        if (uri != null) {
            getBitmapSize(uri);
            Bitmap bitmap = getBitmap(uri);
            String tempPath = FileUtils.saveBitmap(bitmap, "snail_temp" + System.currentTimeMillis());
            File imageFile = new File(tempPath);
            TypedFile typedFile = new TypedFile("application/octet-stream", imageFile);

            service.uploadImage(typedFile, 0, 480, 320, "png", new Callback<CommonResult>() {
                @Override
                public void success(CommonResult result, Response response) {
//                    if (result.getImgGid() != null) {
//                        String gid = result.getImgGid();
//                        updateUserInfo(gid);
//                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }

    }

    private void updateUserInfo(String avatarGId) {
        SVProgressHUD.showInView(UserEditActivity.this, "保存中...", false);
        String sexCode;
        if (sexText.getText().toString().equals("男")) {
            sexCode = "1";
        } else {
            sexCode = "2";
        }
        UserManager.getInstance().updateUserInfo(nameText.getText().toString(),"email", sexCode, "birthday",localText.getText().toString(),
                signatureText.getText().toString(), "avatar", jobText.getText().toString(), new HttpCallBack() {
                    @Override
                    public void success() {
                        SVProgressHUD.dismiss(UserEditActivity.this);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                    @Override
                    public void failure(ServiceError error) {
                        SVProgressHUD.dismiss(UserEditActivity.this);
                    }
                });

    }


//    private FetchPhotoDialog mFetchPhotoDialog;
//
//    private void showDialog() {
//        if (mFetchPhotoDialog == null) {
//            mFetchPhotoDialog = new FetchPhotoDialog(this);
//            mFetchPhotoDialog.setFetchPhoteClickListener(mFetchPhotoClickListener);
//        }
//        mFetchPhotoDialog.show();
//    }
//
//    private final FetchPhotoDialog.FetchPhotoClickListener mFetchPhotoClickListener = new FetchPhotoDialog.FetchPhotoClickListener() {
//        @Override
//        public void fetchFromAblumClick() {
//            openPhotoLibrary(REQUEST_PHOTO_LIBRARY);
//
//        }
//
//        @Override
//        public void fetchFromCameraClick() {
//            mImageUri = openCapture(REQUEST_IMAGE_CAPTURE);
//        }
//
//        @Override
//        public void cancelClick() {
//
//        }
//    };

    /**
     * 调用相机和相册后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            String tempFileName = System.currentTimeMillis() + ".jpg"; // 图片剪裁的时候存储的临时文件的名称
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    // 设置头像使用图片剪裁
                    if (mImageUri != null) {
                        getBitmapSize(mImageUri);
                        avatarImage.setImageBitmap(getBitmap(mImageUri));
                    }
                    break;
                case REQUEST_PHOTO_LIBRARY:
                    if (data == null)
                        return;
                    mImageUri = data.getData();
                    if (mImageUri != null) {
                        getBitmapSize(mImageUri);
                        avatarImage.setImageBitmap(getBitmap(mImageUri));
                    }
                    break;

            }
        }
    }

    /**
     * 请求相机.
     */
    public static final int REQUEST_IMAGE_CAPTURE = 10008;

    /**
     * 请求相册.
     */
    public static final int REQUEST_PHOTO_LIBRARY = 10009;

    private Uri openCapture(int requestCode) {

        if (!isCanUseSDCard()) {
            Toast.makeText(this, "SD卡不可用", Toast.LENGTH_SHORT).show();
            return null;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);
        Uri uri = null;
        try {
            uri = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Throwable ex) {
            // 红米手机会出现无法创建文件的crash，做一个保护
            File file = new File(getImageCacheDir(this).getAbsolutePath(), filename + ".jpg");
            uri = Uri.fromFile(file);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, requestCode);
        return uri;
    }

    private boolean isCanUseSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private File getImageCacheDir(Context context) {
        return getCacheDirByType("uil-images");
    }

    private File getCacheDirByType(String type) {
        File cacheDir = getCacheDir();
        File typeCacheDir = new File(cacheDir, type);
        if (typeCacheDir != null && !typeCacheDir.exists()) {
            typeCacheDir.mkdirs();
        }
        return typeCacheDir;
    }

    private void openPhotoLibrary(int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, requestCode);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private int sampleSize = 1;
    private static final int DEFAULT_WIDTH = 480;
    private static final int DEFAULT_HEIGHT = 320;

    private int width;
    private int height;

    private void getBitmapSize(Uri uri) {
        InputStream is = null;
        try {

            is = getInputStream(uri);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);

            width = options.outWidth;
            height = options.outHeight;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private Bitmap getBitmap(Uri uri) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            try {
                is = getInputStream(uri);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            while ((width / sampleSize > DEFAULT_WIDTH * 2) || (height / sampleSize > DEFAULT_HEIGHT * 2)) {
                sampleSize *= 2;
            }
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = sampleSize;

            bitmap = BitmapFactory.decodeStream(is, null, option);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return bitmap;
    }

    private InputStream getInputStream(Uri mUri) throws IOException {
        try {
            if (mUri.getScheme().equals("file")) {
                return new java.io.FileInputStream(mUri.getPath());
            } else {
                return this.getContentResolver().openInputStream(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
}
