package com.tocong.mymobilesafe.chapter02.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tocong on 2016/4/26.
 * 用于监听权限的变化
 */
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
