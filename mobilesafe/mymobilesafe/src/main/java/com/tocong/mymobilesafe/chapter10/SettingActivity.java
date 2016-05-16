package com.tocong.mymobilesafe.chapter10;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter10.widget.SettingView;

import static com.tocong.mymobilesafe.R.id.imgv_leftbtn;

public class SettingActivity extends Activity implements View.OnClickListener,SettingView.OnCheckedStatusIsChanged {
private SettingView mBlackNumSV;
    private SharedPreferences mSP;
    private boolean running;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        initView();
    }
    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_blue));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("设置中心");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        mBlackNumSV = (SettingView) findViewById(R.id.sv_blacknumber_set);

        mBlackNumSV.setOnCheckedStatusIsChanged(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        mBlackNumSV.setChecked(mSP.getBoolean("BlackNumStatus", true));
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case imgv_leftbtn:
                finish();
                break;
        }
    }
    public void onCheckedChanged(View view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.sv_blacknumber_set:
                saveStatus("BlackNumStatus",isChecked);
                break;

        }
    }

    private void saveStatus(String keyname, boolean isChecked) {
        if(!TextUtils.isEmpty(keyname)){
            SharedPreferences.Editor edit = mSP.edit();
            edit.putBoolean(keyname, isChecked);
            edit.commit();
        }
    }
}
