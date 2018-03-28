package com.chuxin.wechat.fake.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 扫描枪广播接收器
 * Created by chao on 2018/3/15.
 */

public class ScannerReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.android.service_scanner_appdata";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), ACTION)) {
            ScannerWrapper.instance().publish(intent.getStringExtra("data"));
        }
    }
}
