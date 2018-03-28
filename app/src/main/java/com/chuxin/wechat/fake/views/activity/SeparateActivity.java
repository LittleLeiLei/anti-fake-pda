package com.chuxin.wechat.fake.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.scanner.ScannerWrapper;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分离单标
 * Created by chao on 2018/3/17.
 */

public class SeparateActivity extends BaseScanActivity {

    private CodeAdapter mAdapter;

    @BindView(R.id.rv_item_code)
    RecyclerView mItemCodeView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_separate;
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
        return R.string.text_home_separate;
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
        mAdapter.clear();
    }

    @Override
    public void onScanned(final String data) {
        if (mAdapter.contains(data)) {
            toast(getString(R.string.toast_item_code_repeated));
            SoundWrapper.get().playAlert();
        } else {
            // 判断数量限制正确后判断码是否正确，减少不必要的请求次数
            CodeApiWrapper.isItemCode(data, new SimpleObserver<Boolean, Object>() {
                @Override
                public void onSuccess(Boolean isCorrect) {
                    if (isCorrect) {
                        SoundWrapper.get().playSuccess();
                        resolveItemCode(data);
                    } else {
                        SoundWrapper.get().playAlert();
                        toast(String.format("%s 不是正确的单码, 请核对后重新扫描", data));
                    }
                }

                @Override
                public void onFailure(@Nullable Object data, String message) {
                    super.onFailure(data, message);
                    SoundWrapper.get().playAlert();
                }
            });
        }
    }

    @OnClick(R.id.btn_separate)
    public void doSeparate() {
        if (mAdapter.isEmpty()) {
            toast("请录入单码后再执行分离操作");
            return ;
        }

        CodeApiWrapper.doSeparate(mAdapter.getLogisticsCode(), new SimpleObserver() {
            @Override
            public void onSuccess(Object data) {
                finish();
            }
        });
    }

    private void resolveItemCode (String code) {
        mAdapter.add(0, code, Code.TYPE_ITEM_CODE);
    }
}
