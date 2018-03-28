package com.chuxin.wechat.fake.entity;

import java.util.List;

/**
 * 角色
 * Created by chao on 2018/3/18.
 */

public class Role {

    /**
     * auths : ["超级管理员"]
     * id : 3005d970-c82e-11e7-abdc-db1b16150f99
     * name : 超级管理员
     * system : wechat-anti-fake
     * createdAt : 2017-12-05T16:00:00.000Z
     * updatedAt : 2017-12-05T16:00:00.000Z
     */

    private String id;
    private String name;
    private String system;
    private List<String> auths;

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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<String> getAuths() {
        return auths;
    }

    public void setAuths(List<String> auths) {
        this.auths = auths;
    }
}
