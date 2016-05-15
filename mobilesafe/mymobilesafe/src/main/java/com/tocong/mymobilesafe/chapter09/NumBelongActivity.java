package com.tocong.mymobilesafe.chapter09;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter09.db.dao.NumBelongtoDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NumBelongActivity extends Activity implements View.OnClickListener {

    private EditText mNumET;
    private TextView mResultTV;
    private String dbName = "address.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbelong);
        initView();
        copyDB(dbName);
    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_red));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("号码归属地查询");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        findViewById(R.id.btn_searchnumbelongto).setOnClickListener(this);
        mNumET = (EditText) findViewById(R.id.et_num_numbelongto);
        mResultTV = (TextView) findViewById(R.id.tv_searchresult);
        mNumET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String string = editable.toString().toString().trim();
                if (string.length() == 0) {
                    mResultTV.setText("");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
           case  R.id.btn_searchnumbelongto:
            String phonenumber = mNumET.getText().toString().trim();
            if (!TextUtils.isEmpty(phonenumber)) {
                File file = new File(getFilesDir(), dbName);

                if (!file.exists() || file.length() <= 0) {
                    copyDB(dbName);

                }

                String location= NumBelongtoDao.getLocation(phonenumber,this);
                mResultTV.setText("归属地:"+location);
            }else {

                Toast.makeText(this,"请输入号码",Toast.LENGTH_LONG).show();
            }
            break;


        }
    }

    private void copyDB(final String dbname) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                File file = new File(getFilesDir(), dbname);
                if (file.exists() && file.length() > 0) {
                    Log.i("NumBelongtoActivity", "数据库存在");
                    return;
                }
                try {
                    InputStream inputStream = getAssets().open(dbname);
                    FileOutputStream fileOutputStream = openFileOutput(dbname, MODE_PRIVATE);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);


                    }
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
