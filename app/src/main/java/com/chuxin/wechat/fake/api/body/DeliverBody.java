package com.chuxin.wechat.fake.api.body;

import android.support.annotation.NonNull;

import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * 发货body参数
 * Created by chao on 2018/3/17.
 */

public class DeliverBody {

    private String warehouseId = "";
    private Codes codes = new Codes();

    public DeliverBody(@NonNull String warehouseId, List<String> itemCodes, List<String> boxCodes) {
        this.warehouseId = warehouseId;
        codes.boxCodes = boxCodes;
        codes.itemCodes = itemCodes;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Codes getCodes() {
        return codes;
    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }

    static class Codes {
        private List<String> boxCodes = new ArrayList<>();
        private List<String> itemCodes = new ArrayList<>();
    }
}
