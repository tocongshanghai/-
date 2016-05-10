package com.tocong.mymobilesafe.chapter09.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tocong.mymobilesafe.R;

/**
 * Created by yichunyan on 2016/5/10.
 */
public class AdvancedToolsView extends RelativeLayout{

    private TextView mDecriptionTV;
    private String desc="";
    private Drawable drawable;
    private ImageView mLeftImgv;
    public AdvancedToolsView(Context context) {
        super(context);
        init(context);
    }

    public AdvancedToolsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypeArray=context.obtainStyledAttributes(attrs,R.styleable.AdvancedToolsView);
        desc=mTypeArray.getString(R.styleable.AdvancedToolsView_desc);
        drawable=mTypeArray.getDrawable(R.styleable.AdvancedToolsView_android_src);
        mTypeArray.recycle();
        init(context);
    }

    public AdvancedToolsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /*
    * 初始化控件
    * */

    private void init(Context context){
//将资源转化成View对象显示在自己身上
        View view= View.inflate(context, R.layout.ui_advancedtools_view,null);
        this.addView(view);
        mDecriptionTV= (TextView) findViewById(R.id.tv_decription);
        mLeftImgv= (ImageView) findViewById(R.id.imgv_left);
        mDecriptionTV.setText(desc);
        if(drawable!=null){
            mLeftImgv.setImageDrawable(drawable);

        }

    }
}
