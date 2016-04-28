package com.tocong.mymobilesafe.chapter03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter03.entity.ContactInfo;

import java.util.List;

/**
 * Created by tocong on 2016/4/28.
 */
public class ContactAdapter extends BaseAdapter {
    private List<ContactInfo> contactInfos;
    private Context context;
    public ContactAdapter(List<ContactInfo> contactInfos, Context context) {
        this.contactInfos = contactInfos;
        this.context = context;
    }



    @Override
    public int getCount() {
        return contactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_list_contact_select,null);
            holder=new ViewHolder();
            holder.mNameTV= (TextView) convertView.findViewById(R.id.tv_name);
            holder.mPhoneTV= (TextView) convertView.findViewById(R.id.tv_phone);
            holder.mContactImgv= (ImageView) convertView.findViewById(R.id.view1);
            convertView.setTag(holder);
        }else{

            holder= (ViewHolder) convertView.getTag();
        }
        holder.mNameTV.setText(contactInfos.get(position).getName());
        holder.mPhoneTV.setText(contactInfos.get(position).getPhone());
holder.mContactImgv.setBackgroundResource(R.mipmap.brightpurple_contact_icon);
        return convertView;




    }


    static  class ViewHolder{
        TextView mNameTV;
        TextView mPhoneTV;
ImageView mContactImgv;
    }
}
