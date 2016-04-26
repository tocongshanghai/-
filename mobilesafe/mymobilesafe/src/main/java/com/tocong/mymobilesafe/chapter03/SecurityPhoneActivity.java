package com.tocong.mymobilesafe.chapter03;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.tocong.mymobilesafe.R;

public class SecurityPhoneActivity extends Activity implements View.OnClickListener{

    private FrameLayout mHaveBlackNumber;  //有黑名单时，显示的帧布局
    private FrameLayout mNoBlackNumber;     //没有黑名单时，显示的帧布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_phone);
    }

    @Override
    public void onClick(View v) {

    }
}
