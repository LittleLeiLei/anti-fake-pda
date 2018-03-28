package com.chuxin.wechat.fake.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;
import com.chuxin.wechat.fake.views.ui.CellView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 补标
 * Created by chao on 2018/3/17.
 */

public class AppendActivity extends BaseScanActivity {

    // TODO: 2018/3/17 Code列表页面可考虑单独封装成Fragment
    private CodeAdapter mAdapter = new CodeAdapter();
//    private List<String> mItemCodes = new ArrayList<>();
    private String mBoxCode = "";

    @BindView(R.id.rv_item_code)
    RecyclerView mItemCodeView;

    @BindView(R.id.cv_cell_box_code)
    CellView mBoxCodeCell;

    @BindView(R.id.cv_cell_item_code)
    CellView mItemCodeCell;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_append;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mAdapter = new CodeAdapter();

        mItemCodeView.setLayoutManager(new LinearLayoutManager(this));
        mItemCodeView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
        return R.string.text_home_supply;
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
    protected boolean showSecondaryButtonText() {
        return true;
    }

    @Override
    protected int getSecondaryButtonText() {
        return R.string.text_reset_operation;
    }

    @Override
    protected void onSecondaryButtonClicked(View view) {
        super.onSecondaryButtonClicked(view);
        resolveBoxCode("");
        mAdapter.clear();
        mItemCodeCell.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScanned(String data) {
        if (TextUtils.isEmpty(mBoxCode)) {
            checkBoxCode(data);
        } else {
            checkItemCode(data);
        }
    }

    @OnClick(R.id.btn_append)
    public void doAppend() {
        if (mAdapter.isEmpty()) {
            toast("请录入码信息再执行补标操作");
            return ;
        }

        CodeApiWrapper.doAppend(mBoxCode, mAdapter.getLogisticsCode(), new SimpleObserver<Object, List<String>>() {
            @Override
            public void onSuccess(Object data) {
                finish();
            }

            @Override
            public void onFailure(List<String> errorCodes, String message) {
                handlePackageError(errorCodes, message);
            }
        });
    }

    private void checkItemCode(final String code) {
        if (mAdapter.contains(code)) {
            toast(getString(R.string.toast_item_code_repeated));
            return ;
        }
        CodeApiWrapper.isItemCode(code, new SimpleObserver<Boolean, Object>() {
            @Override
            public void onSuccess(Boolean isCorrect) {
                if (isCorrect) {
                    SoundWrapper.get().playSuccess();
                    resolveItemCode(code);
                } else {
                    SoundWrapper.get().playAlert();
                    toast(String.format("%s 不是正确的单码, 请核对后重新扫描", code));
                }
            }

            @Override
            public void onFailure(@Nullable Object data, String message) {
                super.onFailure(data, message);
                SoundWrapper.get().playAlert();
            }
        });
    }

    private void checkBoxCode(final String code) {
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
        mBoxCode = code;
        mBoxCodeCell.setValue(TextUtils.isEmpty(mBoxCode)? getString(R.string.text_scan_box_code_first) : mBoxCode);
    }

    private void resolveItemCode(String code) {
        mAdapter.add(0, code, Code.TYPE_ITEM_CODE);
        mItemCodeCell.setVisibility(View.GONE);
    }

    private void handlePackageError(List<String> errorCodes, String message) {
        if (errorCodes != null && errorCodes.size() > 0) {
            toast(message + "\n" + TextUtils.join("\n", errorCodes));
        } else {
            toast(message);
        }
    }
}
