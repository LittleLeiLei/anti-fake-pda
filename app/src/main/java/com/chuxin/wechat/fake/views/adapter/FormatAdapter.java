package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.Format;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class FormatAdapter extends BaseRecyclerAdapter<Format, FormatAdapter.ViewHolder> {

    private String selectedId = "";

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_format;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(FormatAdapter.ViewHolder holder, Format data, int pos) {
        holder.mProductText.setText(data.getProduct() != null ? data.getProduct().getName() : "");
        holder.mFormatText.setText(data.getName());
        holder.mCheckImg.setVisibility(TextUtils.equals(data.getId(), selectedId) ? View.VISIBLE : View.GONE);
    }

    public void setSelected (String formatId) {
        this.selectedId = formatId;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_product_name)
        TextView mProductText;

        @BindView(R.id.tv_format_name)
        TextView mFormatText;

        @BindView(R.id.iv_check)
        ImageView mCheckImg;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
