package com.tocong.mymobilesafe.chapter02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.tocong.mymobilesafe.R;

/**
 * Created by yichunyan on 2016/4/23.
 */
public abstract class BaseSetUpActivity extends Activity{

    public SharedPreferences sp;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("config",MODE_PRIVATE);

        //初始化手势识别器
        mGestureDetector=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(Math.abs(velocityX)<200){
                    Toast.makeText(getApplicationContext(),"动作无效，移动慢了点",Toast.LENGTH_SHORT).show();

                    return true;
                }
                if ((e2.getRawX() - e1.getRawX()) > 200) {
                    // 从左向右滑动屏幕，显示上一个界面
                    showPre();
                    overridePendingTransition(R.anim.pre_in,
                            R.anim.pre_out);
                    return true;
                }
                if ((e1.getRawX() - e2.getRawX()) > 200) {
                    // 从右向左滑动屏幕，显示下一个界面
                    showNext();
                    overridePendingTransition(R.anim.next_in,
                            R.anim.next_out);
                    return true;
                }


                return super.onFling(e1, e2, velocityX, velocityY);


            }
        });
    }

    public abstract  void showNext();
    public abstract  void showPre();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void startActivityAndFinishSelf(Class<?> cls){

        Intent intent =new Intent(this,cls);
        startActivity(intent);
        finish();

    }
}
