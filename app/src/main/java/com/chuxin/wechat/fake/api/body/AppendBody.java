package com.chuxin.wechat.fake.api.body;

import java.util.ArrayList;
import java.util.List;

/**
 * 补标body
 * Created by chao on 2018/3/17.
 */

public class AppendBody {
    private String boxLogisticsCode = "";
    private List<String> itemLogisticsCodes = new ArrayList<>();

    public AppendBody(String boxLogisticsCode, List<String> itemLogisticsCodes) {
        this.boxLogisticsCode = boxLogisticsCode;
        this.itemLogisticsCodes = itemLogisticsCodes;
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
