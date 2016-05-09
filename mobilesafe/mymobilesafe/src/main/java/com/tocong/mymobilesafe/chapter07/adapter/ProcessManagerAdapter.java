package com.tocong.mymobilesafe.chapter07.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;


import com.tocong.mymobilesafe.chapter07.entity.TaskInfo;
import com.tocong.mymobilesafe.chapter07.utils.DensityUtil;

import java.util.List;

/**
 * Created by tocong on 2016/5/9.
 */
public class ProcessManagerAdapter extends BaseAdapter {
    private Context context;
    private List<TaskInfo> mUsertaskInfos;
    private List<TaskInfo> mSystaskInfos;
    private SharedPreferences mSP;

    public ProcessManagerAdapter(Context context, List<TaskInfo> mUsertaskInfos, List<TaskInfo> mSystaskInfos) {
        this.context = context;
        this.mUsertaskInfos = mUsertaskInfos;
        this.mSystaskInfos = mSystaskInfos;
        mSP = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        if (mSystaskInfos.size() > 0 & mSP.getBoolean("showSystemProcess", true)) {
            return mUsertaskInfos.size() + mSystaskInfos.size() + 2;

        } else {
            return mUsertaskInfos.size() + 1;
        }


    }

    @Override
    public Object getItem(int position) {
        if (position == 0 || position == mUsertaskInfos.size() + 1) {
            return null;
        } else if (position <= mUsertaskInfos.size()) {

            return mUsertaskInfos.get(position - 1);
        } else {
            return mSystaskInfos.get(position - mSystaskInfos.size() - 2);
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            TextView tv = getTextView();
            tv.setText("用户进程:" + mUsertaskInfos.size() + "个");
            return tv;
        } else if (position == mUsertaskInfos.size() + 1) {

            TextView tv = getTextView();
            tv.setText("系统进程:" + mSystaskInfos.size() + "个");
            return tv;
        }

        TaskInfo taskInfo = null;
        if (position <= mUsertaskInfos.size()) {
            taskInfo = mUsertaskInfos.get(position - 1);
        } else if (mSystaskInfos.size() > 0) {
            taskInfo = mSystaskInfos.get(position - mUsertaskInfos.size() - 2);
        }
        ViewHolder viewHolder = null;
        if (convertView != null && convertView instanceof RelativeLayout) {
            viewHolder = (ViewHolder) convertView.getTag();

        } else {
            convertView = View.inflate(context, R.layout.item_processmanager_list, null);
            viewHolder = new ViewHolder();
            viewHolder.mAppIconImgv = (ImageView) convertView.findViewById(R.id.imgv_appicon_processmana);
            viewHolder.mAppMemoryTV = (TextView) convertView.findViewById(R.id.tv_appmemory_processmana);
            viewHolder.mAppNameTV = (TextView) convertView.findViewById(R.id.tv_appname_processmana);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }

        if (taskInfo != null) {
            viewHolder.mAppNameTV.setText(taskInfo.getAppName());
            viewHolder.mAppMemoryTV.setText("占用内存:" + Formatter.formatFileSize(context, taskInfo.getAppMemory()));
            viewHolder.mAppIconImgv.setImageDrawable(taskInfo.getAppIcon());
            if (taskInfo.getPackageName().equals(context.getPackageName())) {
                viewHolder.mCheckBox.setVisibility(View.GONE);
            } else {
                viewHolder.mCheckBox.setVisibility(View.VISIBLE);
            }
            viewHolder.mCheckBox.setChecked(taskInfo.isChecked());
        }

        return convertView;
    }

    /***
     * 创建一个TextView
     *
     * @return
     */
    private TextView getTextView() {
        TextView tv = new TextView(context);
        tv.setBackgroundColor(context.getResources()
                .getColor(R.color.graye5));
        tv.setPadding(DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5));
        tv.setTextColor(context.getResources().getColor(R.color.black));
        return tv;
    }

    static class ViewHolder {
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        TextView mAppMemoryTV;
        CheckBox mCheckBox;

    }

}
