package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.WarehouseApiWrapper;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.entity.Warehouse;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.views.adapter.BaseRecyclerAdapter;
import com.chuxin.wechat.fake.views.adapter.WarehouseAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * 仓库列表
 * Created by chao on 2018/3/15.
 */

public class WarehousesActivity extends BaseActivity {

    @BindView(R.id.rv_warehouse)
    RecyclerView mWarehouseView;

    private Warehouse mWarehouse;
    private WarehouseAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_warehouse;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mWarehouse = getIntent().getParcelableExtra(Constant.Key.KEY_WAREHOUSE);
    }

    @Override
    protected void initView() {
        mAdapter = new WarehouseAdapter();
        mWarehouseView.setLayoutManager(new LinearLayoutManager(this));
        mWarehouseView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mWarehouseView.setAdapter(mAdapter);

        if (mWarehouse != null) {
            mAdapter.setSelected(mWarehouse.getId());
        }

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Warehouse>() {
            @Override
            public void onItemClick(View view, Warehouse data, int pos) {
                Intent intent = new Intent();
                intent.putExtra(Constant.Key.KEY_WAREHOUSE, data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        WarehouseApiWrapper.getWarehouses(new SimpleObserver<List<Warehouse>, Object>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                mAdapter.setData(data);
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
        return R.string.text_deliver_from_warehouse;
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
