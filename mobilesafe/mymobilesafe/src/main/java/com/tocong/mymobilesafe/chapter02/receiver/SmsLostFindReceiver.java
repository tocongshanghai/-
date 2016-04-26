package com.tocong.mymobilesafe.chapter02.receiver;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter02.service.GPSLocationService;

/**
 * Created by tocong on 2016/4/26.
 */
public class SmsLostFindReceiver extends BroadcastReceiver {
    private static final String TAG = SmsLostFindReceiver.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private ComponentName componentName;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
        boolean protecting = sharedPreferences.getBoolean("protecting", true);
        if (protecting) {
            //防盗保护开启
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            //获取超级管理员
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            for (Object obj : objs) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                String sender = smsMessage.getDisplayOriginatingAddress();
                if (sender.startsWith("+86")) {
                    sender = sender.substring(3, sender.length());

                }
                String body = smsMessage.getMessageBody();
                String safephone = sharedPreferences.getString("safephone", null);
                if (!TextUtils.isEmpty(safephone) & sender.equals(safephone)) {
                    if ("#*location*#".equals(body)) {
                        Log.i(TAG, "返回位置信息");
                        //发送位置-----》代写
                        Intent service = new Intent(context, GPSLocationService.class);
                        context.startService(service);
                        abortBroadcast();
                    } else if ("#*alarm*#".equals(body)) {
                        Log.i(TAG, "播放报警音乐");
                        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                        mediaPlayer.setVolume(1.0f, 1.0f);
                        mediaPlayer.start();
                    } else if ("#*wipedata*#".equals(body)) {
                        Log.i(TAG, "远程清除数据");
                        devicePolicyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                        abortBroadcast();
                    } else if ("#*lockScreen*#".equals(body)) {
                        Log.i(TAG, "远程锁屏");
                        devicePolicyManager.resetPassword("123", 0);
                        devicePolicyManager.lockNow();
                        abortBroadcast();

                    }

                }
            }

        }
    }
}
