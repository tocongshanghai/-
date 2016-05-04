package com.tocong.mymobilesafe.chapter05.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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




        }else {
            viewHolder=convertView.getTag();

        }


        return null;
    }

    static class ViewHolder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        ImageView mScanIconImgv;

    }
}
