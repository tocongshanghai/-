package com.tocong.mymobilesafe.chapter03;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;
import com.tocong.mymobilesafe.chapter03.entity.BlackContactInfo;

public class addBlackNumberActivity extends Activity implements View.OnClickListener {

    private CheckBox mSmsCB;
    private CheckBox mTelCB;
    private EditText mNumET;
    private EditText mNameET;
    private BlackNumberDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blacknumber);
        dao = new BlackNumberDao(this);

    }

    private void initView() {
        findViewById(R.id.imgv_leftbtn).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ((TextView) findViewById(R.id.tv_title)).setText("添加黑名单");
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        mSmsCB = (CheckBox) findViewById(R.id.cb_blacknumber_sms);
        mTelCB = (CheckBox) findViewById(R.id.cb_blacknumber_tel);
        mNumET = (EditText) findViewById(R.id.et_blacknumber);
        mNameET = (EditText) findViewById(R.id.et_blackname);
        findViewById(R.id.add_blacknum_btn).setOnClickListener(this);
        findViewById(R.id.add_fromcontact_btn).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.add_blacknum_btn:
                String number = mNumET.getText().toString().trim();
                String name = mNameET.getText().toString().trim();
                if (TextUtils.isEmpty(number) || TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "手机号码和姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    BlackContactInfo blackContactInfo = new BlackContactInfo();
                    blackContactInfo.setPhoneNumber(number);
                    blackContactInfo.setContactName(name);
                    if (mSmsCB.isChecked() & mTelCB.isChecked()) {
                        blackContactInfo.setMode(3);
                    } else if (mSmsCB.isChecked() & !mTelCB.isChecked()) {
                        blackContactInfo.setMode(2);  //短信拦截

                    } else if (!mSmsCB.isChecked() & mTelCB.isChecked()) {
                        blackContactInfo.setMode(1);  //电话拦截
                    } else {
                        Toast.makeText(this, "请选择拦截模式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!dao.IsNumberExist(blackContactInfo.getPhoneNumber())){
                        dao.add(blackContactInfo);

                    }else {
                        Toast.makeText(this,"该号码已经被添加至黑名单",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

                break;

            case  R.id.add_fromcontact_btn:


                break;


        }
    }
}
