package com.naton.hardware.ui.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.naton.hardware.R;
import com.naton.hardware.app.NTConfig;
import com.naton.hardware.model.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by tianxiaopeng on 15-1-9.
 */
public class UserHeaderView extends FrameLayout {


    private Context _context;

    private RoundedImageView avatarImageView;
    private TextView nicknameTextView, signatureTextView;

    private User _user;

    private UserFragment _userFragment;

    public UserHeaderView(UserFragment userFragment) {
        super(userFragment.getActivity());
        construct(userFragment);
    }

    private void construct(UserFragment userFragment) {
        _userFragment = userFragment;
        _context = userFragment.getActivity();
        LayoutInflater.from(_context).inflate(R.layout.view_user_header, this, true);
        setupViews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setupViews();
    }

    private void setupViews() {
        avatarImageView = (RoundedImageView) findViewById(R.id.AvatarImageView);
        nicknameTextView = (TextView) findViewById(R.id.NicknameTextView);
        signatureTextView = (TextView) findViewById(R.id.SignatureTextView);
    }

    public void setUser(User user) {
        _user = user;
        updateAvatarImageView();
        updateTextViews();
    }

    private void updateAvatarImageView() {
        String avatarUrl = "";
        if (_user != null && _user.getAvatar() != null) {
            avatarUrl = NTConfig.getInstance().getIMAGEBaseURL() + "/SDpic/common/picSelect?gid=" + _user.getAvatar();
            DisplayImageOptions options = new DisplayImageOptions.Builder()//
                    .cacheInMemory(true)//
                    .cacheOnDisk(true)//
                    .bitmapConfig(Bitmap.Config.RGB_565)//
                    .build();
            ImageLoader.getInstance().displayImage(avatarUrl, avatarImageView, options);
        } else {
            avatarImageView.setImageResource(R.drawable.ic_avatar_default);
        }
    }

    private void updateTextViews() {
        if (_user != null) {
            nicknameTextView.setText(_user.getName());
            signatureTextView.setText(_user.getDescript());
        } else {
            nicknameTextView.setText("点击登录");
            signatureTextView.setText("骨科人的社交");
        }
    }
}

