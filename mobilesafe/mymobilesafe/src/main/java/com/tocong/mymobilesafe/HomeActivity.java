package com.tocong.mymobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tocong.mymobilesafe.chatper01.adapyer.HomeAdapter;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private SharedPreferences msharePreferences;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        msharePreferences = getSharedPreferences("config", MODE_PRIVATE);
        gv_home.findViewById(R.id.gv_home);

        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

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
    *
    *
    * */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);


    }

    public boolean onkeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "back again ", Toast.LENGTH_SHORT);
                mExitTime = System.currentTimeMillis();


            } else {
                System.exit(0);


            }
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

}
