package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.chuxin.wechat.fake.views.ui.CellView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 装箱
 * Created by chao on 2018/3/12.
 */

public class PackageActivity extends BaseActivity {

    @BindView(R.id.cv_cell_product)
    CellView mFormatCell;

    @BindView(R.id.cv_cell_item_batch)
    CellView mItemBatchCell;

    @BindView(R.id.cv_cell_scale)
    CellView mScaleCell;

    private Format mFormat;
    private ItemBatch mItemBatch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_package;
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
    protected int getToolbarTitle() {
        return R.string.text_package;
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

    @OnClick(R.id.cv_cell_product)
    public void selectProduct () {
        Intent intent = new Intent(IntentAction.PAGE_PRODUCTS);
        if (mFormat != null) {
            intent.putExtra(Constant.Key.KEY_FORMAT, mFormat);
        }
        resolveIntent4Result(intent, Constant.Code.REQUEST_CODE_FORMAT);
    }

    @OnClick(R.id.cv_cell_item_batch)
    public void selectItemBatch () {
        if (mFormat == null) {
            toast(getString(R.string.toast_select_product_firstly));
            return ;
        }

        Intent intent = new Intent(IntentAction.PAGE_ITEM_BATCHES);
        intent.putExtra(Constant.Key.KEY_FORMAT, mFormat);
        if (mItemBatch != null) {
            intent.putExtra(Constant.Key.KEY_ITEM_BATCH, mItemBatch);
        }
        resolveIntent4Result(intent, Constant.Code.REQUEST_CODE_ITEM_BATCH);
    }

    @OnClick(R.id.btn_confirm)
    public void next() {
        if (mFormat == null || mItemBatch == null) {
            toast(getString(R.string.toast_select_prodct_and_batch_firstly));
            return ;
        }
        Intent intent = new Intent(IntentAction.PAGE_PACKAGE_SCAN);
        intent.putExtra(Constant.Key.KEY_FORMAT, mFormat);
        intent.putExtra(Constant.Key.KEY_ITEM_BATCH, mItemBatch);
        resolveIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return ;

        switch (requestCode) {
            case Constant.Code.REQUEST_CODE_FORMAT:
                resolveFormat((Format) data.getParcelableExtra(Constant.Key.KEY_FORMAT));
                break;
            case Constant.Code.REQUEST_CODE_ITEM_BATCH:
                resolveItemBatch((ItemBatch) data.getParcelableExtra(Constant.Key.KEY_ITEM_BATCH));
                break;
        }
    }

    private void resolveFormat(Format format) {
        if (mFormat != null && !TextUtils.equals(format.getId(), mFormat.getId())) {
            mItemBatch = null;
            mItemBatchCell.setValue(getString(R.string.text_cell_placeholder));
            mScaleCell.setValue("");
        }
        mFormat = format;
        mFormatCell.setValue(format.getProduct().getName() + " " + format.getName());
    }

    private void resolveItemBatch(ItemBatch itemBatch) {
        mItemBatch = itemBatch;
        mItemBatchCell.setValue(itemBatch.getName());
        mScaleCell.setValue(String.format("%s个 / 箱", itemBatch.getItemScale()));
    }
}
