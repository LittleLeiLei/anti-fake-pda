package com.chuxin.wechat.fake.views.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.entity.Code;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/15.
 */

public class CodeAdapter extends BaseRecyclerAdapter<Code, CodeAdapter.ViewHolder> {

    private boolean editable = true;

    public CodeAdapter() {
        this(true);
    }

    public CodeAdapter(boolean editable) {
        this.editable = editable;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_itemcode;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindHolder(ViewHolder holder, Code data, final int pos) {
        holder.mLabelText.setText(String.format("%s %s :", data.getTypeName() ,getData().size() - pos));
        holder.mValueText.setText(data.getLogisticsCode());
        holder.mDeleteBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData().remove(pos);
                notifyItemRemoved(pos);
                recomputePosition(pos);
            }
        });
    }

    private void recomputePosition(int pos) {
        if (pos != 0) {
            notifyItemRangeChanged(0, pos);
        }
        if (pos != getData().size()) {
            notifyItemRangeChanged(pos, getData().size() - pos);
        }
    }

    public void clear() {
        getData().clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return getData().size() == 0;
    }

    public void setLogisticsCode(List<String> logisticsCodes, int type) {
        setData(Code.convert2Code(logisticsCodes, type));
        notifyDataSetChanged();
    }

    public List<String> getLogisticsCode() {
        return Code.resolve2LogisticsCodes(getData());
    }

    public boolean contains(String logisticsCode) {
        return Code.resolve2LogisticsCodes(getData()).contains(logisticsCode);
    }

    public void add(int index, String logisticsCode, int type) {
        getData().add(index, new Code(logisticsCode, type));
        notifyItemInserted(index);
        recomputePosition(index);
    }

    public void add(String logisticsCode, int type) {
        getData().add(new Code(logisticsCode, type));
        notifyItemInserted(getData().size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_code_label)
        TextView mLabelText;

        @BindView(R.id.tv_item_code_value)
        TextView mValueText;

        @BindView(R.id.btn_delete)
        TextView mDeleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
