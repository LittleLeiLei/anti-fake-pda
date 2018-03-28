package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.utils.GsonHolder;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单内码查看页面
 * Created by chao on 2018/3/19.
 */

public class OrderFormatCodeActivity extends BaseActivity {

    @BindView(R.id.rv_code)
    RecyclerView mCodeView;

    private boolean isUndelivered = true;
    private CodeAdapter mAdapter;
    private Format mFormat;
    private ArrayList<Code> mCodes = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_code;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mCodes = getIntent().getParcelableArrayListExtra(Constant.Key.KEY_ORDER_FORMAT_CODES);
        mFormat = getIntent().getParcelableExtra(Constant.Key.KEY_FORMAT);
        isUndelivered = getIntent().getBooleanExtra(Constant.Key.KEY_IS_UNDELIVERED, true);
    }

    @Override
    protected void initView() {
        setSecondaryButtonVisibility(isUndelivered ? View.VISIBLE : View.GONE);

        mAdapter = new CodeAdapter(isUndelivered);
        mAdapter.setData(mCodes);

        mCodeView.setLayoutManager(new LinearLayoutManager(this));
        mCodeView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCodeView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getToolbarTitle() {
        return R.string.text_code_detail;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected void onBackClicked(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        backWithCodes();
    }

    @Override
    protected boolean showSecondaryButtonText() {
        return true;
    }

    @Override
    protected int getSecondaryButtonText() {
        return R.string.text_reset_operation;
    }

    @Override
    protected void onSecondaryButtonClicked(View view) {
        mAdapter.clear();
    }

    private void backWithCodes() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.Key.KEY_ORDER_FORMAT_CODES, (ArrayList<Code>) mAdapter.getData());
        intent.putExtra(Constant.Key.KEY_FORMAT, mFormat);
        setResult(RESULT_OK, intent);
        finish();
    }
}
