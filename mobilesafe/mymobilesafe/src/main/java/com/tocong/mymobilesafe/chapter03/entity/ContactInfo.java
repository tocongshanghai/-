package com.tocong.mymobilesafe.chapter03.entity;

/**
 * Created by tocong on 2016/4/26.
 */
public class ContactInfo {

    private String id;
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }
}
