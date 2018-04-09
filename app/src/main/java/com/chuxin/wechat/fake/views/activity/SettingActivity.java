package com.chuxin.wechat.fake.views.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.utils.ACache;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by chao on 2018/4/9.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.sb_switch)
    SwitchButton mDevSwitch;

    private int isDebug = 0;
    final int IS_DEBUG = 1;
    final int IS_RELEASE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            String debugStr = ACache.get(this).getAsString("IS_DEBUG");
            isDebug = debugStr != null ? Integer.parseInt(debugStr) : IS_RELEASE;
        } catch (Exception e) {
            isDebug = IS_RELEASE;
        }
        mDevSwitch.setChecked(isDebug == IS_DEBUG);
    }

    @Override
    protected void initView() {
        mDevSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logger.e(isChecked + "");
                ACache.get(SettingActivity.this).put("IS_DEBUG", String.valueOf(isChecked ? IS_DEBUG : IS_RELEASE));
                logout();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.text_setting;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected void onBackClicked(View view) {
        onBackPressed();
    }

    @OnClick(R.id.ll_logout)
    public void logout() {
        MyApp.get().logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        resolveIntent(intent);
        toast(getString(R.string.text_logout_successfully));
        RetrofitWrapper.reset();
    }
}
