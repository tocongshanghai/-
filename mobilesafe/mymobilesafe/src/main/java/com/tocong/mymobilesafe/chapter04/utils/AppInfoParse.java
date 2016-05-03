package com.tocong.mymobilesafe.chapter04.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.tocong.mymobilesafe.chapter04.entity.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tocong on 2016/5/3.
 * <p/>
 * 解析手机app
 */
public class AppInfoParse {

    public static List<AppInfo> getAppInfos(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        for (PackageInfo packageInfo : packageInfos) {
            AppInfo appInfo = new AppInfo();
            //包名
            String packName = packageInfo.packageName;
            appInfo.setPackName(packName);
            //图标
            Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.setIcon(icon);
//app名字
            String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setAppNmae(appName);
            //apk包的路径
            String apkPath = packageInfo.applicationInfo.sourceDir;
            appInfo.setApkPath(apkPath);
            //大小
            File file = new File(apkPath);
            long appSize = file.length();
            appInfo.setAppSize(appSize);
            //程序安装位置
            int flag = packageInfo.applicationInfo.flags;
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flag) != 0) {
                appInfo.setInRoom(false);

            } else {

                appInfo.setInRoom(true);
            }

            if ((ApplicationInfo.FLAG_SYSTEM & flag) != 0) {
                appInfo.setUserApp(false);

            } else {

                appInfo.setUserApp(true);
            }
            appInfos.add(appInfo);
            appInfo=null;


        }
return appInfos;

    }


}
