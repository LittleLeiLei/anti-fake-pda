package com.chuxin.wechat.fake.views.base;

import com.chuxin.wechat.fake.scanner.ScannerWrapper;

/**
 *
 * Created by chao on 2018/3/17.
 */

public abstract class BaseScanActivity extends BaseActivity implements ScannerWrapper.ScannerObserver {

    @Override
    protected void onResume() {
        super.onResume();
        ScannerWrapper.instance().subscribe(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerWrapper.instance().unsubscribe(this);
    }
}
