package com.chuxin.wechat.fake.views.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;

/**
 *
 * Created by chao on 2018/3/12.
 */

public class NavWidget extends TabWidget {

    private ViewPager mPager;
    private ViewPager.SimpleOnPageChangeListener mPageChangedListener;
    private int lastPosition = 0;

    public NavWidget(Context context) {
        super(context);
        setDividerDrawable(null);
    }

    public NavWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDividerDrawable(null);
    }

    public NavWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDividerDrawable(null);
    }

    public void addTab(Tab tab) {
        addView(tab);
    }

    public Tab getTab(int position) {
        return (Tab) getChildTabViewAt(position);
    }

    public void setupWithViewPager(ViewPager pager) {
        if (this.mPager != null) {
            if (mPageChangedListener != null) {
                mPager.removeOnPageChangeListener(mPageChangedListener);
            }
        }
        if (pager != null) {
            this.mPager = pager;
            mPageChangedListener = new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    setCurrentTab(position);
                }
            };
            this.mPager.addOnPageChangeListener(mPageChangedListener);
            for (int i = 0; i < getTabCount(); i++) {
                getTab(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPager.setCurrentItem(indexOfChild(v));
                    }
                });
            }
            setCurrentTab(0);
        }
    }

    public void setCurrentTab(int position) {
        getTab(lastPosition).selected(false);
        getTab(position).selected(true);
        lastPosition = position;
    }

    public static class Tab extends LinearLayout {

        ImageView tabIcon;
        TextView tabName;

        public Tab(Context context) {
            super(context);
            initView();
        }

        public Tab(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initView();
        }

        private void initView() {
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER);
            setPadding(0, dip2px(6), 0, dip2px(6));
            tabIcon = new ImageView(getContext());
            tabIcon.setLayoutParams(new LayoutParams(dip2px(20), dip2px(20)));
            tabName = new TextView(getContext());
            tabName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            addView(tabIcon);
            addView(tabName);
        }

        public Tab icon(@DrawableRes int resId) {
            tabIcon.setImageResource(resId);
            return this;
        }

        public Tab name(@StringRes int resId) {
            tabName.setText(resId);
            return this;
        }

        public void selected(boolean selected) {
            int color = getResources().getColor(selected ? R.color.colorPrimary : R.color.text_color_light);
            tabIcon.setColorFilter(color);
            tabName.setTextColor(color);
        }

        private int dip2px(int dip) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
        }
    }
}

