package com.tocong.mymobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tocong.mymobilesafe.chapter02.LostFindActivity;
import com.tocong.mymobilesafe.chapter02.dialog.InterPasswordDialog;
import com.tocong.mymobilesafe.chapter02.dialog.SetUpPasswordDialog;
import com.tocong.mymobilesafe.chapter02.utils.MD5Utils;
import com.tocong.mymobilesafe.chatper01.adapyer.HomeAdapter;

public class HomeActivity extends Activity {
    private GridView gv_home;
    private SharedPreferences msharePreferences;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        msharePreferences = getSharedPreferences("config", MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);

        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (isSetUpPassword()) {
                            showInterPswdDialog();

                        } else {

                            showSetUpPswdDialog();
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
            }


        });

    }


    /*
    *private void showInterPswdDialog() {
    }
    *
    * */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);


    }

    public boolean onkeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "back again ", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();


            } else {
                System.exit(0);


            }
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }


    /*
    * 判断用户是否设置防盗密码
    * */
    private boolean isSetUpPassword() {
        String password = msharePreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return false;

        }
        return true;


    }

    /*
    * 弹出密码设置对话框
    * */
    private void showSetUpPswdDialog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);

        setUpPasswordDialog.setMyCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)) {
                    if (firstPwsd.equals(affirmPwsd)) {
                        //保存密码
                        savePswd(affirmPwsd);
                        setUpPasswordDialog.dismiss();
                        //显示输入密码对话框


                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(HomeActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }


    /*
    * 弹出密码输入框
    * */
    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog interPasswordDialog = new InterPasswordDialog(HomeActivity.this);
        interPasswordDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(interPasswordDialog.getPassword())) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                } else if (password.equals(MD5Utils.encode(interPasswordDialog.getPassword()))) {
                    interPasswordDialog.dismiss();
                    startActivity(LostFindActivity.class);

                } else {
                    interPasswordDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，重新输入", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                interPasswordDialog.dismiss();
            }
        });
        interPasswordDialog.setCancelable(true);
        interPasswordDialog.show();

    }

//保存密码

    private void savePswd(String affirmPwsd) {
        SharedPreferences.Editor editor = msharePreferences.edit();
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        editor.commit();

    }


    //取出保存的密码
    private String getPassword() {
        String password = msharePreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {

            return "";
        }
        return password;

    }


}