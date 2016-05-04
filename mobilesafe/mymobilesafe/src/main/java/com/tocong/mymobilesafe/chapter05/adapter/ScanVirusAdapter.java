package com.tocong.mymobilesafe.chapter05.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter05.entity.ScanAppInfo;

import java.util.List;

/**
 * Created by tocong on 2016/5/4.
 */
public class ScanVirusAdapter extends BaseAdapter {
   private List<ScanAppInfo> mScanAppInfos;
    private Context context;

    public ScanVirusAdapter(Context context, List<ScanAppInfo> mScanAppInfos) {
        this.context = context;
        this.mScanAppInfos = mScanAppInfos;
    }

    @Override
    public int getCount() {
        return mScanAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_list_applock,null);
            viewHolder=new ViewHolder();
            viewHolder.mAppIconImgv= (ImageView) convertView.findViewById(R.id.imagv_appicon);
            viewHolder.mAppNameTV= (TextView) convertView.findViewById(R.id.tv_appname);
            viewHolder.mScanIconImgv= (ImageView) convertView.findViewById(R.id.imgv_lock);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

ScanAppInfo scanAppInfo=mScanAppInfos.get(position);
        if (!scanAppInfo.isVirus()){
            viewHolder.mScanIconImgv.setBackgroundResource(R.mipmap.blue_right_icon);
            viewHolder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.mAppNameTV.setText(scanAppInfo.getAppName());

        }else {
            viewHolder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.bright_red));
            viewHolder.mAppNameTV.setText(scanAppInfo.getAppName()+"("+scanAppInfo.getDescription()+")");

        }
        viewHolder.mAppIconImgv.setImageDrawable(scanAppInfo.getAppIcon());
        return convertView;
    }

    static class ViewHolder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        ImageView mScanIconImgv;

    }
}
