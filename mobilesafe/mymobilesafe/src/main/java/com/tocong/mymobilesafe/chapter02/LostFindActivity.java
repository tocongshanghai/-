package com.tocong.mymobilesafe.chapter02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tocong.mymobilesafe.R;

/**
 * Created by tocong on 2016/4/22.
 */
public class LostFindActivity extends Activity implements View.OnClickListener{
    private TextView mSafePhoneTV;
    private RelativeLayout mInterSetupRL;
    private SharedPreferences msharedPreferences;
    private ToggleButton mToggleButton;
    private TextView mProtectStatusTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostfind);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
       // if(!isSetUp()){
//            如果没有进入过设置向导，就进入
         //   startSetUpActivity();
        //}
        initView();
    }

    private void initView() {
        TextView mTitleTV = (TextView) findViewById(R.id.tv_title);
        mTitleTV.setText("手机防盗");
        ImageView  mLeftImgv= (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setImageResource(R.mipmap.back);
        mLeftImgv.setOnClickListener(this);
       findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
        //findViewById(R.id.rl_titlebar).setBackgroundColor(getColor(R.color.purple));
mSafePhoneTV= (TextView) findViewById(R.id.tv_safephone);
        mSafePhoneTV.setText(msharedPreferences.getString("safephone",""));
        mToggleButton= (ToggleButton) findViewById(R.id.togglebtn_lostfind);
        mInterSetupRL= (RelativeLayout) findViewById(R.id.rl_inter_setup_wizard);
mInterSetupRL.setOnClickListener(this);
        mProtectStatusTV= (TextView) findViewById(R.id.tv_lostfind_protectstauts);
        //查询手机防盗是否开启，默认为开启
        boolean protecting = msharedPreferences.getBoolean("protecting", true);
        if(protecting){
            mProtectStatusTV.setText("防盗保护已经开启");
            mToggleButton.setChecked(true);
        }else{
            mProtectStatusTV.setText("防盗保护没有开启");
            mToggleButton.setChecked(false);
        }
mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            mProtectStatusTV.setText("防盗保护已经开启");
        }else{

            mProtectStatusTV.setText("防盗保护没有开启");
        }

        SharedPreferences.Editor editor=msharedPreferences.edit();
        editor.putBoolean("protecting",b);
        editor.commit();
    }
});
    }


    private boolean isSetUp() {
        return msharedPreferences.getBoolean("isSetUp", false);
    }

private void startSetUpActivity(){

    Intent intent=new Intent(LostFindActivity.this,SetUp1Activity.class);
    startActivity(intent);
    finish();
}

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.rl_inter_setup_wizard:
        startSetUpActivity();
    break;
    case R.id.imgv_leftbtn:
        finish();
        break;
}
    }
}
