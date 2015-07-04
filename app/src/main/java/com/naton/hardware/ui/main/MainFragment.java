package com.naton.hardware.ui.main;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naton.hardware.R;
import com.naton.hardware.app.NTThemer;
import com.naton.hardware.ui.main.tab.TabBar;


/**
 * A simple {@link android.app.Fragment} subclass.
 */


public class MainFragment extends Fragment {
    private MainPagerAdapter _viewPagerAdapter;
    private TabBar _tabBar;
    private static ViewPager _viewPager;

    private static Activity mActivity;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupActionBar();

        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        _viewPager = (ViewPager) fragmentView.findViewById(R.id.MainPager);
        _viewPager.setOffscreenPageLimit(999);
        _viewPagerAdapter = new MainPagerAdapter(getFragmentManager());
        _viewPager.setAdapter(_viewPagerAdapter);
        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int index) {
                handlePageSelected(index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _tabBar = (TabBar) fragmentView.findViewById(R.id.TabBar);

        Typeface typeface = NTThemer.getInstance().getWTFont();
        _tabBar.getItemViewAt(0).setIconTextWithTypeface("\uf10f", typeface); // Compass Icon
        _tabBar.getItemViewAt(1).setIconTextWithTypeface("\uf10a", typeface); // Picture Icon
        _tabBar.getItemViewAt(2).setIconTextWithTypeface("\uf10e", typeface); // Alt User Icon

        _tabBar.getItemViewAt(0).setTitleText("数据");
        _tabBar.getItemViewAt(1).setTitleText("设备");
        _tabBar.getItemViewAt(2).setTitleText("我的");

        _tabBar.setOnTabSelectionEventListener(new TabBar.OnTabSelectionEventListener() {
            @Override
            public boolean onWillSelectTab(int tabIndex) {
                return handleOnWillSelectTab(tabIndex);
            }

            @Override
            public void onDidSelectTab(int tabIndex) {
                handleOnDidSelectTab(tabIndex);
            }
        });

        _tabBar.limitTabNum(3);
        switchToPage(0);

        return fragmentView;
    }

    private void setupActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }
    }

    private void handlePageSelected(int index) {
        _tabBar.setSelectedTabIndex(index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean handleOnWillSelectTab(int tabIndex) {
        return true;
    }

    private void handleOnDidSelectTab(int tabIndex) {
        switchToPage(tabIndex);
    }

    public void switchToPage(int tabIndex) {
        switch (tabIndex) {
            case 0:
                _viewPager.setCurrentItem(tabIndex, false);
                setActionBarVisible(false);
                break;
            case 1:
                _viewPager.setCurrentItem(tabIndex, false);
                setActionBarVisible(true);
                ((MainActivity) mActivity).setTitleText("设备");
                ((MainActivity) mActivity).setLeftImageInvisibility();
                break;
            case 2:
                _viewPager.setCurrentItem(tabIndex, false);
                setActionBarVisible(true);
                ((MainActivity) mActivity).setTitleText("我的");
                ((MainActivity) mActivity).setLeftImageInvisibility();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void setActionBarVisible(boolean actionBarVisible) {
        ActionBar actionBar = getActivity().getActionBar();

        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }

        if (actionBarVisible) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

}

