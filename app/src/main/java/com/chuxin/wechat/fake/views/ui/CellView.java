package com.chuxin.wechat.fake.views.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class CellView extends LinearLayout {

    private String mLabel = "";
    private String mValue = "";
    private boolean showArrow = false;
    private boolean showDivider = true;
    private int mLabelColor;
    private int mValueColor;

    private TextView mLabelText;
    private TextView mValueText;
    private ImageView mArrow;
    private View divider;


    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs (Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CellView);
            mLabel = ta.getString(R.styleable.CellView_cellLabel);
            mValue = ta.getString(R.styleable.CellView_cellValue);
            mLabelColor = ta.getColor(R.styleable.CellView_cellLabelColor, Color.parseColor("#323232"));
            mValueColor = ta.getColor(R.styleable.CellView_cellValueColor, Color.parseColor("#888888"));
            showArrow = ta.getBoolean(R.styleable.CellView_showArrow, false);
            showDivider = ta.getBoolean(R.styleable.CellView_showDivider, false);
        }

    }

    private void initView () {
        inflate(getContext(), R.layout.view_base_cell, this);

        mLabelText = findViewById(R.id.tv_cell_label);
        mValueText = findViewById(R.id.tv_cell_value);
        mArrow = findViewById(R.id.iv_arrow_right);
        divider = findViewById(R.id.divider);

        mLabelText.setText(mLabel);
        mLabelText.setTextColor(mLabelColor);
        mValueText.setTextColor(mValueColor);
        mValueText.setText(mValue);
        mArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
    }

    public void setValue(String text) {
        mValueText.setText(text);
    }

    public void setLabel(String text) {
        mLabelText.setText(text);
    }

    public void showDivider(boolean flag) {
        this.showDivider = flag;
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
    }

    public void showArrow(boolean flag) {
        this.showArrow = flag;
        mArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);
    }

    public void setLabelColor(@ColorInt int color) {
        this.mLabelColor = color;
        mLabelText.setTextColor(mLabelColor);
    }

    public void setValueColor(@ColorInt int color) {
        this.mValueColor = color;
        mValueText.setTextColor(mValueColor);
    }

}
