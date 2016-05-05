package com.tocong.mymobilesafe.chapter05;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter05.adapter.ScanVirusAdapter;
import com.tocong.mymobilesafe.chapter05.dao.AntiVirusDao;
import com.tocong.mymobilesafe.chapter05.entity.ScanAppInfo;
import com.tocong.mymobilesafe.chapter05.utils.MD5Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VirusScanSpeedActivity extends Activity implements View.OnClickListener {

    protected static final int SCAN_BENGIN = 100;
    protected static final int SCANING = 101;
    protected static final int SCAN_FINISH = 103;

    private int total;
    private int process;
    private TextView mProcessTV;
    private PackageManager PM;
    private boolean flag;
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
           switch (msg.what){
               case SCAN_BENGIN:
                   mScanAppTV.setText("初始化杀毒引擎...");
                   break;
               case SCANING:
                   ScanAppInfo info= (ScanAppInfo) msg.obj;
                   mScanAppTV.setText("正在扫描:"+info.getAppName());
                   int speed=msg.arg1;
                   mProcessTV.setText((speed*100/total)+"%");
                   mScanAppInfos.add(info);
                   adapter.notifyDataSetChanged();
                   mScanListView.setSelection(mScanAppInfos.size());
                   break;
               case SCAN_FINISH:
                   mScanAppTV.setText("扫描完成");
                   mScanningIcon.clearAnimation();
                   mCancleBtn.setBackgroundResource(R.mipmap.scan_complete);
                   saveScanTime();
                   break;


           }



        }
    };

    private void saveScanTime(){

        SharedPreferences.Editor editor=mSP.edit();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String  currentTime=simpleDateFormat.format(new Date());
        currentTime="上次查杀时间:"+currentTime;
        editor.putString("lastVirusScan",currentTime);
        editor.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virusscanspeed);
        PM=getPackageManager();
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        initView();
        scanVirus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_canclescan:
                if (process == total & process > 0) {

                    finish();
                } else if (process > 0 & process < total & isStop == false) {
                    mScanningIcon.clearAnimation();
                    //取消扫描
                    flag = false;
                    //更换背景图片
                    mCancleBtn.setBackgroundResource(R.mipmap.restart_scan_btn);
                } else if (isStop) {
                    startAnim();
                    scanVirus();
                    mCancleBtn.setBackgroundResource(R.drawable.cancle_scan_btn_selector);


                }
                break;

        }
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

    private void startAnim() {
        if (rani == null) {
            rani = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rani.setRepeatCount(Animation.INFINITE);
        rani.setDuration(2000);
        mScanningIcon.startAnimation(rani);
    }


    /*
    * 扫描病毒
    * */


    private void scanVirus() {
        flag = true;
        isStop = false;
        process = 0;
        mScanAppInfos.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();

                Message msg = Message.obtain();
                msg.what = SCAN_BENGIN;
                handler.sendMessage(msg);
                List<PackageInfo> installedPackages = PM.getInstalledPackages(0);
                total = installedPackages.size();
                for (PackageInfo packageInfo : installedPackages) {
                    if (!flag) {
                        isStop = true;
                        return;
                    }
                    String apkPath = packageInfo.applicationInfo.sourceDir;
                    //检查文件的特征码
                    String md5info = MD5Utils.getFileMd5(apkPath);
                    String result = AntiVirusDao.checkVirus(md5info);
                    msg = Message.obtain();
                    msg.what = SCANING;
                    ScanAppInfo scanAppInfo = new ScanAppInfo();
                    if (result == null) {
                        scanAppInfo.setDescription("扫描安全");
                        scanAppInfo.setVirus(false);

                    } else {
                        scanAppInfo.setDescription(result);
                        scanAppInfo.setVirus(true);
                    }
                    process++;
                    scanAppInfo.setPackageName(packageInfo.packageName);
                    scanAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(PM).toString());
                    scanAppInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(PM));
                    msg.obj = scanAppInfo;
                    msg.arg1 = process;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

                msg=Message.obtain();
                msg.what=SCAN_FINISH;
                handler.sendMessage(msg);


            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=false;


    }
}
