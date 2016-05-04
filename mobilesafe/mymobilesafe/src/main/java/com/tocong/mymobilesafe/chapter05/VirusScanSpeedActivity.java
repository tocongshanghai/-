package com.tocong.mymobilesafe.chapter05;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter05.adapter.ScanVirusAdapter;
import com.tocong.mymobilesafe.chapter05.entity.ScanAppInfo;

import java.util.ArrayList;
import java.util.List;

public class VirusScanSpeedActivity extends Activity implements View.OnClickListener {

    protected static final int SCAN_BENGIN = 100;
    protected static final int SCANING = 101;
    protected static final int SCAN_FINISH = 103;

    private int total;
    private int process;
    private TextView mProcessTV;
    private PackageManager PM;
    private boolean glag;
    private boolean isStop;
    private TextView mScanAppTV;
    private Button mCancleBtn;
    private ImageView mScanningIcon;
    private RotateAnimation rani;
    private ListView mScanListView;
    private ScanVirusAdapter adapter;
    private List<ScanAppInfo> mScanAppInfos = new ArrayList<ScanAppInfo>();
    private SharedPreferences mSP;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virusscanspeed);
    }

    @Override
    public void onClick(View view) {

    }

    private void initView() {

        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.light_blue));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("病毒查杀进度");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        mProcessTV = (TextView) findViewById(R.id.tv_scanprocess);
        mScanAppTV = (TextView) findViewById(R.id.tv_scanapp);
        mCancleBtn = (Button) findViewById(R.id.btn_canclescan);
        mCancleBtn.setOnClickListener(this);
        mScanListView = (ListView) findViewById(R.id.lv_scanapps);
        adapter = new ScanVirusAdapter(this, mScanAppInfos);
        mScanListView.setAdapter(adapter);
        mScanningIcon = (ImageView) findViewById(R.id.imgv_scaningicon);
        startAnim();
    }

    private void startAnim(){


    }
}
