package com.tocong.mymobilesafe.chatper01.adapyer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;

/**
 * Created by tocong on 2016/4/22.
 */
public class HomeAdapter extends BaseAdapter {

    int[] imagedId = new int[]{R.mipmap.safe, R.mipmap.callmsgsafe, R.mipmap.app,
            R.mipmap.trojan, R.mipmap.sysoptimize, R.mipmap.taskmanager,
            R.mipmap.netmanager, R.mipmap.atools, R.mipmap.settings};
    String[] names = new String[]{"手机防盗", "通讯卫士", "软件管家",
            "手机杀毒", "缓存清理", "进程管理",
            "流量统计", "高级工具", "设置中心"};
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return imagedId[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_home, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        TextView textView = (TextView) view.findViewById(R.id.tv_name);
        imageView.setImageResource(imagedId[position]);
        textView.setText(names[position]);
        return view;


    }
}
