package com.tocong.mymobilesafe.chapter05.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by yichunyan on 2016/5/4.
 * 检查某个md5是否是病毒
 */
public class AntiVirusDao {
    private static String DATABASE = "/data/data/com.tocong.mymobilesafe/files/antivirus.db";


    /*
    * 检查某个md5是否是病毒
    * */
    public static String checkVirus(String md5) {
        String desc = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select desc from datable where md5=?", new String[]{md5});
        if (cursor.moveToNext()) {
            desc = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return desc;
    }
/*
* 判断数据库文件是否存在
* */

    public static boolean isDBExit() {
        File file = new File(DATABASE);
        return file.exists() && file.length() > 0;

    }

    /*
    * 判断数据库版本号
    * */
    public static String getDBVersion() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE, null, SQLiteDatabase.OPEN_READONLY);
        String versionNumber = "0";
        Cursor cursor = db.rawQuery("select subcnt from version", null);
        if (cursor.moveToNext()) {
            versionNumber = cursor.getString(0);

        }
        cursor.close();
        db.close();
        return versionNumber;
    }


    /*
    * 更新数据库版本号的操作
    * */
    public static void updateDBVersion(int newversion) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues contentValues = new ContentValues();
        String versionNumber = "0";
        contentValues.put("subcnt", newversion);
        db.update("version", contentValues, null, null);
        db.close();
    }

    /*
    * 更新数据库的api
    * */
    public static void add(String desc, String md5) {

        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues contentValues = new ContentValues();
        contentValues.put("md5", md5);
        contentValues.put("desc", desc);
        contentValues.put("type", 6);
        contentValues.put("name", "Android.Hack.i22hkt.a");
        db.insert("datable", null, contentValues);
        db.close();
    }
}
