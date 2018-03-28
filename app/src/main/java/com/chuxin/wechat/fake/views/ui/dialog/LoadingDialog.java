package com.chuxin.wechat.fake.views.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chuxin.wechat.fake.R;

import me.wangyuwei.slackloadingview.SlackLoadingView;

/**
 *
 * Created by chao on 2018/3/19.
 */

public class LoadingDialog extends Dialog {

    SlackLoadingView mLoadingView;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.LoadingDialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setCancelable(false);
        setContentView(R.layout.dialog_loading);
        mLoadingView = findViewById(R.id.slv_loading);
    }

    @Override
    public void show() {
        super.show();
        mLoadingView.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoadingView.reset();
    }
}
