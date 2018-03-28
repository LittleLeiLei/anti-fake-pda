package com.chuxin.wechat.fake.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 码
 * Created by chao on 2018/3/19.
 */

public class Code implements Parcelable {

    public static final int TYPE_ITEM_CODE = 0;
    public static final int TYPE_BOX_CODE = 1;

    private String id = "";
    private String logisticsCode = "";
    private int type = TYPE_ITEM_CODE;

    private CodeInfo codeInfo;

    public Code(String logisticsCode, int type) {
        this.logisticsCode = logisticsCode;
        this.type = type;
    }

    protected Code(Parcel in) {
        id = in.readString();
        logisticsCode = in.readString();
        type = in.readInt();
        codeInfo = in.readParcelable(CodeInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(logisticsCode);
        dest.writeInt(type);
        dest.writeParcelable(codeInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Code> CREATOR = new Creator<Code>() {
        @Override
        public Code createFromParcel(Parcel in) {
            return new Code(in);
        }

        @Override
        public Code[] newArray(int size) {
            return new Code[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return getType() == TYPE_ITEM_CODE ? "单码" : "箱码";
    }

    public CodeInfo getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(CodeInfo codeInfo) {
        this.codeInfo = codeInfo;
    }

    public static List<String> resolve2LogisticsCodes(List<? extends Code> codes) {
        List<String> logisticsCodes = new ArrayList<>();
        for (Code code : codes) {
            logisticsCodes.add(code.getLogisticsCode());
        }
        return logisticsCodes;
    }

    public static List<Code> convert2Code(List<String> logisticsCodes, int type) {
        List<Code> codes = new ArrayList<>();
        for (String code : logisticsCodes) {
            codes.add(new Code(code, type));
        }
        return codes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Code) {
            return TextUtils.equals(((Code)obj).getLogisticsCode(), getLogisticsCode());
        }
        return false;
    }
}
