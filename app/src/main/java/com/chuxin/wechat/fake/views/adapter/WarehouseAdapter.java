package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.Warehouse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/15.
 */

public class WarehouseAdapter extends BaseRecyclerAdapter<Warehouse, WarehouseAdapter.ViewHolder> {

    private String mWarehouseId = "";

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_warehouse;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, Warehouse data, int pos) {
        holder.mWarehouseText.setText(data.getName());
        holder.mCheckImg.setVisibility(TextUtils.equals(mWarehouseId, data.getId()) ? View.VISIBLE : View.GONE);
    }

    public void setSelected(String warehouseId) {
        this.mWarehouseId = warehouseId;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_warehouse)
        TextView mWarehouseText;

        @BindView(R.id.iv_check)
        ImageView mCheckImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
