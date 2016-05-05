package com.tocong.mymobilesafe.chapter06.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return null;
    }


    static class  ViewHolder{


    }
}
