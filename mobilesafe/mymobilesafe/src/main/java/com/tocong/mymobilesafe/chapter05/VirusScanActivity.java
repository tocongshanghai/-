package com.tocong.mymobilesafe.chapter05;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VirusScanActivity extends Activity implements View.OnClickListener {
    private TextView mLastTimeTV;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virusscan);
        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
copyDB("antivirus.db");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String lastVirusScanTime=mSharedPreferences.getString("lastVirusScan","您还没有查杀病毒");
        mLastTimeTV.setText(lastVirusScanTime);
    }
/*
    * 拷贝数据库
    * */

    private void copyDB(final String dbname) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                File file = new File(getFilesDir(), dbname);
                if (file.exists() && file.length() > 0) {
                    Log.i("VirusActivity", "数据库存在");
                    return;
                }
                try {
                    InputStream inputStream = getAssets().open(dbname);
                    FileOutputStream fileOutputStream = openFileOutput(dbname, MODE_PRIVATE);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.light_blue));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("病毒查杀");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        mLastTimeTV = (TextView) findViewById(R.id.tv_lastscantime);
        findViewById(R.id.rl_allscanvirus).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.rl_allscanvirus:
                startActivity(new Intent(this, VirusScanSpeedActivity.class));
                break;
        }
    }
}
