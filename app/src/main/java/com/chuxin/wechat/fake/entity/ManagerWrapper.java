package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class ManagerWrapper {

    private String token = "";
    private Manager manager = null;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
