package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 码信息
 * Created by chao on 2018/3/16.
 */

public class CodeInfo implements Parcelable {
    private int count = 0;
    private int linkedCount = 0;
    private String type = "";
    private String code = "";
    private Format format = null;

    protected CodeInfo(Parcel in) {
        count = in.readInt();
        linkedCount = in.readInt();
        type = in.readString();
        code = in.readString();
        format = in.readParcelable(Format.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeInt(linkedCount);
        dest.writeString(type);
        dest.writeString(code);
        dest.writeParcelable(format, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CodeInfo> CREATOR = new Creator<CodeInfo>() {
        @Override
        public CodeInfo createFromParcel(Parcel in) {
            return new CodeInfo(in);
        }

        @Override
        public CodeInfo[] newArray(int size) {
            return new CodeInfo[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLinkedCount() {
        return linkedCount;
    }

    public void setLinkedCount(int linkedCount) {
        this.linkedCount = linkedCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public boolean isItemCode() {
        return TextUtils.equals(getType(), "itemCode");
    }

    public String getCodeName() {
        return isItemCode() ? "单码" : "箱码";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
