package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发货记录
 * Created by chao on 2018/3/26.
 */

public class StockRecord {

    private CodeDetail codes;

    private String id;
    private String formatId;
    private int count;

    public CodeDetail getCodes() {
        return codes;
    }

    public void setCodes(CodeDetail codes) {
        this.codes = codes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class CodeDetail {
        List<Code> itemCodes = new ArrayList<>();
        List<Code> boxCodes = new ArrayList<>();

        public List<Code> getItemCodes() {
            return itemCodes;
        }

        public void setItemCodes(List<Code> itemCodes) {
            this.itemCodes = itemCodes;
        }

        public List<Code> getBoxCodes() {
            return boxCodes;
        }

        public void setBoxCodes(List<Code> boxCodes) {
            this.boxCodes = boxCodes;
        }
    }
}
