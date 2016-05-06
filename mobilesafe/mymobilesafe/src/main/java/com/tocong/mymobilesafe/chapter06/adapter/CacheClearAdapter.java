package com.tocong.mymobilesafe.chapter06.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter06.entity.CacheInfo;

import java.util.List;

/**
 * Created by tocong on 2016/5/5.
 */
public class CacheClearAdapter extends BaseAdapter {
    private Context context;
    private List<CacheInfo> cacheInfos;

    public CacheClearAdapter(List<CacheInfo> cacheInfos, Context context) {
        this.cacheInfos = cacheInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cacheInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return cacheInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_cacheclean,null);
            viewHolder.mAppIconImgv= (ImageView) convertView.findViewById(R.id.imgv_appicon_cacheclean);
            viewHolder.mAppNameTV= (TextView) convertView.findViewById(R.id.tv_appname_cacheclean);
            viewHolder.mCacheSizeTV= (TextView) convertView.findViewById(R.id.tv_appsize_cacheclean);
            convertView.setTag(viewHolder);
        }else{

            viewHolder= (ViewHolder) convertView.getTag();
        }
        CacheInfo cacheInfo=cacheInfos.get(position);
        viewHolder.mAppIconImgv.setImageDrawable(cacheInfo.getAppIncon());
        viewHolder.mAppNameTV.setText(cacheInfo.getAppName());
        viewHolder.mCacheSizeTV.setText(Formatter.formatFileSize(context,cacheInfo.getCacheSize()));
        return convertView;
    }


    static class ViewHolder {
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        TextView mCacheSizeTV;
    }
}
