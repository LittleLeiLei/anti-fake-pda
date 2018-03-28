package com.chuxin.wechat.fake.views.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.api.wrapper.ProductApiWrapper;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.entity.CodeInfo;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.entity.PackageWrapper;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.scanner.ScannerWrapper;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;
import com.chuxin.wechat.fake.views.ui.CellView;
import com.chuxin.wechat.fake.views.ui.dialog.ConfirmDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 装箱扫码
 * Created by chao on 2018/3/15.
 */

public class PackageScanActivity extends BaseScanActivity {

    private Format mFormat;
    private ItemBatch mItemBatch;
    private PackageWrapper mCurrentPackageWrapper;
    private List<PackageWrapper> mPackages = new ArrayList<>();

    private ConfirmDialog mDialog;

    @BindView(R.id.cv_cell_product)
    CellView mProductCell;

    @BindView(R.id.cv_cell_item_batch)
    CellView mItemBatchCell;

    @BindView(R.id.cv_cell_batch_scale)
    CellView mScaleCell;

    @BindView(R.id.cv_cell_box_code)
    CellView mBoxCodeCell;

    @BindView(R.id.cv_cell_item_code)
    CellView mItemCodeCell;

    @BindView(R.id.cv_cell_package_count)
    CellView mPackageCountCell;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_package_scan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFormat = getIntent().getParcelableExtra(Constant.Key.KEY_FORMAT);
        mItemBatch = getIntent().getParcelableExtra(Constant.Key.KEY_ITEM_BATCH);
    }

    @Override
    protected void initView() {
        mDialog = new ConfirmDialog.Builder(this)
                .message("存在码未装箱，是否确定完成装箱？")
                .positive(new ConfirmDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                }).build();

        mProductCell.setValue(mFormat.getName());
        mItemBatchCell.setValue(mItemBatch.getName());
        mScaleCell.setValue(String.format("%s个 / 箱", mItemBatch.getItemScale()));
        mPackageCountCell.setValue(String.valueOf(mPackages.size()));
        resetPackage();
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
        return R.string.text_package_scan_code;
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
        resetPackage();
    }

    @Override
    public void onScanned(final String data) {

        if (TextUtils.isEmpty(mCurrentPackageWrapper.getBoxLogisticsCode())) {
            checkBoxCode(data);
        } else {
            checkItemCode(data);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Constant.Code.REQUEST_CODE_ITEM_BATCH_SCAN:
                resolveItemCodes(data.getStringArrayListExtra(Constant.Key.KEY_ITEM_CODES));
                break;
        }
    }

    @OnClick(R.id.cv_cell_item_code)
    public void scanItemCode() {
        Intent intent = new Intent(IntentAction.PAGE_PACKAGE_SCAN_ITEM_CODE);
        intent.putExtra(Constant.Key.KEY_ITEM_BATCH, mItemBatch);
        intent.putStringArrayListExtra(Constant.Key.KEY_ITEM_CODES, mCurrentPackageWrapper.getItemLogisticsCodes());
        resolveIntent4Result(intent, Constant.Code.REQUEST_CODE_ITEM_BATCH_SCAN);
    }

    @OnClick(R.id.btn_complete)
    public void packageComplete() {
        if (!TextUtils.isEmpty(mCurrentPackageWrapper.getBoxLogisticsCode()) || mCurrentPackageWrapper.getItemLogisticsCodes().size() > 0) {
            mDialog.show();
        } else {
            finish();
        }
    }

    private void resolveItemCodes(ArrayList<String> itemCodes) {
        toast(itemCodes.size() < mItemBatch.getItemScale() ? "单码未录入完成" : "单码录入完成");
        mCurrentPackageWrapper.setItemLogisticsCodes(itemCodes);
        mItemCodeCell.setValue(String.format("%s / %s", itemCodes.size(), mItemBatch.getItemScale()));
    }

    private void checkBoxCode(final String code) {
        CodeApiWrapper.getCodeInfo(code, new SimpleObserver<CodeInfo, Object>() {
            @Override
            public void onSuccess(CodeInfo data) {
                if (data.isItemCode()) {
                    SoundWrapper.get().playAlert();
                    toast(String.format("%s 不是箱码, 请核对后重新扫描", code));
                } else if (data.getLinkedCount() > 0) {
                    SoundWrapper.get().playAlert();
                    toast(String.format("%s 已被装箱，请核对后重新扫描", code));
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

    private void resolveBoxCode(String code) {
        MyApp.get().toast("箱码录入:" + code);
        mCurrentPackageWrapper.setBoxLogisticsCode(code);
        mBoxCodeCell.setValue(code);
    }

    private void checkItemCode(final String code) {
        if (mCurrentPackageWrapper.getItemLogisticsCodes().size() >= mItemBatch.getItemScale()) {
            SoundWrapper.get().playAlert();
            toast(getString(R.string.toast_item_code_over_count));
        } else if (mCurrentPackageWrapper.getItemLogisticsCodes().contains(code)) {
            SoundWrapper.get().playAlert();
            toast(getString(R.string.toast_item_code_repeated));
        } else {
            // 判断数量限制正确后判断码是否正确，减少不必要的请求次数
            CodeApiWrapper.getCodeInfo(code, new SimpleObserver<CodeInfo, Object>() {
                @Override
                public void onSuccess(CodeInfo data) {
                    if (!data.isItemCode()) {
                        SoundWrapper.get().playAlert();
                        toast(String.format("%s 不是单码, 请核对后重新扫描", code));
                    } else if (data.getLinkedCount() > 0) {
                        SoundWrapper.get().playAlert();
                        toast(String.format("%s 已被装箱，请核对后重新扫描", code));
                    } else {
                        SoundWrapper.get().playSuccess();
                        resolveItemCode(code);
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

    private void resolveItemCode(String code) {
        mCurrentPackageWrapper.getItemLogisticsCodes().add(code);
        mItemCodeCell.setValue(String.format("%s / %s", mCurrentPackageWrapper.getItemLogisticsCodes().size(), mItemBatch.getItemScale()));
        if (mCurrentPackageWrapper.getItemLogisticsCodes().size() == mItemBatch.getItemScale()) {
            CodeApiWrapper.doPackage(mItemBatch.getId(), mCurrentPackageWrapper.getBoxLogisticsCode(), mCurrentPackageWrapper.getItemLogisticsCodes(), new SimpleObserver<Object, List<String>>() {
                @Override
                public void onSuccess(Object data) {
                    doPackageSuccess();
                }

                @Override
                public void onFailure(List<String> errorCodes, String message) {
                    handlePackageError(errorCodes, message);
                }
            });
        }
    }


    /**
     * 清空该次装箱
     */
    private void resetPackage() {
        mCurrentPackageWrapper = new PackageWrapper();
        mBoxCodeCell.setValue(String.format("%s / %s", 0, 1));
        mItemCodeCell.setValue(String.format("%s / %s", 0, mItemBatch.getItemScale()));
    }

    private void doPackageSuccess() {
        mPackages.add(mCurrentPackageWrapper);
        mPackageCountCell.setValue(String.valueOf(mPackages.size()));
        resetPackage();
    }

    private void handlePackageError(List<String> errorCodes, String message) {
        if (errorCodes != null && errorCodes.size() > 0) {
            toast(message + "\n" + TextUtils.join("\n", errorCodes));
        } else {
            toast(message);
        }
    }
}
