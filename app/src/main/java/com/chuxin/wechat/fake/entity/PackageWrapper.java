package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 装箱
 * Created by chao on 2018/3/15.
 */

public class PackageWrapper {
    private String boxLogisticsCode = "";
    private ArrayList<String> itemLogisticsCodes = new ArrayList<>();

    public PackageWrapper() {}

    public String getBoxLogisticsCode() {
        return boxLogisticsCode;
    }

    public void setBoxLogisticsCode(String boxLogisticsCode) {
        this.boxLogisticsCode = boxLogisticsCode;
    }

    public ArrayList<String> getItemLogisticsCodes() {
        return itemLogisticsCodes;
    }

    public void setItemLogisticsCodes(ArrayList<String> itemLogisticsCodes) {
        this.itemLogisticsCodes = itemLogisticsCodes;
    }
}
