package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 仓库
 * Created by chao on 2018/3/15.
 */

public class Warehouse implements Parcelable {
    private String id = "";
    private String name = "";

    protected Warehouse(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Warehouse> CREATOR = new Creator<Warehouse>() {
        @Override
        public Warehouse createFromParcel(Parcel in) {
            return new Warehouse(in);
        }

        @Override
        public Warehouse[] newArray(int size) {
            return new Warehouse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Warehouse) {
            return TextUtils.equals(((Warehouse)obj).getId(), getId());
        }
        return false;
    }
}
