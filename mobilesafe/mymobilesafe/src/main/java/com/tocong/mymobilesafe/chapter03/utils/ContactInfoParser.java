package com.tocong.mymobilesafe.chapter03.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.tocong.mymobilesafe.chapter03.entity.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tocong on 2016/4/25.
 */
public class ContactInfoParser {
    /*
    * 获取系统联系人
    * */
    public static List<ContactInfo> getSystemContact(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                System.out.println("-------------------------------------");
                System.out.println("联系人id：" + id);
                ContactInfo info = new ContactInfo();
                info.setId(id);
                //根据联系人的id,查询data表，把id号的数据取出来
                //系统api 查询data表时，查询的是data表的视图
                Cursor dataCursor = resolver.query(datauri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{id}, null);

                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.print("姓名：" + data1);
                        info.setName(data1);
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                        System.out.println("号码：" + data1);
                        info.setPhone(data1);
                    }

                }
                if (TextUtils.isEmpty(info.getName()) && TextUtils.isEmpty(info.getPhone())) {
                    continue;

                }
                infos.add(info);
                dataCursor.close();
            }


        }

        cursor.close();
        return infos;
    }

}
