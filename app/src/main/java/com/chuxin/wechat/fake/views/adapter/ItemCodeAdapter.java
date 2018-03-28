package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.views.ui.CellView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查询页使用
 * Created by chao on 2018/3/19.
 */

public class ItemCodeAdapter extends BaseRecyclerAdapter<ItemCode, ItemCodeAdapter.ViewHolder> {


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_query_itemcode;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, ItemCode data, int pos) {
        holder.mItemCodeCell.setValue(data.getLogisticsCode());
        holder.mItemCodeCell.setLabel(String.format("单码 %s", pos + 1));
        holder.mItemCodeCell.showDivider(pos != getData().size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_cell_item_code)
        CellView mItemCodeCell;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
