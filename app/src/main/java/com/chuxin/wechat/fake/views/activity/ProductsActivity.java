package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.ProductApiWrapper;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ListWrapper;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.views.adapter.BaseRecyclerAdapter;
import com.chuxin.wechat.fake.views.adapter.FormatAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;

import butterknife.BindView;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class ProductsActivity extends BaseActivity {

    @BindView(R.id.rv_products)
    RecyclerView mProductView;

//    @BindView(R.id.ptr_wrapper)
//    PtrLayout mPtrWrapper;

    private FormatAdapter mAdapter;

    private Format mFormat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_products;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFormat = getIntent().getParcelableExtra(Constant.Key.KEY_FORMAT);
    }

    @Override
    protected void initView() {
        mAdapter = new FormatAdapter();
        mProductView.setLayoutManager(new LinearLayoutManager(this));
        mProductView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mProductView.setAdapter(mAdapter);

        if (mFormat != null) {
            mAdapter.setSelected(mFormat.getId());
        }

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Format>() {
            @Override
            public void onItemClick(View view, Format data, int pos) {
                Intent intent = new Intent();
                intent.putExtra(Constant.Key.KEY_FORMAT, data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ProductApiWrapper.getFormats(-1, 0, new SimpleObserver<ListWrapper<Format>, Object>() {
            @Override
            public void onSuccess(ListWrapper<Format> data) {
                mAdapter.setData(data.getRows());
                hideLoadingDialog();
            }

            @Override
            public void onFailure(@Nullable Object data, String message) {
                super.onFailure(data, message);
                hideLoadingDialog();
            }
        });
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.text_package_product;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected void onBackClicked(View view) {
        onBackPressed();
    }
}
