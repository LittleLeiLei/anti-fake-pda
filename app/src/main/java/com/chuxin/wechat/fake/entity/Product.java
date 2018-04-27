package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品
 * Created by chao on 2018/3/13.
 */

public class Product implements Parcelable {

    private String id = "";
    private String name = "";
    private String code = "";
    private String minUnit = "";
    private Unit soldUnit = null;
    private boolean antiFake = true;
    private List<Format> formats = new ArrayList<>();

    public Product() {}

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        code = in.readString();
        minUnit = in.readString();
        formats = in.createTypedArrayList(Format.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(minUnit);
        dest.writeTypedList(formats);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(String minUnit) {
        this.minUnit = minUnit;
    }

    public Unit getSoldUnit() {
        return soldUnit;
    }

    public void setSoldUnit(Unit soldUnit) {
        this.soldUnit = soldUnit;
    }

    public boolean isAntiFake() {
        return antiFake;
    }

    public void setAntiFake(boolean antiFake) {
        this.antiFake = antiFake;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }
}
