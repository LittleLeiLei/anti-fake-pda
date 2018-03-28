package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 产品批次
 * Created by chao on 2018/3/14.
 */

public class ItemBatch implements Parcelable {

    /**
     * id : 1
     * name : 1234
     * code : 1234
     * formatId : 32abbac4-49ab-11e7-af61-4d15670d70af
     * company : 12345
     * region : 北京
     * producedAt : null
     * itemScale : 5
     * maker : null
     * remark : null
     */

    private String id;
    private String name;
    private String code;
    private String formatId;
    private String company;
    private String region;
    private Object producedAt;
    private int itemScale;
    private Object maker;
    private Object remark;

    protected ItemBatch(Parcel in) {
        id = in.readString();
        name = in.readString();
        code = in.readString();
        formatId = in.readString();
        company = in.readString();
        region = in.readString();
        itemScale = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(formatId);
        dest.writeString(company);
        dest.writeString(region);
        dest.writeInt(itemScale);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemBatch> CREATOR = new Creator<ItemBatch>() {
        @Override
        public ItemBatch createFromParcel(Parcel in) {
            return new ItemBatch(in);
        }

        @Override
        public ItemBatch[] newArray(int size) {
            return new ItemBatch[size];
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

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Object getProducedAt() {
        return producedAt;
    }

    public void setProducedAt(Object producedAt) {
        this.producedAt = producedAt;
    }

    public int getItemScale() {
        return itemScale;
    }

    public void setItemScale(int itemScale) {
        this.itemScale = itemScale;
    }

    public Object getMaker() {
        return maker;
    }

    public void setMaker(Object maker) {
        this.maker = maker;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }
}
