package com.tocong.mymobilesafe.chapter07.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by tocong on 2016/5/9.
 *
 * 正在运行的app信息
 */
public class TaskInfo {
    private String appName;
    private long appMemory;
    private boolean isChecked; //是否被选中
    private Drawable appIcon;
    private boolean isUserApp;
    private String packageName;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getAppName() {
        return appName;
    }

    public long getAppMemory() {
        return appMemory;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppMemory(long appMemory) {
        this.appMemory = appMemory;
    }

    public void setUserApp(boolean userApp) {
        isUserApp = userApp;
    }
}
