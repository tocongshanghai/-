package com.tocong.mymobilesafe.chapter03.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;

import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;

/**
 * Created by tocong on 2016/4/28.
 */
public class interceptSmsReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean BlackNumStatus=sharedPreferences.getBoolean("BlackNumStatus",true);
        if(!BlackNumStatus){

            return;
        }
        //如果是黑名单，则终止广播
        BlackNumberDao blackNumberDao=new BlackNumberDao(context);
        Object [] objects= (Object[]) intent.getExtras().get("pdus");
        for(Object o:objects){
            SmsMessage smsMessage=SmsMessage.createFromPdu((byte[]) o);
            String sender=smsMessage.getOriginatingAddress();
            String body=smsMessage.getMessageBody();
            if(sender.startsWith("+86")){
                sender=sender.substring(3,sender.length());

            }
            int mode=blackNumberDao.getBlackContactMode(sender);
            if(mode==2||mode==3){
                abortBroadcast();
            }

        }


    }
}
