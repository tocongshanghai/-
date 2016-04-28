package com.tocong.mymobilesafe.chapter03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter03.adapter.BlackContactAdapter;
import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;
import com.tocong.mymobilesafe.chapter03.entity.BlackContactInfo;

import java.util.ArrayList;
import java.util.List;

public class SecurityPhoneActivity extends Activity implements View.OnClickListener {

    private FrameLayout mHaveBlackNumber;  //有黑名单时，显示的帧布局
    private FrameLayout mNoBlackNumber;     //没有黑名单时，显示的帧布局
    private BlackNumberDao dao;
    private ListView mListView;
    private int pageNumber = 0;
    private int pageSize = 15;
    private int totalNumber;
    private List<BlackContactInfo> pageBlackNumber = new ArrayList<BlackContactInfo>();

    private BlackContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_phone);
        initView();
        fillData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
            break;
            case R.id.btn_addblacknumber:
              //原来的  startActivity(new Intent(this,addBlackNumberActivity.class));
                startActivityForResult(new Intent(this,addBlackNumberActivity.class),0);


        }
    }

    private void fillData() {
        dao = new BlackNumberDao(SecurityPhoneActivity.this);
        totalNumber = dao.getTotalNumber();
        if (totalNumber == 0) {
            //数据库中没有黑名单
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);

        } else if (totalNumber > 0) {
            // 数据库中有黑名单
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
            pageNumber = 0;
            if (pageBlackNumber.size() > 0) {
                pageBlackNumber.clear();

            }
            pageBlackNumber.addAll(dao.getPageBlackNumber(pageNumber, pageSize));
            if (adapter == null) {
                adapter = new BlackContactAdapter(pageBlackNumber, SecurityPhoneActivity.this);
                adapter.setCallBack(new BlackContactAdapter.BlackContactCallBack() {
                    @Override
                    public void DataSizeChanged() {
                        fillData();
                    }
                });
                mListView.setAdapter(adapter);

            }else{
                adapter.notifyDataSetChanged();

            }

        }


    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView) findViewById(R.id.tv_title)).setText("通讯卫士");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        mHaveBlackNumber = (FrameLayout) findViewById(R.id.fl_haveblacknumber);
        mNoBlackNumber = (FrameLayout) findViewById(R.id.fl_noblacknumber);
        findViewById(R.id.btn_addblacknumber).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_blacknumber);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //没有滑动状态
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //获取最后一个可见条目
                        int lastVisiblePosition = mListView.getLastVisiblePosition();
                        //如果当前条目是该页查询的最后一个，就要增加查询下一页的数据
                        if (lastVisiblePosition == pageBlackNumber.size() - 1) {
                            pageNumber++;
                            if (pageNumber * pageSize >= totalNumber) {
                                Toast.makeText(SecurityPhoneActivity.this, "没有可加载数据", Toast.LENGTH_SHORT).show();

                            } else {
                                pageBlackNumber.addAll(dao.getPageBlackNumber(pageNumber, pageSize));
                                adapter.notifyDataSetChanged();
                            }

                        }
                        break;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null&&resultCode==0){
            fillData();

        }
    }
}
