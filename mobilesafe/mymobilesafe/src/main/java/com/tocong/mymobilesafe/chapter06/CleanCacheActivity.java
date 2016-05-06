package com.tocong.mymobilesafe.chapter06;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class CleanCacheActivity extends Activity implements View.OnClickListener {
    protected static final int CLEANING = 100;
    protected static final int CLEAN_FINISH = 10;
    private AnimationDrawable animationDrawable;
    private long cacheMemory;
    private TextView mMemroyTV;
    private TextView mMemoryUnitTV;
    private PackageManager pm;
    private FrameLayout mCleanCacheFL;
    private FrameLayout mFinishCleanFL;
    private TextView mSizeTV;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case CLEANING:
                    long memory= (long) msg.obj;
                    formatMemory(memory);
                    if (memory==cacheMemory){
                        animationDrawable.stop();
                        mCleanCacheFL.setVisibility(View.GONE);
                        mFinishCleanFL.setVisibility(View.VISIBLE);
                        mSizeTV.setText("成功清理:"+Formatter.formatFileSize(CleanCacheActivity.this,cacheMemory));
                    }
                    break;



            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleancache);
        initView();
        pm = getPackageManager();
        Intent intent = getIntent();
        cacheMemory = intent.getLongExtra("cacheMemory", 0);
        initData();

    }


    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.rose_red));
        ((TextView) findViewById(R.id.tv_title)).setText("缓存清理");
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        animationDrawable = (AnimationDrawable) findViewById(R.id.imgv_trashbin_cacheclean).getBackground();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        mMemroyTV = (TextView) findViewById(R.id.tv_cleancache_momory);
        mMemoryUnitTV = (TextView) findViewById(R.id.tv_cleancache_memoryunit);
        mCleanCacheFL = (FrameLayout) findViewById(R.id.fl_cleancache);
        mFinishCleanFL = (FrameLayout) findViewById(R.id.fl_finishclean);
        mSizeTV = (TextView) findViewById(R.id.tv_cleanmemorysize);
        findViewById(R.id.btn_finish_cleancache).setOnClickListener(this);
    }

    private void initData() {
        clearAll();
        new Thread() {
            @Override
            public void run() {
                super.run();
                long memory = 0;
                while (memory < cacheMemory) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    i=random.nextInt(1024);
                    memory+=1024*i;
                    if(memory>cacheMemory){
                        memory=cacheMemory;

                    }
                    Message msg=Message.obtain();
                    msg.what=CLEANING;
                    msg.obj=memory;
                    handler.sendMessageDelayed(msg,200);
                }

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_finish_cleancache:
                finish();
                break;
        }
    }

    class ClearCacheObserver extends android.content.pm.IPackageDataObserver.Stub {
        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

        }
    }

    private void clearAll() {
        Method[] methods = PackageManager.class.getMethods();
        for (Method method : methods) {
            if ("freeStorageAndNotify".equals(method.getName())) {
                try {
                    method.invoke(pm, Integer.MAX_VALUE, new ClearCacheObserver());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return;
            }

        }

        Toast.makeText(this, "清理完毕", Toast.LENGTH_SHORT).show();
    }

    private void formatMemory(long memory) {
        String cacheMemoryStr=Formatter.formatFileSize(this, memory);
        String memoryStr;
        String memoryUnit;
        //根据大小判定单位
        if(memory >900){
            //大于900则单位两位
            memoryStr = cacheMemoryStr.substring(0, cacheMemoryStr.length()-2);
            memoryUnit = cacheMemoryStr.substring(cacheMemoryStr.length()-2, cacheMemoryStr.length());
        }else{
            //单位是一位
            memoryStr = cacheMemoryStr.substring(0, cacheMemoryStr.length()-1);
            memoryUnit = cacheMemoryStr.substring(cacheMemoryStr.length()-1, cacheMemoryStr.length());
        }
        mMemroyTV.setText(memoryStr);
        mMemoryUnitTV.setText(memoryUnit);
    }

}
