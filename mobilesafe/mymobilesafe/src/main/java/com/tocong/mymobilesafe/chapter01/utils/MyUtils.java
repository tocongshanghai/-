package com.tocong.mymobilesafe.chapter01.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by tocong on 2016/4/21.
 * 1.获取本地应用的版本号
 * 2.安装下载下来的版本号
 */
public class MyUtils {

    public static String getVersion(Context context){
        PackageManager packageManager=context.getPackageManager();
        try {
           PackageInfo  packageInfo= packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }


    }

    public static void installApk(Activity activity,File result){
        Intent intent =new Intent("android.intent.action.View");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(result),"application/vnd.package-archive");
        activity.startActivityForResult(intent,0);


    }

}
