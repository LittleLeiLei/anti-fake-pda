package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chao on 2018/3/17.
 */

public class DeliverResult {
    private List<String> errorCodes = new ArrayList<>();
    private List<ItemCode> repeatCodes = new ArrayList<>();

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public List<ItemCode> getRepeatCodes() {
        return repeatCodes;
    }

    public void setRepeatCodes(List<ItemCode> repeatCodes) {
        this.repeatCodes = repeatCodes;
    }
}
