package com.tocong.mymobilesafe.chatper01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chatper01.utils.MyUtils;
import com.tocong.mymobilesafe.chatper01.utils.VersionUpdateUtils;

public class SplashActivity extends AppCompatActivity {

    //    应用版本号
    private TextView mVersionTV;
    //    本地版本号
    private String mVersion;

    /*
    * 初始化控件
    * */

    private void initView() {
        mVersionTV = (TextView) findViewById(R.id.tv_splash_version);
        mVersionTV.setText(mVersion);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVersion = MyUtils.getVersion(getApplicationContext());
        initView();
        final VersionUpdateUtils versionUpdateUtils = new VersionUpdateUtils(mVersion, SplashActivity.this);
        new Thread() {
            @Override
            public void run() {
                versionUpdateUtils.getCloudVersion();
            }
        }.start();
    }
}
