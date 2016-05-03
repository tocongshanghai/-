package com.tocong.mymobilesafe.chapter04.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by tocong on 2016/4/30.
 */
public class AppInfo {
    private String packName;//应用包名
    private Drawable icon;  //应用程序图标
    private String appNmae;  //应用程序名称
    private String apkPath; //应用程序路径
    private long appSize;  // 应用程序大小
    private boolean isInRoom; // 是否是手机存储
    private boolean isUserApp;  //是否是用户应用
    private boolean isSelected = false;  //是否选中，默认不选中

    public String getPackName() {
        return packName;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isInRoom() {
        return isInRoom;
    }

    public Drawable getIcon() {
        return icon;
    }

    public long getAppSize() {
        return appSize;
    }

    public String getAppNmae() {
        return appNmae;
    }

    public String getApkPath() {
        return apkPath;

    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public void setUserApp(boolean userApp) {
        isUserApp = userApp;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setInRoom(boolean inRoom) {
        isInRoom = inRoom;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public void setAppNmae(String appNmae) {
        this.appNmae = appNmae;
    }

    //拿到手机app位置的字符串
    public String getAppLocation(boolean isInRoom) {
        if (isInRoom) {
            return "手机内存";
        } else {
            return "外部存储";

        }

    }
}
