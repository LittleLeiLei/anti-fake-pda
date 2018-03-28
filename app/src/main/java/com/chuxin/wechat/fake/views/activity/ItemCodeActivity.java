package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.scanner.ScannerWrapper;
import com.chuxin.wechat.fake.views.adapter.CodeAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 扫描单标
 * Created by chao on 2018/3/15.
 */

public class ItemCodeActivity extends BaseActivity {

    @BindView(R.id.rv_item_code)
    RecyclerView mItemCodeView;

    private CodeAdapter mAdapter;

    private ItemBatch mItemBatch;
    private ArrayList<String> mItemCodes = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_code;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mItemBatch = getIntent().getParcelableExtra(Constant.Key.KEY_ITEM_BATCH);
        mItemCodes = getIntent().getStringArrayListExtra(Constant.Key.KEY_ITEM_CODES);
    }

    @Override
    protected void initView() {
        mAdapter = new CodeAdapter();
        mAdapter.setLogisticsCode(mItemCodes, Code.TYPE_ITEM_CODE);

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
        return R.string.text_package_scan_item_code;
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
    public void onBackPressed() {
        backWithItemCodes(mAdapter.getLogisticsCode());
    }

//    @Override
//    public void onScanned(final String data) {
//        if (mAdapter.getLogisticsCode().size() >= mItemBatch.getItemScale()) {
//            toast(getString(R.string.toast_item_code_over_count));
//        } else if (mAdapter.contains(data)) {
//            toast(getString(R.string.toast_item_code_repeated));
//        } else {
//            // 判断数量限制正确后判断码是否正确，减少不必要的请求次数
//
//            CodeApiWrapper.isItemCode(data, new SimpleObserver<Boolean, Object>() {
//                @Override
//                public void onSuccess(Boolean isCorrect) {
//                    if (isCorrect) {
//                        resolveItemCode(data);
//                    } else {
//                        toast(data + " 不是正确的单码, 请核对后重新扫描");
//                    }
//                }
//            });
//        }
//    }

    private void resolveItemCode (String code) {
        mAdapter.add(0, code, Code.TYPE_ITEM_CODE);
        if (mAdapter.getLogisticsCode().size() == mItemBatch.getItemScale()) {
            backWithItemCodes(mAdapter.getLogisticsCode());
        }
    }

    private void backWithItemCodes(List<String> logisticsCode) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(Constant.Key.KEY_ITEM_CODES, (ArrayList<String>) logisticsCode);
        setResult(RESULT_OK, intent);
        finish();
    }
}
