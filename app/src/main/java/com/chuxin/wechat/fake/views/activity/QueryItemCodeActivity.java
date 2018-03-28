package com.chuxin.wechat.fake.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.entity.BoxCode;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.utils.GsonHolder;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;
import com.chuxin.wechat.fake.views.ui.CellView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

/**
 * 单码查询箱码
 * Created by chao on 2018/3/17.
 */

public class QueryItemCodeActivity extends BaseScanActivity {

    @BindView(R.id.cv_cell_item_code)
    CellView mItemCodeCell;

    @BindView(R.id.cv_cell_hint)
    CellView mHintCell;

    @BindView(R.id.cv_cell_box_code)
    CellView mBoxCodeCell;

    @BindView(R.id.tv_empty_tips)
    TextView mEmptyText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_item_code;
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
        return R.string.text_item_code_to_box_code;
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
        checkItemCode(data);
    }

    public void checkItemCode(final String code) {
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

    public void resolveItemCode(String code) {
        showLoadingDialog();
        mItemCodeCell.setValue(code);
        CodeApiWrapper.getBoxCodeByItemCode(code, new SimpleObserver<BoxCode, Object>() {
            @Override
            public void onSuccess(BoxCode data) {
                super.onSuccess(data);
                mHintCell.setVisibility(View.VISIBLE);
                mEmptyText.setVisibility(data == null ? View.VISIBLE : View.GONE);
                mBoxCodeCell.setVisibility(data == null ? View.GONE : View.VISIBLE);
                mBoxCodeCell.setValue(data == null ? "" : data.getLogisticsCode());
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
