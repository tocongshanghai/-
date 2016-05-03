package com.tocong.mymobilesafe.chapter01.entity;

/**
 * Created by tocong on 2016/4/21.
 * 版本实体类
 */
public class VersionEntity {
    //服务器版本号
    public String versioncode;
    //    版本描述
    public String description;
    //    apk下载的地址
    public String apkurl;

    public String getVersioncode() {
        return versioncode;
    }

    public String getDescription() {
        return description;
    }

    public String getApkurl() {
        return apkurl;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApkurl(String apkurl) {
        this.apkurl = apkurl;
    }
}
