package com.tocong.mymobilesafe.chapter04.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter04.entity.AppInfo;
import com.tocong.mymobilesafe.chapter04.utils.DensityUtil;

import java.util.Formatter;
import java.util.List;

/**
 * Created by tocong on 2016/4/30.
 */
public class AppManagerAdapter extends BaseAdapter {
    private List<AppInfo> UserAppInfos;
    private List<AppInfo> systemAppInfos;
    Context context;

    public AppManagerAdapter(Context context, List<AppInfo> userAppInfos, List<AppInfo> systemAppInfos) {
        this.context = context;
        this.UserAppInfos = userAppInfos;
        this.systemAppInfos = systemAppInfos;

    }

    @Override
    public int getCount() {
        return systemAppInfos.size() + UserAppInfos.size() + 2;
        //因为有两个条目需要用于显示用户进程，系统进程因此要+2
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null;
        } else if (position == (UserAppInfos.size() + 1)) {
            return null;
        }

        AppInfo appInfo;
        if (position < (UserAppInfos.size() + 1)) {
            appInfo = UserAppInfos.get(position - 1);

        } else {
            int location = position - UserAppInfos.size() - 2;
            appInfo = systemAppInfos.get(location);

        }
        return appInfo;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            TextView tv = getTextView();
            tv.setText("用户程序" + UserAppInfos.size() + "个");
            return tv;

        } else if (position == (UserAppInfos.size() + 1)) {
            TextView tv = getTextView();
            tv.setText("系统程序" + systemAppInfos.size() + "个");
            return tv;
        }
        //获取当前app的对象
        AppInfo appInfo;
        if (position < (UserAppInfos.size() + 1)) {
            appInfo = UserAppInfos.get(position - 1);

        } else {
            appInfo = systemAppInfos.get(position - UserAppInfos.size() - 2);

        }
        ViewHolder viewHolder = null;
        if (convertView != null & convertView instanceof LinearLayout) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {

            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_appmanager_list, null);
            viewHolder.mAppIconImgv = (ImageView) convertView.findViewById(R.id.imagv_appicon);
            viewHolder.mAppLocationTV = (TextView) convertView.findViewById(R.id.tv_appisroom);
            viewHolder.mAppNameTV = (TextView) convertView.findViewById(R.id.tv_appname);
            viewHolder.mAppSizeTV = (TextView) convertView.findViewById(R.id.tv_appsize);
            viewHolder.mLaunchAppTV = (TextView) convertView.findViewById(R.id.tv_launch_app);
            viewHolder.mUninstallTV = (TextView) convertView.findViewById(R.id.tv_uninstall_app);
            viewHolder.mShareAppTV = (TextView) convertView.findViewById(R.id.tv_share_app);
            viewHolder.mSettingAppTV = (TextView) convertView.findViewById(R.id.tv_setting_app);
            viewHolder.mAppOptionll = (LinearLayout) convertView.findViewById(R.id.ll_option_app);

        }
        if (appInfo != null) {
            viewHolder.mAppLocationTV.setText(appInfo.getAppLocation(appInfo.isInRoom()));
            viewHolder.mAppIconImgv.setImageDrawable(appInfo.getIcon());
            viewHolder.mAppSizeTV.setText(android.text.format.Formatter.formatFileSize(context, appInfo.getAppSize()));
            viewHolder.mAppNameTV.setText(appInfo.getAppNmae());
            if (appInfo.isSelected()) {
                viewHolder.mAppOptionll.setVisibility(View.VISIBLE);


            } else {
                viewHolder.mAppOptionll.setVisibility(View.GONE);

            }



        }
        MyClickListener listener = new MyClickListener(appInfo);
        viewHolder.mLaunchAppTV.setOnClickListener(listener);
        viewHolder.mSettingAppTV.setOnClickListener(listener);
        viewHolder.mShareAppTV.setOnClickListener(listener);
        viewHolder.mUninstallTV.setOnClickListener(listener);
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
        TextView mLaunchAppTV;
        TextView mUninstallTV;
        TextView mShareAppTV;
        TextView mSettingAppTV;
        ImageView mAppIconImgv;
        TextView mAppLocationTV;
        TextView mAppSizeTV;
        TextView mAppNameTV;
        LinearLayout mAppOptionll;

    }

    class MyClickListener implements View.OnClickListener {

        private AppInfo appInfo;

        public MyClickListener(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_launch_app:
                    break;
                case R.id.tv_uninstall_app:
                    break;
                case R.id.tv_share_app:
                    break;
                case R.id.tv_setting_app:
                    break;

            }
        }
    }

}
