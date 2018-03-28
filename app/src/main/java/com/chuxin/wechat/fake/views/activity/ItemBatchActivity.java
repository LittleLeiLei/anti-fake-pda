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
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.entity.ListWrapper;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.views.adapter.BaseRecyclerAdapter;
import com.chuxin.wechat.fake.views.adapter.ItemBatchAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;

import butterknife.BindView;

/**
 * 选择产品批次
 * Created by chao on 2018/3/14.
 */

public class ItemBatchActivity extends BaseActivity {

    @BindView(R.id.rv_item_batch)
    RecyclerView mItemBatchView;

    private ItemBatchAdapter mAdapter;
    private Format mFormat;
    private ItemBatch mItemBatch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_batch;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFormat = getIntent().getParcelableExtra(Constant.Key.KEY_FORMAT);
        mItemBatch = getIntent().getParcelableExtra(Constant.Key.KEY_ITEM_BATCH);
    }

    @Override
    protected void initView() {
        mAdapter = new ItemBatchAdapter();

        mItemBatchView.setLayoutManager(new LinearLayoutManager(this));
        mItemBatchView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mItemBatchView.setAdapter(mAdapter);

        if (mItemBatch != null) {
            mAdapter.setSelected(mItemBatch.getId());
        }

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ItemBatch>() {
            @Override
            public void onItemClick(View view, ItemBatch data, int pos) {
                Intent intent = new Intent();
                intent.putExtra(Constant.Key.KEY_ITEM_BATCH, data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ProductApiWrapper.getItemBatches(mFormat.getId(), -1, 0, new SimpleObserver<ListWrapper<ItemBatch>, Object>() {
            @Override
            public void onSuccess(ListWrapper<ItemBatch> data) {
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
        return R.string.text_package_itembatch;
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
