package com.tocong.mymobilesafe.chapter03.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;
import com.tocong.mymobilesafe.chatper01.utils.StreamUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tocong on 2016/4/28.
 */
public class InterceptCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean BlackNumstatus = sharedPreferences.getBoolean("BlackNumStatus", true);
        if (!BlackNumstatus) {
            return;

        }

        BlackNumberDao blackNumberDao = new BlackNumberDao(context);
        if (!intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String mIncomingNumber = "";
            //来电
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            switch (telephonyManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    mIncomingNumber = intent.getStringExtra("incoming_number");
                    int blackContactMode = blackNumberDao.getBlackContactMode(mIncomingNumber);
                    if (blackContactMode == 1 || blackContactMode == 3) {
                        //观察呼叫记录，如果呼叫记录生成了，就删除呼叫记录
                        Uri uri = Uri.parse("content://call_log_calls");
                        context.getContentResolver().registerContentObserver(uri, true, new CallLogObserver(new Handler(), mIncomingNumber, context));
                        try {
                            endCall(context);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                    break;


            }


        }
    }

    private class CallLogObserver extends ContentObserver {
        private String inComingNumber;
        private Context context;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public CallLogObserver(Handler handler, String inComingNumber, Context context) {
            super(handler);
            this.inComingNumber = inComingNumber;
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.i("calllogobserver", "呼叫记录生成");
            context.getContentResolver().unregisterContentObserver(this);
            deleteCallLog(inComingNumber, context);

        }
    }

    /*
    * 删除呼叫记录
    * */
    public void deleteCallLog(String inComingNumber, Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://call_log/calls");
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, "number=?", new String[]{inComingNumber}, "_id desc limit 1");
        if (cursor.moveToNext()) {
            String id = cursor.getString(0);
            resolver.delete(uri, "_id=?", new String[]{id});

        }
    }

    /*
    * 挂断电话
    * */

    public void endCall(Context context) throws InvocationTargetException, IllegalAccessException {
        try {
            Class clazz = context.getClassLoader().loadClass("android.os.ServiceManager");
            try {
                Method method = clazz.getDeclaredMethod("getService", String.class);
                IBinder iBinder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
                ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
                iTelephony.endCall();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
