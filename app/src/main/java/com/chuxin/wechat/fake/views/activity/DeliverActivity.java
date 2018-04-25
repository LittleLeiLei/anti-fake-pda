package com.chuxin.wechat.fake.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.CodeApiWrapper;
import com.chuxin.wechat.fake.api.wrapper.OrderApiWrapper;
import com.chuxin.wechat.fake.api.wrapper.WarehouseApiWrapper;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.CodeInfo;
import com.chuxin.wechat.fake.entity.DeliverResult;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.entity.Order;
import com.chuxin.wechat.fake.entity.StockDetail;
import com.chuxin.wechat.fake.entity.StockRecord;
import com.chuxin.wechat.fake.entity.Warehouse;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.scanner.ScannerWrapper;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.utils.ACache;
import com.chuxin.wechat.fake.utils.GsonHolder;
import com.chuxin.wechat.fake.views.adapter.BaseRecyclerAdapter;
import com.chuxin.wechat.fake.views.adapter.OrderFormatAdapter;
import com.chuxin.wechat.fake.views.base.BaseScanActivity;
import com.chuxin.wechat.fake.views.ui.CellView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发货
 * Created by chao on 2018/3/15.
 */

public class DeliverActivity extends BaseScanActivity {

    private Warehouse mWarehouse;
    private Order mOrder;
    private OrderFormatAdapter mAdapter;

    private List<String> mItemCodes = new ArrayList<>();
    private List<String> mBoxCodes = new ArrayList<>();
    // private List<Warehouse> warehouses = new ArrayList<>();

    private HashMap<String, List<Code>> mCodeMap = new HashMap<>();

    @BindView(R.id.rv_order_products)
    RecyclerView mFormatsView;

    @BindView(R.id.cv_cell_hint_scan_code)
    CellView mHintCell;

    @BindView(R.id.cv_cell_order_state)
    CellView mOrderStateCell;

    @BindView(R.id.ll_order_info)
    LinearLayout mOrderInfoView;

    @BindView(R.id.rl_receiver_info)
    RelativeLayout mAddressView;

    @BindView(R.id.tv_receiver_name)
    TextView mReceiverNameText;

    @BindView(R.id.tv_receiver_phone)
    TextView mReceiverPhoneText;

    @BindView(R.id.tv_receiver_address)
    TextView mFullAddressText;

    @BindView(R.id.tv_remark)
    CellView mRemarkText;

    @BindView(R.id.cv_cell_from_warehouse)
    CellView mWarehouseCell;

    @BindView(R.id.cv_cell_order_code)
    CellView mOrderCode;

    @BindView(R.id.btn_deliver)
    Button mDeliverBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deliver;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        resolveOrder(mOrder);
    }

    @Override
    protected void initData() {
        mWarehouse = GsonHolder.get().fromJson(ACache.get(this).getAsString(Constant.Key.KEY_WAREHOUSE), Warehouse.class);
        WarehouseApiWrapper.getWarehouses(new SimpleObserver<List<Warehouse>, Object>() {
            @Override
            public void onSuccess(List<Warehouse> data) {
                if (mWarehouse == null || !data.contains(mWarehouse)) {
                    mWarehouse = null;
                }
                resolveWarehouse(mWarehouse);
            }
        });
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.text_home_deliver;
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
    protected int getSecondaryButtonText() {
        return R.string.text_reset_operation;
    }

    @Override
    protected boolean showSecondaryButtonText() {
        return true;
    }

    @Override
    protected void onSecondaryButtonClicked(View view) {
        super.onSecondaryButtonClicked(view);
        resetFormats();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case Constant.Code.REQUEST_CODE_WAREHOUSE:
                mWarehouse = data.getParcelableExtra(Constant.Key.KEY_WAREHOUSE);
                resolveWarehouse(mWarehouse);
                break;
            case Constant.Code.REQUEST_CODE_ORDER_FORMAT_CODES:
                ArrayList<Code> codes = data.getParcelableArrayListExtra(Constant.Key.KEY_ORDER_FORMAT_CODES);
                Format format = data.getParcelableExtra(Constant.Key.KEY_FORMAT);
                resolveFormatCodes(format, codes);
                break;
        }
    }

    @Override
    public void onScanned(final String code) {

        if (mOrder == null) {
            resolveOrderCode(code);
        } else {
            if (mOrder.isUndelivered()) {
                getCodeInfo(code);
            }
        }
    }

    @OnClick(R.id.cv_cell_from_warehouse)
    public void selectFromWarehouse() {
        Intent intent = new Intent(IntentAction.PAGE_WAREHOUSE);
        intent.putExtra(Constant.Key.KEY_WAREHOUSE, mWarehouse);
        resolveIntent4Result(intent, Constant.Code.REQUEST_CODE_WAREHOUSE);
    }

    @OnClick(R.id.btn_deliver)
    public void doDeliver() {

        if (!checkWarehouse(mWarehouse)) return;

        if (!checkOrderFormat(mAdapter.getData())) return;

        OrderApiWrapper.doDeliver(mOrder.getId(), mWarehouse.getId(), mBoxCodes, mItemCodes, new SimpleObserver<Object, DeliverResult>() {
            @Override
            public void onSuccess(Object data) {
                resetOrder();
                ACache.get(DeliverActivity.this).put(Constant.Key.KEY_WAREHOUSE, GsonHolder.get().toJson(mWarehouse));
            }

            @Override
            public void onFailure(DeliverResult data, String message) {
                if (data != null) {
                    handleDeliverError(data, message);
                } else {
                    toast(message);
                }
            }
        });
    }

    private void resolveOrder(Order order) {
        boolean isOrderEmpty = (order == null);
        if (isOrderEmpty) {
            setSecondaryButtonVisibility(View.GONE);
            mOrderInfoView.setVisibility(View.GONE);
            mHintCell.setVisibility(View.VISIBLE);
        } else {
            setSecondaryButtonVisibility(View.VISIBLE);
            mHintCell.setVisibility(View.GONE);
            mOrderInfoView.setVisibility(View.VISIBLE);
            mDeliverBtn.setVisibility(order.isUndelivered() ? View.VISIBLE : View.GONE);
            mOrderCode.setValue(order.getCode());
            mOrderStateCell.setValue(mOrder.getOrderStateName());
            mOrderStateCell.setValueColor(mOrder.isDelivered() ? getResources().getColor(R.color.color_success) : getResources().getColor(R.color.color_failed));
            mRemarkText.setValue(TextUtils.isEmpty(order.getNote()) ? "无" : order.getNote());
            mReceiverNameText.setText(String.format("收件人 ： %s", order.getAddress().getName()));
            mReceiverPhoneText.setText(order.getAddress().getPhone());
            mFullAddressText.setText(order.getAddress().getFullAddress());

            resolveOrderCodes(order);
            mAdapter = new OrderFormatAdapter();
            mAdapter.setDelivered(mOrder.isDelivered());
            mAdapter.setData(order.getFormats());
            mFormatsView.setLayoutManager(new LinearLayoutManager(this));
            mFormatsView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Format>() {
                @Override
                public void onItemClick(View view, Format data, int pos) {

                    if (mOrder.getOrderState() == Order.STATE_OBSOLETE) return;

                    Intent intent = new Intent(IntentAction.PAGE_ORDER_FORMAT_CODES);
                    ArrayList<Code> codes = (ArrayList<Code>) mCodeMap.get(data.getId());
                    intent.putParcelableArrayListExtra(Constant.Key.KEY_ORDER_FORMAT_CODES, codes != null ? codes : new ArrayList<Code>());
                    intent.putExtra(Constant.Key.KEY_FORMAT, data);
                    intent.putExtra(Constant.Key.KEY_IS_UNDELIVERED, mOrder.isUndelivered());
                    resolveIntent4Result(intent, Constant.Code.REQUEST_CODE_ORDER_FORMAT_CODES);
                }
            });

        }
    }

    private void resolveWarehouse(Warehouse warehouse) {
        mWarehouseCell.setValue(warehouse != null ? warehouse.getName() : getString(R.string.text_cell_placeholder));
    }

    private void resolveFormatCodes(Format format, ArrayList<Code> codes) {
        if (mCodeMap.get(format.getId()) == null) return;
        Iterator it = mCodeMap.get(format.getId()).iterator();
        for (;it.hasNext(); ) {
            Code code = (Code) it.next();
            if (!codes.contains(code)) {
                it.remove();
                format.setPackageCount(format.getPackageCount() - code.getCodeInfo().getLinkedCount());
                if (code.getCodeInfo().isItemCode()) {
                    mItemCodes.remove(code.getLogisticsCode());
                } else {
                    mBoxCodes.remove(code.getLogisticsCode());
                }
            }
        }
        int index = mAdapter.getData().indexOf(format);
        mAdapter.getData().get(index).setPackageCount(format.getPackageCount());
        mAdapter.notifyDataSetChanged();
    }

    private void resolveOrderCode(final String code) {
        OrderApiWrapper.getOrderDetail(code, new SimpleObserver<Order, Object>() {
            @Override
            public void onSuccess(Order data) {
                SoundWrapper.get().playSuccess();
                mOrder = data;
                mOrder.setCode(code);
                resolveOrder(mOrder);
            }

            @Override
            public void onFailure(@Nullable Object data, String message) {
                super.onFailure(data, message);
                SoundWrapper.get().playAlert();
            }
        });
    }

    private void getCodeInfo(final String code) {
        CodeApiWrapper.getCodeInfo(code, new SimpleObserver<CodeInfo, Object>() {
            @Override
            public void onSuccess(CodeInfo data) {
                if (data.getLinkedCount() == 0) {
                    toast(String.format("码 %s 还未关联商品", code));
                    SoundWrapper.get().playAlert();
                } else {
                    data.setCode(code);
                    resolveCodeInfo(data);
                }
            }

            @Override
            public void onFailure(@Nullable Object data, String message) {
                super.onFailure(data, message);
                SoundWrapper.get().playAlert();
            }
        });

    }

    private void resolveCodeInfo(CodeInfo data) {
        if (mItemCodes.contains(data.getCode()) || mBoxCodes.contains(data.getCode())) {
            toast("该码已录入订单，请勿重复扫描");
            SoundWrapper.get().playAlert();
            return;
        }

        int index = mOrder.getFormats().indexOf(data.getFormat());
        if (index > -1) {

            Format orderFormat = mOrder.getFormats().get(index);
            if (orderFormat.getPackageCount() + data.getLinkedCount() > orderFormat.getMinUnitCount()) {
                toast(String.format("超过订单内 %s 的数量上限", orderFormat.getFullName()));
                SoundWrapper.get().playAlert();
            } else {
                if (data.isItemCode()) {
                    mItemCodes.add(data.getCode());
                } else {
                    mBoxCodes.add(data.getCode());
                }

                Code code = new Code(data.getCode(), data.isItemCode() ? Code.TYPE_ITEM_CODE : Code.TYPE_BOX_CODE);
                code.setCodeInfo(data);
                if (mCodeMap.get(orderFormat.getId()) == null) {
                    List<Code> codes = new ArrayList<>();
                    codes.add(code);
                    mCodeMap.put(orderFormat.getId(), codes);
                } else {
                    mCodeMap.get(orderFormat.getId()).add(code);
                }

                orderFormat.setPackageCount(orderFormat.getPackageCount() + data.getLinkedCount());
                mAdapter.notifyDataSetChanged();
                toast(String.format("扫描 %s %s %s", orderFormat.getFullName(), data.getLinkedCount(), orderFormat.getProduct().getMinUnit()));
                SoundWrapper.get().playSuccess();
            }

        } else {
            SoundWrapper.get().playAlert();
            toast(String.format("该 %s 不属于订单内商品", data.getCodeName()));
        }
    }

    /**
     * 获取已发货订单的码信息
     * @param order
     */
    private void resolveOrderCodes(Order order) {
        for (StockDetail detail : order.getStockDetails()) {
            for (StockRecord record : detail.getStockRecords()) {
                List<Code> codes = new ArrayList<>();
                if (mCodeMap.get(record.getFormatId()) != null) {
                    codes = mCodeMap.get(record.getFormatId());
                }
                for (Code code : record.getCodes().getBoxCodes()) {
                    code.setType(Code.TYPE_BOX_CODE);
                    codes.add(code);
                }
                for (Code code : record.getCodes().getItemCodes()) {
                    code.setType(Code.TYPE_ITEM_CODE);
                    codes.add(code);
                }
                mCodeMap.put(record.getFormatId(), codes);
//                List<Code> existedFormatCodes = mCodeMap.get(record.getFormatId());
//                if (existedFormatCodes != null) {
//                    existedFormatCodes.addAll(codes);
//                } else {
//                    mCodeMap.put(record.getFormatId(), codes);
//                }
            }
        }
    }

    private void handleDeliverError(DeliverResult result, String message) {
        if (result.getErrorCodes() != null && result.getErrorCodes().size() > 0) {
            toast(message + "\n" + TextUtils.join("\n", result.getErrorCodes()));
        } else if (result.getRepeatCodes() != null && result.getRepeatCodes().size() > 0) {
            String toastText = message;
            for (ItemCode code : result.getRepeatCodes()) {
                toastText += "\n" + code.getLogisticsCode();
            }
            toast(toastText);
        }
    }


    private boolean checkWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            toast("未选择发货仓库");
            return false;
        }
        return true;
    }

    private boolean checkOrderFormat(List<Format> formats) {
        for (Format format : formats) {
            if (format.getPackageCount() < format.getMinUnitCount()) {
                toast(String.format("%s 数量未达到订单要求", format.getFullName()));
                return false;
            }
        }
        return true;
    }

    private void resetFormats() {
        mCodeMap.clear();
        mItemCodes.clear();
        mBoxCodes.clear();
        mAdapter.reset();
    }

    private void resetOrder() {
        mCodeMap.clear();
        mItemCodes.clear();
        mBoxCodes.clear();
        mOrder = null;
        resolveOrder(null);
    }
}
