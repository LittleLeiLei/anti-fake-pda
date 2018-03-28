package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * Created by chao on 2018/3/16.
 */

public class Order {

    public static final int STATE_DELIVERED = 1;
    public static final int STATE_OBSOLETE = -1;

    private String id = "";
    private String code = "";
    private String remark = "";
    private int orderState = 0;
    private int price = 0;
    private Address address = null;
    private List<Product> products = new ArrayList<>();
    private List<Format> formats = new ArrayList<>();
    private List<StockDetail> stockDetails = new ArrayList<>();

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public List<StockDetail> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<StockDetail> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public boolean isDelivered() {
        return getOrderState() == 1;
    }

    public boolean isUndelivered() {
        return getOrderState() != 1 && getOrderState() != -1;
    }

    public String getOrderStateName() {
        return getOrderState() == STATE_DELIVERED ? "已发货" : getOrderState() == STATE_OBSOLETE ? "已作废" : "未发货";
    }
}
