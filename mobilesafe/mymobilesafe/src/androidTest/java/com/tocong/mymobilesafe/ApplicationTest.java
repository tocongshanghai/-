package com.tocong.mymobilesafe;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;
import com.tocong.mymobilesafe.chapter03.entity.BlackContactInfo;

import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
   private Context context;

    public ApplicationTest() {
        super(Application.class);
    }
    public void test(){


    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context=getContext();

    }

    public void testAdd() throws Exception {
        BlackNumberDao dao = new BlackNumberDao(context);
        Random random = new Random(8979);
        for (long i = 1; i < 30; i++) {
            BlackContactInfo info = new BlackContactInfo();
           info.setPhoneNumber("1350000000"+"i");
            info.setContactName("zahngsan"+i+"");
            info.setMode(random.nextInt(3)+1);
            dao.add(info);
        }

    }
}