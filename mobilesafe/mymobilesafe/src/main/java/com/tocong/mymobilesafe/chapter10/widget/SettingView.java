package com.tocong.mymobilesafe.chapter10.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tocong.mymobilesafe.R;

/**
 * Created by taoo on 2016/5/16.
 */
public class SettingView extends RelativeLayout {
    private String setTitle = "";
    private String status_on = "";
    private String status_off = "";
    private TextView mSettingTitleTV;
    private TextView mSettingStatusTV;
    private ToggleButton mToggleBtn;
    private boolean isChecked;
private OnCheckedStatusIsChanged onCheckedStatusIsChanged;

    public SettingView(Context context) {
        super(context);
        initView(context);

    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs,R.styleable.SettingView);
        setTitle=mTypedArray.getString(R.styleable.SettingView_settitle);
        status_on = mTypedArray.getString(R.styleable.SettingView_status_on);
        status_off = mTypedArray.getString(R.styleable.SettingView_status_off);
        isChecked = mTypedArray.getBoolean(R.styleable.SettingView_status_ischecked,false);
mTypedArray.recycle();
        initView(context);
        setStatus(status_on,status_off,isChecked());
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /*
    * 初始化空间
    * */

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.ui_setting_view, null);

        this.addView(view);
        mSettingTitleTV = (TextView) findViewById(R.id.tv_setting_title);
        mSettingStatusTV = (TextView) findViewById(R.id.tv_setting_status);
        mToggleBtn = (ToggleButton) findViewById(R.id.toggle_setting_status);
        mSettingTitleTV.setText(setTitle);
        mToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setChecked(isChecked);
                onCheckedStatusIsChanged.onCheckedChanged(SettingView.this,isChecked);
            }
        });

    }

/*
* 返回空间是否选中
* */

    public boolean isChecked() {
        return mToggleBtn.isChecked();

    }

    /*
    * 设置组合控件的选中方式
    * */
    public void setChecked(boolean checked) {
        mToggleBtn.setChecked(checked);
        isChecked = checked;
        if (checked) {
            if (!TextUtils.isEmpty(status_on)) {
                mSettingStatusTV.setText(status_on);
            }


        } else {
            if (!TextUtils.isEmpty(status_off)) {
                mSettingStatusTV.setText(status_off);
            }
        }

    }
/*
* 设置自定义控件的描述
* */
    public void setStatus(String status_on,String status_off,boolean checked){
        if(checked){
            mSettingStatusTV.setText(status_on);

        }else {
            mSettingStatusTV.setText(status_off);
        }
        mToggleBtn.setChecked(checked);

    }

    public void setOnCheckedStatusIsChanged(OnCheckedStatusIsChanged onCheckedStatusIsChanged){
        this.onCheckedStatusIsChanged=onCheckedStatusIsChanged;

    }

    public interface  OnCheckedStatusIsChanged{

        void onCheckedChanged(View view,boolean isChecked);
    }
}
