package com.chuxin.wechat.fake.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 箱码
 * Created by chao on 2018/3/17.
 */

public class BoxCode extends Code {

    public BoxCode(String logisticsCode) {
        super(logisticsCode, Code.TYPE_BOX_CODE);
    }

    public static List<Code> convert2BoxCode(List<String> logisticsCodes) {
        List<Code> codes = new ArrayList<>();
        for (String code : logisticsCodes) {
            codes.add(new BoxCode(code));
        }
        return codes;
    }
}
