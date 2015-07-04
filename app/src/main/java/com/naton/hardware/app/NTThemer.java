package com.naton.hardware.app;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by tianxiaopeng on 15-1-2.
 */
public class NTThemer {


    private static NTThemer s_instance;

    private Typeface _wtFont;
    private Typeface _fakFont;

    private int _actionBarForegroundColor;
    private int _actionBarBackgroundColor;

    private NTThemer() {
    }

    public static NTThemer getInstance() {
        if (s_instance == null) {
            s_instance = new NTThemer();
        }
        return s_instance;
    }

    public void init(Context context) {
        _wtFont = Typeface.createFromAsset(context.getAssets(), "fonts/WTFont.ttf");
        _fakFont = Typeface.createFromAsset(context.getAssets(), "fonts/FontAwesome.ttf");
    }

    public Typeface getFAKFont() {
        return _fakFont;
    }

    public Typeface getWTFont() {
        return _wtFont;
    }

    public int getActionBarForegroundColor() {
        return _actionBarForegroundColor;
    }

    public void setActionBarForegroundColor(int actionBarForegroundColor) {
        _actionBarForegroundColor = actionBarForegroundColor;
    }

    public int getActionBarBackgroundColor() {
        return _actionBarBackgroundColor;
    }

    public void setActionBarBackgroundColor(int actionBarBackgroundColor) {
        _actionBarBackgroundColor = actionBarBackgroundColor;
    }
}

