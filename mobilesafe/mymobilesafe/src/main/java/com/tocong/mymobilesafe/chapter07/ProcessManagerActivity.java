package com.tocong.mymobilesafe.chapter07;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.MainActivity;
import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter07.adapter.ProcessManagerAdapter;
import com.tocong.mymobilesafe.chapter07.entity.TaskInfo;
import com.tocong.mymobilesafe.chapter07.utils.SystemInfoUtils;
import com.tocong.mymobilesafe.chapter07.utils.TaskInfoParser;

import java.util.ArrayList;
import java.util.List;

public class ProcessManagerActivity extends Activity implements View.OnClickListener {
    private TextView mRunProcessNum;
    private TextView mMemoryTV;
    private TextView mProcessNumTV;
    private ListView mListView;
    ProcessManagerAdapter adapter;
    private List<TaskInfo> runningTaskInfos;
    private List<TaskInfo> userTaskInfos = new ArrayList<TaskInfo>();
    private List<TaskInfo> sysTaskInfos = new ArrayList<TaskInfo>();

    private ActivityManager activityManager;
    private int runningProcessCount;
    private long totalMem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processmanager);
        initView();
        fillData();
    }

    private void initView() {
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_green));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.mipmap.back);
        ImageView mRightImgv = (ImageView) findViewById(R.id.imgv_rightbtn);
        mRightImgv.setImageResource(R.mipmap.processmanager_setting_icon);
        mRightImgv.setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText("进程管理");
        mRunProcessNum = (TextView) findViewById(R.id.tv_runningprocess_num);
        mMemoryTV = (TextView) findViewById(R.id.tv_memory_processmanager);
        mProcessNumTV = (TextView) findViewById(R.id.tv_user_runningprocess);
        runningProcessCount = SystemInfoUtils.getRunningProcessCount(ProcessManagerActivity.this);
        mRunProcessNum.setText("运行中的进程:" + runningProcessCount + "个");
        long totalAvailMem = SystemInfoUtils.getAvailMem(this);
        totalMem = SystemInfoUtils.getTotalMem();
        mMemoryTV.setText("可用/总内存："
                + Formatter.formatFileSize(this, totalAvailMem) + "/"
                + Formatter.formatFileSize(this, totalMem));
        mListView = (ListView) findViewById(R.id.lv_runningapps);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btn_selectall).setOnClickListener(this);
        findViewById(R.id.btn_select_inverse).setOnClickListener(this);
        findViewById(R.id.btn_cleanprocess).setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = mListView.getItemAtPosition(position);
                if (object != null & object instanceof TaskInfo) {
                    TaskInfo taskInfo = (TaskInfo) object;
                    if (taskInfo.getPackageName().equals(getPackageName())) {
                        return;
                    }
                    taskInfo.setChecked(!taskInfo.isChecked());
                    adapter.notifyDataSetChanged();
                }


            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= userTaskInfos.size() + 1) {
                    mProcessNumTV.setText("系统进程:" + sysTaskInfos.size() + "个");
                } else {
                    mProcessNumTV.setText("用户进程:" + userTaskInfos.size() + "个");
                }
            }
        });

    }

    private void fillData() {
        userTaskInfos.clear();
        sysTaskInfos.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                runningTaskInfos = TaskInfoParser.getRunningTaskInfos(getApplicationContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (TaskInfo taskInfo : runningTaskInfos) {
                            if (taskInfo.isUserApp()) {
                                userTaskInfos.add(taskInfo);

                            } else {

                                sysTaskInfos.add(taskInfo);
                            }
                        }
                        if (adapter == null) {
                            adapter = new ProcessManagerAdapter(getApplicationContext(), userTaskInfos, sysTaskInfos);
                            mListView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                        if (userTaskInfos.size() > 0) {
                            mProcessNumTV.setText("用户进程:" + userTaskInfos.size() + "个");
                        } else {

                            mProcessNumTV.setText("系统进程:" + sysTaskInfos.size() + "个");
                        }
                    }
                });
            }
        }.start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.imgv_rightbtn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_selectall:
                selectAll();
                break;
            case R.id.btn_select_inverse:
                inverse();
                break;
            case R.id.btn_cleanprocess:
                cleanProcess();
                break;

        }
    }

    /*
    * 清理进程
    * */
    private void cleanProcess() {

        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int count = 0;
        long saveMemory = 0;
        List<TaskInfo> killedtaskInfos = new ArrayList<TaskInfo>();
        for (TaskInfo taskInfo : userTaskInfos) {
            if (taskInfo.isChecked()) {
                count++;
                saveMemory += taskInfo.getAppMemory();
                activityManager.killBackgroundProcesses(taskInfo.getPackageName());
                killedtaskInfos.add(taskInfo);
            }

        }
        for (TaskInfo taskInfo : sysTaskInfos) {
            if (taskInfo.isChecked()) {
                count++;
                saveMemory += taskInfo.getAppMemory();
                activityManager.killBackgroundProcesses(taskInfo.getPackageName());
                killedtaskInfos.add(taskInfo);
            }

        }
        for (TaskInfo taskInfo : killedtaskInfos) {
            if (taskInfo.isUserApp()) {
                userTaskInfos.remove(taskInfo);
            } else {
                sysTaskInfos.remove(taskInfo);
            }

        }
        runningProcessCount -= count;
        mRunProcessNum.setText("运行中的进程:" + runningProcessCount + "个");
        mMemoryTV.setText("可用/总内存："
                + Formatter.formatFileSize(this, SystemInfoUtils.getAvailMem(this)) + "/"
                + Formatter.formatFileSize(this, totalMem));
        Toast.makeText(this, "清理了" + count + "个进程,释放了"
                + Formatter.formatFileSize(this, saveMemory) + "内存", Toast.LENGTH_SHORT).show();
        mProcessNumTV.setText("用户进程：" + userTaskInfos.size() + "个");
        adapter.notifyDataSetChanged();

    }

    /*
    * 反选
    * */
    private void inverse() {
        for (TaskInfo taskInfo : userTaskInfos) {
            if (taskInfo.getPackageName().equals(getPackageName())) {

                continue;
            }
            boolean checked = taskInfo.isChecked();
            taskInfo.setChecked(!checked);
        }
        for (TaskInfo taskInfo : sysTaskInfos) {

            boolean checked = taskInfo.isChecked();
            taskInfo.setChecked(!checked);
        }
        adapter.notifyDataSetChanged();


    }

    /*
    * 全选
    * */
    private void selectAll() {
        for (TaskInfo taskInfo : userTaskInfos) {
            if (taskInfo.getPackageName().equals(getPackageName())) {
                continue;
            }
            taskInfo.setChecked(true);

        }
        for (TaskInfo taskInfo : sysTaskInfos) {

            taskInfo.setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }


    protected void OnResume() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }
}
