package com.tocong.mymobilesafe.chapter05.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by tocong on 2016/5/4.
 */
public class ScanAppInfo {
    private String appName;
    private boolean isVirus;
    private String packageName;
    private String description;
    private Drawable appIcon;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isVirus() {
        return isVirus;
    }

    public String getDescription() {
        return description;
    }

    public String getAppName() {
        return appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVirus(boolean virus) {
        isVirus = virus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
