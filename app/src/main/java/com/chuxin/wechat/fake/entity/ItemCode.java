package com.chuxin.wechat.fake.entity;

/**
 * 单码
 * Created by chao on 2018/3/17.
 */

public class ItemCode extends Code {
    private BoxCode boxCode = null;

    public ItemCode(String logisticsCode) {
        super(logisticsCode, Code.TYPE_ITEM_CODE);
    }

    public BoxCode getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(BoxCode boxCode) {
        this.boxCode = boxCode;
    }
}
