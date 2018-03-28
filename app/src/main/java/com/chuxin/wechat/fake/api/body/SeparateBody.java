package com.chuxin.wechat.fake.api.body;

import java.util.ArrayList;
import java.util.List;

/**
 * 分离单标body
 * Created by chao on 2018/3/17.
 */

public class SeparateBody {
    private List<String> itemLogisticsCodes = new ArrayList<>();

    public SeparateBody(List<String> itemLogisticsCodes) {
        this.itemLogisticsCodes = itemLogisticsCodes;
    }

    public List<String> getItemLogisticsCodes() {
        return itemLogisticsCodes;
    }

    public void setItemLogisticsCodes(List<String> itemLogisticsCodes) {
        this.itemLogisticsCodes = itemLogisticsCodes;
    }
}
