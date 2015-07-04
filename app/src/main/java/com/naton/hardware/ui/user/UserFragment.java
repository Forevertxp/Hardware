package com.naton.hardware.ui.user;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.naton.hardware.R;
import com.naton.hardware.app.NTConfig;
import com.naton.hardware.http.HttpCallBack;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.UserManager;
import com.naton.hardware.http.result.ServiceError;
import com.naton.hardware.model.AccessToken;
import com.naton.hardware.model.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class UserFragment extends Fragment implements View.OnClickListener {
    private final static int USER_LOGIN = 0x1111;
    private final static int USER_INFO = 0x1212;
    private final static String USER_CONFIG_FILENAME = "userInfo.dat";

    private UserHeaderView headerView;
    private RelativeLayout passRL, aboutRL, settingRL;
    private Button logoutBtn;

    public UserFragment() {
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerView = new UserHeaderView(this);
        if (AuthManager.getInstance().isAuthenticated()) {
            User userInfo = UserManager.getInstance().loadLocalUserInfo();
            if (userInfo != null)
                headerView.setUser(userInfo);
        }
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AuthManager.getInstance().isAuthenticated()) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, USER_LOGIN);
                } else {
                    Intent intent = new Intent(getActivity(), UserEditActivity.class);
                    startActivityForResult(intent, USER_INFO);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        FrameLayout userFrame = (FrameLayout) view.findViewById(R.id.userLayout);
        aboutRL = (RelativeLayout) view.findViewById(R.id.aboutRL);
        passRL = (RelativeLayout) view.findViewById(R.id.passRL);
        settingRL = (RelativeLayout) view.findViewById(R.id.settingRL);
        logoutBtn = (Button) view.findViewById(R.id.logoutBtn);
        if (AuthManager.getInstance().isAuthenticated()) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }
        userFrame.addView(headerView);
        aboutRL.setOnClickListener(this);
        passRL.setOnClickListener(this);
        settingRL.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutRL:
                break;
            case R.id.passRL:
                onModifyPass();
                break;
            case R.id.settingRL:
                onUserSetting();
                break;
            case R.id.logoutBtn:
                logout();
                break;
        }
    }

    private void onUserSetting() {
    }

    private void onModifyPass() {
        AuthManager authManager = AuthManager.getInstance();
        if (!authManager.isAuthenticated()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("您尚未登录")
                    .setMessage("登录后才能查看，是否去登录？")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转登录
                            onUserLogin();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        } else {
            Intent intent = new Intent(getActivity(), ModifyPassActivity.class);
            startActivity(intent);
        }
    }

    protected void onUserLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, USER_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            logoutBtn.setVisibility(View.VISIBLE);
            reloadFromUser();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void reloadFromUser() {
        if (!AuthManager.getInstance().isAuthenticated()) {
            headerView.setUser(null);
            return;
        }
        AccessToken token = AuthManager.getInstance().getAccessToken();
        fetchUserInfo(token.getUserId());
    }

    /**
     * 查询用户基本信息
     */
    private void fetchUserInfo(String userId) {
        final UserManager userManager = UserManager.getInstance();
        userManager.fetchUserInfo(userId, new HttpCallBack() {
            @Override
            public void success() {
                User userInfo = userManager.getUserInfo();
                headerView.setUser(userInfo);
                // 保存在本地
                save(userInfo);
            }

            @Override
            public void failure(ServiceError error) {

            }
        });
    }

    /**
     * 本地保持userInfo信息
     */
    private void save(User userInfo) {
        try {
            FileOutputStream fos = NTConfig.getInstance().getContext().openFileOutput(USER_CONFIG_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            if (userInfo != null)
                oos.writeObject(userInfo);
            oos.close();
            //发广播通知更新
            //NTAPP.getAppContext().sendBroadcast(new Intent(MainActivity.BROADCAST_ACTION_LOGIN_SUCCESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        new AlertDialog.Builder(getActivity())
                .setTitle("请确认")
                .setMessage("确认退出当前用户吗？")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AuthManager.getInstance().logout();
                        save(null);
                        Intent intent = new Intent("com.xpown.device.changed");
                        getActivity().sendBroadcast(intent);
                        logoutBtn.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

}