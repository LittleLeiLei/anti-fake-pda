package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 规格
 * Created by chao on 2018/3/13.
 */

public class Format implements Parcelable {
    private String id = "";
    private String name = "";
    private String code = "";
    private Product product = null;
    private int price = 0;
    private int count = 0;
    private int packageCount = 0;

    protected Format(Parcel in) {
        id = in.readString();
        name = in.readString();
        code = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
        price = in.readInt();
        count = in.readInt();
        packageCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeParcelable(product, flags);
        dest.writeInt(price);
        dest.writeInt(count);
        dest.writeInt(packageCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Format> CREATOR = new Creator<Format>() {
        @Override
        public Format createFromParcel(Parcel in) {
            return new Format(in);
        }

        @Override
        public Format[] newArray(int size) {
            return new Format[size];
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(int packageCount) {
        this.packageCount = packageCount;
    }

    public String getFullName() {
        return getProduct().getName() + "(" + getName() + ")";
    }

    public int getMinUnitCount() {
        return getProduct().getSoldUnit().getNumber() * getCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Format){
            if (TextUtils.equals(((Format) obj).getId(), getId())) {
                return true;
            }
        }
        return false;
    }
}
