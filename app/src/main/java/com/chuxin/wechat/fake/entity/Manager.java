package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 管理员(登录用户)
 * Created by chao on 2018/3/12.
 */

public class Manager {
    private String id = "";
    private String account = "";
    private String name = "";
    private String phone = "";
    private boolean enablePhone = false;
    private String qq = "";
    private Role fakeRole = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnablePhone() {
        return enablePhone;
    }

    public void setEnablePhone(boolean enablePhone) {
        this.enablePhone = enablePhone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Role getFakeRole() {
        return fakeRole;
    }

    public void setFakeRole(Role fakeRole) {
        this.fakeRole = fakeRole;
    }
}
