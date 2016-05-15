package com.tocong.mymobilesafe.chapter09;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;

public class AdvancedToolsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedtools);
        initView();
    }


    private void initView() {

        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_red));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("高级工具");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        findViewById(R.id.advanceview_numbelongs).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.advanceview_numbelongs:
        startActivity(new Intent(this, NumBelongActivity.class));
        break;
    case R.id.imgv_leftbtn:
        finish();
        break;
}
    }
}
