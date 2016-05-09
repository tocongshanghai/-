package com.tocong.mymobilesafe.chapter07.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter07.entity.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tocong on 2016/5/9.
 * <p>
 * 任务信息 ，进程信息解析器
 */
public class TaskInfoParser {
    /*
    * 获取正在运行的所有进程的信息
    * */
    public static List<TaskInfo> getRunningTaskInfos(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager packageManager = context.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            String packname = info.processName;
            TaskInfo taskInfo = new TaskInfo();
            //
            taskInfo.setPackageName(packname);
            Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{info.pid});
            long memsize = memoryInfos[0].getTotalPrivateDirty() * 1024;
            //
            taskInfo.setAppMemory(memsize);
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(packname, 0);
                Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
                //
                taskInfo.setAppIcon(icon);
                String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                //
                taskInfo.setAppName(appName);
                //
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    taskInfo.setUserApp(false);

                } else {
                    taskInfo.setUserApp(true);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                taskInfo.setAppName(packname);
                taskInfo.setAppIcon(context.getResources().getDrawable(R.mipmap.ic_default));
            }
            taskInfos.add(taskInfo);
        }
        return taskInfos;
    }

}
