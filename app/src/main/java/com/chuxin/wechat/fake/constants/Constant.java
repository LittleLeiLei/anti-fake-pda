package com.chuxin.wechat.fake.constants;

/**
 * 常量
 * Created by chao on 2018/3/12.
 */

public class Constant {

    public static class Key {
        public static final String KEY_CACHE_USER = "key_cache_user";

        public static final String KEY_FORMAT = "KEY_FORMAT";
        public static final String KEY_ITEM_BATCH = "KEY_ITEM_BATCH";
        public static final String KEY_ITEM_CODES = "KEY_ITEM_CODES";
        public static final String KEY_WAREHOUSE = "KEY_WAREHOUSE";
        public static final String KEY_ORDER_FORMAT_CODES = "KEY_ORDER_FORMAT_CODES";
        public static final String KEY_IS_UNDELIVERED = "KEY_IS_UNDELIVERED";
    }


    public static class Code {
        public static final int REQUEST_CODE_FORMAT = 0x100;
        public static final int REQUEST_CODE_ITEM_BATCH = 0x101;
        public static final int REQUEST_CODE_ITEM_BATCH_SCAN = 0x102;
        public static final int REQUEST_CODE_WAREHOUSE = 0X103;
        public static final int REQUEST_CODE_ORDER_FORMAT_CODES = 0X104;
    }
}
