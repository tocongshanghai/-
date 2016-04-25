package com.tocong.mymobilesafe.chapter02;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;

public class SetUp2Activity extends BaseSetUpActivity implements View.OnClickListener {

    private TelephonyManager mTelecomManager;
    private Button mBindSIMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        mTelecomManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        initView();
    }

    @Override
    public void showNext() {
        if(!isBind()){
            Toast.makeText(this,"您还没有绑定SIM卡",Toast.LENGTH_SHORT).show();

        }
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp2Activity.class);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_bind_sim:
                bindSIM();
                break;

        }
    }

    private void initView() {
//        设置第二个小圆点的颜色
        ((RadioButton) findViewById(R.id.rb_second)).setChecked(true);
        mBindSIMBtn = (Button) findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);
        if (isBind()) {

            mBindSIMBtn.setEnabled(false);
        } else {
            mBindSIMBtn.setEnabled(true);
        }
    }

    private boolean isBind() {

        String simString = sp.getString("sim", null);
        if (TextUtils.isEmpty(simString)) {
            return false;
        }
        return true;

    }

    private void bindSIM(){
        if(!isBind()){
            String simSerialNumber=mTelecomManager.getSimSerialNumber();
            SharedPreferences.Editor edit=sp.edit();
            edit.putString("sim",simSerialNumber);
            edit.commit();
            Toast.makeText(this,"sim卡已经绑定成功",Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);

        }else{

            Toast.makeText(this,"sim卡已经绑定",Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        }



    }

}
