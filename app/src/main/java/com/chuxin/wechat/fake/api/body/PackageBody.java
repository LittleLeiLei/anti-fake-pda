package com.chuxin.wechat.fake.api.body;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 装箱body参数
 * Created by chao on 2018/3/17.
 */

public class PackageBody {
    private String itemBatchId = "";
    private String boxLogisticsCode = "";
    private List<String> itemLogisticsCodes = new ArrayList<>();

    public PackageBody(@NonNull String itemBatchId, @NonNull String boxLogisticsCode, @NonNull List<String> itemLogisticsCodes) {
        this.itemBatchId = itemBatchId;
        this.boxLogisticsCode = boxLogisticsCode;
        this.itemLogisticsCodes = itemLogisticsCodes;
    }

    public String getItemBatchId() {
        return itemBatchId;
    }

    public void setItemBatchId(String itemBatchId) {
        this.itemBatchId = itemBatchId;
    }

    public String getBoxLogisticsCode() {
        return boxLogisticsCode;
    }

    public void setBoxLogisticsCode(String boxLogisticsCode) {
        this.boxLogisticsCode = boxLogisticsCode;
    }

    public List<String> getItemLogisticsCodes() {
        return itemLogisticsCodes;
    }

    public void setItemLogisticsCodes(List<String> itemLogisticsCodes) {
        this.itemLogisticsCodes = itemLogisticsCodes;
    }
}
