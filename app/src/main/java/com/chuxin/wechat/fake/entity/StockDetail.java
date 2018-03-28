package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发货明细
 * Created by chao on 2018/3/26.
 */

public class StockDetail {

    /**
     * id : 4cf16bc0-2e7d-11e8-8163-79daaef41100
     * code : OUT-1803231733-000001
     * maker : 草坪
     * passer : null
     * stockType : 销售出库
     * action : 0
     * logisticsId : 4cdf9170-2e7d-11e8-8163-79daaef41100
     * warehouseId : 1
     * state : 0
     */

    private String id;
    private String code;
    private String maker;
    private Object passer;
    private String stockType;
    private int action;
    private String logisticsId;
    private String warehouseId;
    private int state;
    private List<StockRecord> stockRecords = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public Object getPasser() {
        return passer;
    }

    public void setPasser(Object passer) {
        this.passer = passer;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<StockRecord> getStockRecords() {
        return stockRecords;
    }

    public void setStockRecords(List<StockRecord> stockRecords) {
        this.stockRecords = stockRecords;
    }
}
