package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.views.base.BaseActivity;

import butterknife.OnClick;

/**
 * 查询入口
 * Created by chao on 2018/3/17.
 */

public class QueryActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_query;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

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
        return R.string.text_home_query;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected void onBackClicked(View view) {
        onBackPressed();
    }

    @OnClick(R.id.rl_item_to_box)
    public void queryItemCode() {
        Intent intent = new Intent(IntentAction.PAGE_QUERY_ITEM_CODE);
        resolveIntent(intent);
    }

    @OnClick(R.id.rl_box_to_item)
    public void queryBoxCode() {
        Intent intent = new Intent(IntentAction.PAGE_QUERY_BOX_CODE);
        resolveIntent(intent);
    }
}
