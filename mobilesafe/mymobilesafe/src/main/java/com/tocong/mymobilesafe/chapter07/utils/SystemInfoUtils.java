package com.tocong.mymobilesafe.chapter07.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by tocong on 2016/5/9.
 */
public class SystemInfoUtils {

    /*
    * 判断一个服务是否处于运行状态
    * */

    public static boolean isServiceRunning(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = activityManager.getRunningServices(200);
        for (ActivityManager.RunningServiceInfo info : infos) {
            String serviceClassName = info.service.getClassName();
            if (serviceClassName.equals(className)) {
                return true;

            }

        }
        return false;

    }


    /*
    * 获取手机的总内存大小
    * */

    public static long getTotalMem() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/proc/meminfo"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String totalInfo = bufferedReader.readLine();
            StringBuffer sb = new StringBuffer();
            for (char c : totalInfo.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }

            }
            long bytesize = Long.parseLong(sb.toString()) * 1024;
            System.out.println(bytesize);
            return bytesize;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }


    }

    /*
    * 获取可用内存大小
    * */
    public static long getAvailMem(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /*
    * 获取正在运行的进程数量
    * */
    public static int getRunningProcessCount(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
        int count = infos.size();
        return count;
    }

}
