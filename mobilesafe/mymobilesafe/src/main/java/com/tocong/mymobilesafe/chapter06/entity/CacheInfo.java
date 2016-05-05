package com.tocong.mymobilesafe.chapter06.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by tocong on 2016/5/5.
 */
public class CacheInfo {
    private String packageNmae;
    private long cacheSize;
    private Drawable appIncon;
    private String appName;

    public Drawable getAppIncon() {
        return appIncon;
    }

    public String getPackageNmae() {
        return packageNmae;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppIncon(Drawable appIncon) {
        this.appIncon = appIncon;
    }

    public void setPackageNmae(String packageNmae) {
        this.packageNmae = packageNmae;
    }

    public void setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
