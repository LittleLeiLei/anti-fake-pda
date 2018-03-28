package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.Unit;
import com.chuxin.wechat.fake.utils.CommonUtils;
import com.chuxin.wechat.fake.views.ui.CellView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/16.
 */

public class OrderFormatAdapter extends BaseRecyclerAdapter<Format, OrderFormatAdapter.ViewHolder> {

    private boolean isDelivered = false;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_order_product;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, Format data, int pos) {
        holder.mProductCell.setLabel(CommonUtils.wrapOverText(data.getFullName(), 15));

        int totalCount = soldUnit2MinUnit(data.getProduct().getSoldUnit(), data.getCount());
        holder.mProductCell.setValue(String.format("%s / %s %s", isDelivered ? totalCount : data.getPackageCount(), totalCount, data.getProduct().getMinUnit()));
        holder.mProductCell.showDivider(pos != getData().size() - 1);
    }

    private int soldUnit2MinUnit(Unit soldUnit, int count) {
        return count * soldUnit.getNumber();
    }

    public void setDelivered (boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public void reset() {
        for (Format format : getData()) {
            format.setPackageCount(0);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_cell_product)
        CellView mProductCell;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
