package com.chuxin.wechat.fake.api.body;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chao on 2018/3/17.
 */

public class UnpackBody {
    private List<String> boxLogisticsCodes = new ArrayList<>();

    public UnpackBody(List<String> boxLogisticsCodes) {
        this.boxLogisticsCodes = boxLogisticsCodes;
    }

    public List<String> getBoxLogisticsCodes() {
        return boxLogisticsCodes;
    }

    public void setBoxLogisticsCodes(List<String> boxLogisticsCodes) {
        this.boxLogisticsCodes = boxLogisticsCodes;
    }
}
