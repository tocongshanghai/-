package com.tocong.mymobilesafe.chapter04.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.RootToolsException;
import com.tocong.mymobilesafe.chapter04.entity.AppInfo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by tocong on 2016/5/3.
 */
public class EngineUtils {

    /*
    * 分享应用
    * */

    public static  void shareApplication(Context context, AppInfo appInfo){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"推荐您使用一款软件叫"+appInfo.getAppNmae()+"下载路径: https://play.google.com/store/apps/details?id="+appInfo.getPackName());
        context.startActivity(intent);


    }


    /*
    * 开启应用
    * */
public static  void startApplication(Context context ,AppInfo appInfo){
    PackageManager packageManager=context.getPackageManager();
    Intent intent=packageManager.getLaunchIntentForPackage(appInfo.getPackName());
    if(intent!=null){
        context.startActivity(intent);
    }else {

        Toast.makeText(context,"该应用没有启动画面",Toast.LENGTH_LONG).show();
    }
}

    /*
    * 设置应用
    * */

    public static  void SettingAppDetail(Context context,AppInfo appInfo){
        Intent intent=new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:"+appInfo.getPackName()));
        context.startActivity(intent);

    }


    /*
    * 卸载应用
    * */

    public static void  uninstallApplication(Context context,AppInfo appInfo){
        if(appInfo.isUserApp()){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:"+appInfo.getPackName()));
            context.startActivity(intent);

        }else {
           if(!RootTools.isRootAvailable()){

               Toast.makeText(context,"对不起，您还没有root权限",Toast.LENGTH_LONG).show();
           return;
           }
            try {
                if(RootTools.isAccessGiven()){
                    Toast.makeText(context,"请授予安全助手权限",Toast.LENGTH_LONG).show();
return ;
                }
                RootTools.sendShell("mount -o remount,rw /system",3000);
                RootTools.sendShell("rm -r"+appInfo.getApkPath(),3000);

            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (RootToolsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }
}
