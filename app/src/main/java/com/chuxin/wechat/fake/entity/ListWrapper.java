package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chao on 2018/3/14.
 */

public class ListWrapper<T> {
    private int count = 0;
    private List<T> rows = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
