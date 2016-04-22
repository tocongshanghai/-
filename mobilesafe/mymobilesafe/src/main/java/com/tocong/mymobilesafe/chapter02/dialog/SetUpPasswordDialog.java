package com.tocong.mymobilesafe.chapter02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;

/**
 * Created by tocong on 2016/4/22.
 */
public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    //标题
    private TextView mTitleTV;
    //首次输入的密码框
    public EditText mFirstPWDET;

    //确认密码输入框
    public EditText mAffirmET;

    private MyCallBack myCallBack;

    public SetUpPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);

    }

    public void setMyCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.setup_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }
/*
* 初始化控件
*
* */

    private void initView() {
        mTitleTV = (TextView) findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_firstpwd);
        mAffirmET = (EditText) findViewById(R.id.et_affirm_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);


    }

    public void setTitle(String title) {

        if (!TextUtils.isEmpty(title)) {
            mTitleTV.setText(title);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                myCallBack.ok();
                break;
            case R.id.btn_cancle:
                myCallBack.cancle();
                break;

        }
    }


    public interface MyCallBack {
        void ok();

        void cancle();


    }
}
