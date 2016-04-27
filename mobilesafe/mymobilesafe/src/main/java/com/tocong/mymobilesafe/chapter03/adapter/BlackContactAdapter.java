package com.tocong.mymobilesafe.chapter03.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tocong.mymobilesafe.R;
import com.tocong.mymobilesafe.chapter03.db.dao.BlackNumberDao;
import com.tocong.mymobilesafe.chapter03.entity.BlackContactInfo;
import com.tocong.mymobilesafe.chapter03.entity.ContactInfo;

import java.util.List;

/**
 * Created by tocong on 2016/4/27.
 */
public class BlackContactAdapter extends BaseAdapter {
    List<BlackContactInfo> blackContactInfos;
    private Context context;
    private BlackNumberDao dao;
    private BlackContactCallBack callBack;

    public void setCallBack(BlackContactCallBack callBack) {
        this.callBack = callBack;
    }

    public BlackContactAdapter(List<BlackContactInfo> blackContactInfos, Context context) {
        this.blackContactInfos = blackContactInfos;
        this.context = context;
        dao = new BlackNumberDao(context);
    }

    @Override
    public int getCount() {
        return blackContactInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return blackContactInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list_blackcontact, null);
            viewHolder = new ViewHolder();
            viewHolder.mNameTV = (TextView) convertView.findViewById(R.id.tv_black_name);
            viewHolder.mModeTV = (TextView) convertView.findViewById(R.id.tv_black_mode);
            viewHolder.mContactImgv = (ImageView) convertView.findViewById(R.id.view_black_icon);
            viewHolder.mDeleteImagv = (ImageView) convertView.findViewById(R.id.view_black_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.mNameTV.setText(blackContactInfos.get(position).getContactName() + "(" + blackContactInfos.get(position).getPhoneNumber() + ")");
        viewHolder.mModeTV.setText(blackContactInfos.get(position).getModeString(blackContactInfos.get(position).getMode()));
        viewHolder.mContactImgv.setBackgroundResource(R.mipmap.brightpurple_contact_icon);
        viewHolder.mDeleteImagv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean detele = dao.delete(blackContactInfos.get(position));
                if (detele) {
                    blackContactInfos.remove(blackContactInfos.get(position));
                    BlackContactAdapter.this.notifyDataSetChanged();
                    //如果数据库中没有数据了，则执行回调函数
                    if (dao.getTotalNumber() == 0) {
                        callBack.DataSizeChanged();
                    }
                }else {
                    Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();

                }

            }
        });
        return convertView;
    }


    public interface BlackContactCallBack {
        void DataSizeChanged();

    }

    class ViewHolder {
        TextView mNameTV;
        TextView mModeTV;
        ImageView mContactImgv;
        ImageView mDeleteImagv;

    }

}


