package com.tocong.mymobilesafe.chapter04;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter04.adapter.AppManagerAdapter;
import com.tocong.mymobilesafe.chapter04.entity.AppInfo;
import com.tocong.mymobilesafe.chapter04.utils.AppInfoParse;

import java.util.ArrayList;
import java.util.List;

public class AppMnaagerActivity extends Activity implements View.OnClickListener {
    private TextView mPhoneMemoryTV;
    private TextView mSDMemoryTV;
    private ListView mListView;
    private List<AppInfo> appInfos;
    private List<AppInfo> userAppInfos = new ArrayList<AppInfo>();
    private List<AppInfo> systemAppInfos = new ArrayList<AppInfo>();
    private AppManagerAdapter adapter;
    private UninstallReceiver receiver;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    if (adapter == null) {
                        adapter = new AppManagerAdapter(AppMnaagerActivity.this, userAppInfos, systemAppInfos);
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 15:
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    private TextView mAPPNumtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_mnaager);
        receiver = new UninstallReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver, intentFilter);
        initView();
    }


    /*
    * 初始化控件
    * */

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_yellow));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        ((TextView) findViewById(R.id.tv_title)).setText("软件管家");
        mPhoneMemoryTV = (TextView) findViewById(R.id.tv_phonememory_appmanager);
        mSDMemoryTV = (TextView) findViewById(R.id.tv_sdmemory_appmanager);
        mAPPNumtv = (TextView) findViewById(R.id.tv_appnumber);
        mListView = (ListView) findViewById(R.id.lv_appmanager);

        getMemoryFromPhone();
        initData();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }


    /*
    * 初始化数据
    * */

    private void initData() {
        appInfos = new ArrayList<AppInfo>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                appInfos.clear();
                userAppInfos.clear();
                systemAppInfos.clear();
                appInfos.addAll(AppInfoParse.getAppInfos(AppMnaagerActivity.this));
                for (AppInfo appInfo : appInfos) {
                    if (appInfo.isUserApp()) {

                        userAppInfos.add(appInfo);
                    } else {
                        systemAppInfos.add(appInfo);

                    }


                }
                handler.sendEmptyMessage(10);
            }
        }.start();

    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (adapter != null) {
                    new Thread() {

                        @Override
                        public void run() {
                            super.run();
                            AppInfo mappInfo = (AppInfo) adapter.getItem(position);
                            boolean flag = mappInfo.isSelected();
                            //先将集合中所有条目的AppInfo变为未选中状态
                            for (AppInfo appInfo : userAppInfos) {
                                appInfo.setSelected(false);
                            }
                            for (AppInfo appInfo : systemAppInfos) {
                                appInfo.setSelected(false);
                            }
                            if (mappInfo != null) {
                                //如果已经选中，则变为未选中
                                if (flag) {
                                    mappInfo.setSelected(false);
                                } else {
                                    mappInfo.setSelected(true);
                                }
                                handler.sendEmptyMessage(15);
                            }

                        }
                    }.start();


                }


            }
        });


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= userAppInfos.size() + 1) {
                    mAPPNumtv.setText("系统程序:" + systemAppInfos.size() + "个");

                } else {
                    mAPPNumtv.setText("用户程序:" + userAppInfos.size() + "个");

                }
            }
        });

    }


    /*
    * 拿到手机和sd剩余容量
    * */
    private void getMemoryFromPhone() {

        long avail_sd = Environment.getExternalStorageDirectory().getFreeSpace();
        long avail_rom = Environment.getDataDirectory().getFreeSpace();
        //格式化内存
        String str_avail_sd = Formatter.formatFileSize(this, avail_sd);
        String str_avail_rom = Formatter.formatFileSize(this, avail_rom);
        mPhoneMemoryTV.setText("剩余手机内存" + str_avail_sd);
        mSDMemoryTV.setText("剩余sd卡内存" + str_avail_sd);
    }

    class UninstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        receiver = null;
    }
}
