package com.chuxin.wechat.fake.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.adapter.ItemCodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;
import com.chuxin.wechat.fake.views.ui.CellView;

import java.util.List;

import butterknife.BindView;

/**
 * 箱码查询单码
 * Created by chao on 2018/3/18.
 */

public class QueryBoxCodeActivity extends BaseScanActivity {

    @BindView(R.id.rv_item_code)
    RecyclerView mItemCodeView;

    @BindView(R.id.cv_cell_box_code)
    CellView mBoxCodeCell;

    @BindView(R.id.cv_cell_hint)
    CellView mHintCell;

    @BindView(R.id.tv_empty_tips)
    TextView mEmptyText;

    private CodeAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_box_code;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        mAdapter = new CodeAdapter(false);

        mItemCodeView.setLayoutManager(new LinearLayoutManager(this));
        mItemCodeView.setAdapter(mAdapter);
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
        return R.string.text_box_code_to_item_code;
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
    public void onScanned(String data) {
        checkBoxCode(data);
    }

    public void checkBoxCode(final String code) {
        CodeApiWrapper.isBoxCode(code, new SimpleObserver<Boolean, Object>() {
            @Override
            public void onSuccess(Boolean isCorrect) {
                if (isCorrect) {
                    SoundWrapper.get().playSuccess();
                    resolveBoxCode(code);
                } else {
                    SoundWrapper.get().playAlert();
                    toast(String.format("%s 不是正确的箱码, 请核对后重新扫描", code));
                }
            }

            @Override
            public void onFailure(@Nullable Object data, String message) {
                super.onFailure(data, message);
                SoundWrapper.get().playAlert();
            }
        });
    }

    private void resolveBoxCode(String code) {
        showLoadingDialog();
        mBoxCodeCell.setValue(code);
        CodeApiWrapper.getItemCodeByBoxCode(code, new SimpleObserver<List<Code>, Object>() {
            @Override
            public void onSuccess(List<Code> data) {
                super.onSuccess(data);
                mHintCell.setVisibility(View.VISIBLE);
                mEmptyText.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
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
}
