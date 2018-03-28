package com.chuxin.wechat.fake.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.views.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 * Created by chao on 2018/3/12.
 */

public class HomePageFragment extends BaseFragment {

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @OnClick(R.id.ll_package)
    public void direct2Package () {
        Intent intent = new Intent(IntentAction.PAGE_PACKAGE);
        resolveIntent(intent);
    }

    @OnClick(R.id.ll_deliver)
    public void direct2Deliver () {
        Intent intent = new Intent(IntentAction.PAGE_DELIVER);
        resolveIntent(intent);
    }

    @OnClick(R.id.ll_unpack)
    public void direct2Unpack () {
        Intent intent = new Intent(IntentAction.PAGE_UNPACK);
        resolveIntent(intent);
    }

    @OnClick(R.id.ll_separate)
    public void direct2Separate () {
        Intent intent = new Intent(IntentAction.PAGE_SEPARATE);
        resolveIntent(intent);
    }

    @OnClick(R.id.ll_supply)
    public void direct2Supply () {
        Intent intent = new Intent(IntentAction.PAGE_APPEND);
        resolveIntent(intent);
    }

    @OnClick(R.id.ll_query)
    public void direct2Query () {
        Intent intent = new Intent(IntentAction.PAGE_QUERY);
        resolveIntent(intent);
    }
}
