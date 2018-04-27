package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private boolean enabled = true;

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
        holder.mFormatName.setText(CommonUtils.wrapOverText(data.getFullName(), 20));

        holder.mFormatCount.setText(String.format("%s / %s %s", data.getPackageCount(), data.getCount(), data.getProduct().getMinUnit()));
        holder.divider.setVisibility(pos != getData().size() - 1 ? View.VISIBLE : View.INVISIBLE);
        holder.mArrowImg.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    public void setEnabled (boolean enabled) {
        this.enabled = enabled;
    }

    public void reset() {
        for (Format format : getData()) {
            format.setPackageCount(0);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        // @BindView(R.id.cv_cell_product)
        // CellView mProductCell;

        @BindView(R.id.tv_format_name)
        TextView mFormatName;

        @BindView(R.id.tv_format_count)
        TextView mFormatCount;

        @BindView(R.id.iv_arrow_right)
        ImageView mArrowImg;

        @BindView(R.id.divider)
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
