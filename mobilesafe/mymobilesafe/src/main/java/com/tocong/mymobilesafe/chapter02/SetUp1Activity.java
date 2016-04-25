package com.tocong.mymobilesafe.chapter02;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;

/**
 * Created by yichunyan on 2016/4/23.
 */
public class SetUp1Activity extends BaseSetUpActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        initView();
    }

    @Override
    public void showNext() {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }

    @Override
    public void showPre() {
        Toast.makeText(this,"当前已经是第一页了",Toast.LENGTH_SHORT).show();
    }

    private void initView(){
//        设置第一个小圆点的颜色
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);

    }



}
