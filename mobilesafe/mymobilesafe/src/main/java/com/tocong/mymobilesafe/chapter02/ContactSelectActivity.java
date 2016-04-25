package com.tocong.mymobilesafe.chapter02;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter02.adapter.ContactAdapter;
import com.tocong.mymobilesafe.chapter02.entity.ContactInfo;
import com.tocong.mymobilesafe.chapter02.utils.ContactInfoParser;

import java.util.List;

public class ContactSelectActivity extends Activity implements View.OnClickListener {

    private ListView mListView;
    private ContactAdapter adapter;
    private List<ContactInfo> systemContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_select);
        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    if (systemContacts != null) {
                        adapter = new ContactAdapter(systemContacts, ContactSelectActivity.this);
                        mListView.setAdapter(adapter);
                    }
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText("选择联系人");
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);

//        导航栏的颜色
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
        mListView = (ListView) findViewById(R.id.lv_contact);
        new Thread() {
            @Override
            public void run() {
                super.run();
                systemContacts = ContactInfoParser.getSystemContact(ContactSelectActivity.this);
                handler.sendEmptyMessage(10);
            }
        }.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo item = (ContactInfo) adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("phone", item.getPhone());
                setResult(0, intent);
                finish();
            }
        });
    }
}
