package com.tocong.mymobilesafe.chapter06;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter06.adapter.CacheClearAdapter;
import com.tocong.mymobilesafe.chapter06.entity.CacheInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CacheClearListActivity extends Activity implements View.OnClickListener {

    protected static final int SCANNING = 100;
    protected static final int FINISH = 101;
    private AnimationDrawable animationDrawable;
    //建议清理
    private TextView mRecomandTV;
    //可清理
    private TextView mCanCleanTV;
    private long caccheMemory;
    private List<CacheInfo> cacheInfos = new ArrayList<CacheInfo>();
    private List<CacheInfo> mCacheInfos = new ArrayList<CacheInfo>();
    private PackageManager pm;
    private CacheClearAdapter adapter;
    private ListView mCacheLV;
    private Button mCacheBtn;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCANNING:
                    PackageInfo info = (PackageInfo) msg.obj;
                    mRecomandTV.setText("正在扫描:" + info.packageName);
                    mCanCleanTV.setText("已扫描缓存:" + Formatter.formatFileSize(CacheClearListActivity.this, caccheMemory));
                    mCacheInfos.clear();
                    mCacheInfos.addAll(cacheInfos);

                    adapter.notifyDataSetChanged();
                    mCacheLV.setSelection(mCacheInfos.size());
                    break;
                case FINISH:
                    animationDrawable.stop();
                    if (caccheMemory > 0) {
                        mCacheBtn.setEnabled(true);
                        mRecomandTV.setText("扫描完成");
                    } else {
                        mCacheBtn.setEnabled(false);
                        Toast.makeText(CacheClearListActivity.this, "手机很干净", Toast.LENGTH_LONG).show();
                    }

                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacheclearlist);
        pm = getPackageManager();
        initView();
    }

    /*
    * 初始化数据
    * */
    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.rose_red));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        ((TextView) findViewById(R.id.tv_title)).setText("缓存扫描");
        mRecomandTV = (TextView) findViewById(R.id.tv_recommend_clean);
        mCanCleanTV = (TextView) findViewById(R.id.tv_can_clean);
        mCacheLV = (ListView) findViewById(R.id.lv_scancache);
        mCacheBtn = (Button) findViewById(R.id.btn_cleanall);
        mCacheBtn.setOnClickListener(this);
        animationDrawable = (AnimationDrawable) findViewById(R.id.imgv_broom).getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        adapter = new CacheClearAdapter(mCacheInfos, this);
        mCacheLV.setAdapter(adapter);
        fillData();
    }

    /*
    * 填充数据
    * */
    private Thread thread;

    private void fillData() {
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                cacheInfos.clear();
                List<PackageInfo> infos = pm.getInstalledPackages(0);
                for (PackageInfo packageInfo : infos) {
                    getCacheSize(packageInfo);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.obj = packageInfo;
                    msg.what = SCANNING;
                    handler.sendMessage(msg);

                }
                Message msg = Message.obtain();
                msg.what = FINISH;
                handler.sendMessage(msg);

            }
        };
        thread.start();

    }


    /*
    * 得到一个应用包的应用程序的缓存大小
    *
    * */

    public void getCacheSize(PackageInfo info) {
        try {
            Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
            try {
                method.invoke(pm, info.packageName, new MyPackObserver(info));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    private class MyPackObserver extends android.content.pm.IPackageStatsObserver.Stub {

        private PackageInfo info;

        public MyPackObserver(PackageInfo info) {
            this.info = info;
        }

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            long cachesize = pStats.cacheSize;
            if (cachesize > 0) {
                CacheInfo cacheInfo = new CacheInfo();
                cacheInfo.setCacheSize(cachesize);
                cacheInfo.setPackageNmae(info.packageName);
                cacheInfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
                cacheInfo.setAppIncon(info.applicationInfo.loadIcon(pm));
                cacheInfos.add(cacheInfo);
                caccheMemory += cachesize;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationDrawable.stop();
        if(thread!=null){
            thread.interrupt();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_cleanall:
                if (caccheMemory > 0) {
                    Intent intent = new Intent(this, CleanCacheActivity.class);
                    intent.putExtra("cacheMemory", caccheMemory);
                    startActivity(intent);
                    finish();
                }
                break;


        }
    }
}
