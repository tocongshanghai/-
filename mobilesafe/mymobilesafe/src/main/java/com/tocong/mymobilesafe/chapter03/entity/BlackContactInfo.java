package com.tocong.mymobilesafe.chapter03.entity;

/**
 * Created by tocong on 2016/4/26.
 */
public class BlackContactInfo  {
    private String phoneNumber; //黑名单号码
    private String contactName;  //联系人名称
    private  int mode;    //模式 ，1：电话拦截   2：短信拦截   3：都拦截

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public int getMode() {
        return mode;
    }
    public String getModeString( int mode){
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话，短信拦截";
        }

        return  "";
    }
}
