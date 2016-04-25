package com.tocong.mymobilesafe.chapter02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tocong.mymobilesafe.App;

/**
 * Created by tocong on 2016/4/25.
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    private static final String TAG=BootCompleteReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        ((App)context.getApplicationContext()).correctSIM();
    }

}
