package com.chuxin.wechat.fake.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chao on 2018/3/13.
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int TYPE_NORMAL = -1;

    private OnItemClickListener mListener;
    private List<T> dataSet = new ArrayList<>();
    private List<View> mHeaders = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(), parent, false);
            return onCreateHolder(view);
        }
        return new RecyclerView.ViewHolder(mHeaders.get(viewType)) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= mHeaders.size()) {
            final int realPos = position - mHeaders.size();
            onBindHolder((VH)holder, dataSet.get(realPos), realPos);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(view, dataSet.get(realPos), realPos);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mListener != null) {
                        return mListener.onItemLongClick(view, dataSet.get(realPos), realPos);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < mHeaders.size() ? position : TYPE_NORMAL;
    }

    protected abstract int getItemLayoutId();

    protected abstract RecyclerView.ViewHolder onCreateHolder(View view);

    protected abstract void onBindHolder(VH holder, T data, int pos);

    public void setData(List<T> data) {
        this.dataSet = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.dataSet;
    }

    public void addHeader(View header) {
        mHeaders.add(header);
    }

    public void removeHeader(int index) {
        mHeaders.remove(index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class OnItemClickListener<T> {
        public void onItemClick(View view, T data, int pos) {
        }

        public boolean onItemLongClick(View view, T data, int pos) { return false; }
    }
}
