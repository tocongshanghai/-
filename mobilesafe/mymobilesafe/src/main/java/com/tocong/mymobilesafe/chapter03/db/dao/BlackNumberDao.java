package com.tocong.mymobilesafe.chapter03.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;

import com.tocong.mymobilesafe.chapter02.entity.ContactInfo;
import com.tocong.mymobilesafe.chapter03.db.BlackNumberOpenHelper;
import com.tocong.mymobilesafe.chapter03.entity.BlackContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tocong on 2016/4/26.
 */
public class BlackNumberDao {

    private BlackNumberOpenHelper blackNumberOpenHelper;

    public BlackNumberDao(Context context) {
        blackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }

    /*
    * 添加数据
    * */

    public boolean add(BlackContactInfo blackContactInfo) {
        SQLiteDatabase sqLiteDatabase = blackNumberOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (blackContactInfo.getPhoneNumber().startsWith("+86")) {
            String phoneNumber = blackContactInfo.getPhoneNumber().substring(3, blackContactInfo.getPhoneNumber().length());

            blackContactInfo.setPhoneNumber(phoneNumber);

        }
        contentValues.put("number", blackContactInfo.getPhoneNumber());
        contentValues.put("name", blackContactInfo.getContactName());
        contentValues.put("mode", blackContactInfo.getMode());
        long rowId = sqLiteDatabase.insert("blacknumber", null, contentValues);
        if (rowId == -1) {
            return false;

        } else {
            return true;
        }
    }
    /*
    * 删除数据
    * */

    public boolean delete(BlackContactInfo blackContactInfo) {
        SQLiteDatabase sqLiteDatabase = blackNumberOpenHelper.getWritableDatabase();
        int rowNumber = sqLiteDatabase.delete("blacknumber", "number=?", new String[]{blackContactInfo.getPhoneNumber()});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }

    }


    /*
    * 分页查询数据的记录
    * pagenumber  第几页页码，从0页开始
    * pagesize 每一个页面的大小
    * */

    public List<BlackContactInfo> getPageBlackNumber(int pagenumber, int pagesize) {

        SQLiteDatabase sqLiteDatabase = blackNumberOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select number,mode,name from blacknumber limit ? offset ?",
                new String[]{String.valueOf(pagesize), String.valueOf(pagesize * pagenumber)});

        List<BlackContactInfo> mBlackContactInfos = new ArrayList<BlackContactInfo>();
        while (cursor.moveToNext()) {
            BlackContactInfo blackContactInfo = new BlackContactInfo();
            blackContactInfo.setPhoneNumber(cursor.getString(0));
            blackContactInfo.setMode(cursor.getInt(1));
            blackContactInfo.setContactName(cursor.getString(2));
            mBlackContactInfos.add(blackContactInfo);
        }
        cursor.close();
        sqLiteDatabase.close();
        SystemClock.sleep(30);
        return mBlackContactInfos;
    }

    /*
    * 判断号码是否在黑名单中存在
    * */
public boolean IsNumberExist(String number){
    SQLiteDatabase sqLiteDatabase =blackNumberOpenHelper.getWritableDatabase();
    Cursor cursor=sqLiteDatabase.query("blacknumber",null,"number=?",new String[]{number},null,null,null);
    if(cursor.moveToNext()){

        cursor.close();
        sqLiteDatabase.close();
        return true;
    }else{
        cursor.close();
        sqLiteDatabase.close();
        return false;
    }

}

    /*
    * 根据号码查询黑名单的信息
    * */

public int  getBlackContactMode(String number){
    SQLiteDatabase sqLiteDatabase =blackNumberOpenHelper.getWritableDatabase();
    Cursor cursor=sqLiteDatabase.query("blacknumber",new String[]{"mode"},"number=?",new String[]{number},null,null,null);
    int mode=0;
    if(cursor.moveToNext()){
        mode=cursor.getInt(cursor.getColumnIndex("mode"));


    }
    cursor.close();
    sqLiteDatabase.close();;
    return mode;

}
    /*
    * 获取数据库的条目总数
    * */
public int getTotalNumber(){
    SQLiteDatabase sqLiteDatabase =blackNumberOpenHelper.getWritableDatabase();
    Cursor cursor=sqLiteDatabase.rawQuery("select count(*) from blacknumber",null);
    cursor.moveToNext();
    int count=cursor.getInt(0);
    cursor.close();;
    sqLiteDatabase.close();
    return count;


}

}
