package com.chuxin.wechat.fake.entity;

/**
 * Created by chao on 2018/3/16.
 */

public class Address {

    /**
     * id : 9f38f500-5279-11e7-a244-231943fda26a
     * name : 黄XX
     * phone : 13880465242
     * regionId : 2877
     * detail : c12
     * fullAddress : 广东 广州市 广州大学城 c12
     */

    private String id;
    private String name;
    private String phone;
    private String regionId;
    private String detail;
    private String fullAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
