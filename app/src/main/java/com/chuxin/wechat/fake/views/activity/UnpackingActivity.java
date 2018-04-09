package com.chuxin.wechat.fake.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.entity.BoxCode;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.CodeInfo;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.scanner.ScannerWrapper;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拆箱
 * Created by chao on 2018/3/17.
 */

public class UnpackingActivity extends BaseScanActivity {

    private CodeAdapter mAdapter;

    @BindView(R.id.rv_box_code)
    RecyclerView mBoxCodeView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unpack;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mAdapter = new CodeAdapter();

        mBoxCodeView.setLayoutManager(new LinearLayoutManager(this));
        mBoxCodeView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBoxCodeView.setAdapter(mAdapter);
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
        return R.string.text_home_unpack;
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
    public void onScanned(final String code) {
        if (mAdapter.contains(code)) {
            toast(getString(R.string.toast_box_code_repeated));
            SoundWrapper.get().playAlert();
        } else {
            // 判断数量限制正确后判断码是否正确，减少不必要的请求次数

            CodeApiWrapper.getCodeInfo(code, new SimpleObserver<CodeInfo, Object>() {
                @Override
                public void onSuccess(CodeInfo data) {
                    if (data.isItemCode()) {
                        SoundWrapper.get().playAlert();
                        toast(String.format("%s 不是正确的箱码, 请核对后重新扫描", code));
                    } else if (data.getLinkedCount() == 0) {
                        SoundWrapper.get().playAlert();
                        toast(String.format("%s 还未装箱, 请核对后重新扫描", code));
                    } else {
                        SoundWrapper.get().playSuccess();
                        resolveBoxCode(code);
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

    @OnClick(R.id.btn_unpack)
    public void doUnpack() {
        if (mAdapter.isEmpty()) {
            toast("请录入箱码后再执行拆箱");
            return ;
        }

        CodeApiWrapper.doUnpack(mAdapter.getLogisticsCode(), new SimpleObserver() {
            @Override
            public void onSuccess(Object data) {
                toast("拆箱成功");
                finish();
            }
        });
    }

    private void resolveBoxCode (String code) {
        mAdapter.add(0, code, Code.TYPE_BOX_CODE);
    }
}
