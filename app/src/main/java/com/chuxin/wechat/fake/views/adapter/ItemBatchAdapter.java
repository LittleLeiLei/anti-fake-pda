package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.ItemBatch;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/14.
 */

public class ItemBatchAdapter extends BaseRecyclerAdapter<ItemBatch, ItemBatchAdapter.ViewHolder> {

    private String mItemBatchId = "";

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_itembatch;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, ItemBatch data, int pos) {
        holder.mItemBatchNameText.setText(data.getName());
        holder.mCheckImg.setVisibility(TextUtils.equals(mItemBatchId, data.getId()) ? View.VISIBLE : View.GONE);
    }

    public void setSelected(String itemBatchId) {
        mItemBatchId = itemBatchId;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_batch_name)
        TextView mItemBatchNameText;

        @BindView(R.id.iv_check)
        ImageView mCheckImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
