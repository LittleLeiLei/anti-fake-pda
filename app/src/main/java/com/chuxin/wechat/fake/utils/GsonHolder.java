package com.chuxin.wechat.fake.utils;

import com.google.gson.Gson;

/**
 *
 * Created by chao on 2018/3/18.
 */

public class GsonHolder {

    private static class Holder {
        private static Gson INSTANCE = new Gson();
    }

    public static Gson get() {
        return Holder.INSTANCE;
    }
}
