package com.hellopopartz.hidlauncher.data.res;

import android.graphics.drawable.Drawable;

/**
 * Created by Resh on 22/02/2015.
 */
public class AppInfo {
    public CharSequence appPackage;
    public CharSequence appName;
    public Drawable appIcon;
    public boolean official;

    public AppInfo(CharSequence appName, CharSequence appPackage, Drawable appIcon, boolean official) {
        this.appName = appName;
        this.appPackage = appPackage;
        this.appIcon = appIcon;
        this.official = official;
    }
}
