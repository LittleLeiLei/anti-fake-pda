package com.chuxin.wechat.fake.views.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.utils.CommonUtils;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class PtrLayout extends LinearLayout implements View.OnTouchListener {

    private static final String TAG = PtrLayout.class.getSimpleName();

    private RecyclerView recyclerView;
    private View loadingView;

    private int loadingViewHeight = 0;
    private int pointerId = 0;
    private float startY = 0;
    private float startIntercept = 0;
    private float endY = 0;
    private float offset = 0;
    private int threshold = 0;
    private int loadingLayoutId = 0;
    private boolean isScroll2Bottom = true;
    private boolean isRefreshing = false;
    final int INVALID = -1;

    private OnPtrLoadingListener listener;

    public PtrLayout(Context context) {
        this(context, null);
    }

    public PtrLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PtrLayout);
        loadingLayoutId = typedArray.getResourceId(R.styleable.PtrLayout_loadingLayout, -1);
        loadingViewHeight = (int) CommonUtils.dp2px(35);
        threshold = loadingViewHeight;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof RecyclerView) {
                recyclerView = (RecyclerView) getChildAt(i);
            }
        }

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Log.e(TAG, "ChatRecordLayout has no RecyclerView element~");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isRefreshing || ViewCompat.canScrollVertically(recyclerView, 1) && ViewCompat.canScrollVertically(recyclerView, -1)) {
            return false;
        }

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isScroll2Bottom = true;
                pointerId = MotionEventCompat.getPointerId(event, 0);
                startY = getMotionEventY(event);
                if (startIntercept == INVALID)
                    return false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointerId == INVALID) return false;
                float moved = getMotionEventY(event);
                if (moved == INVALID) return false;

                isScroll2Bottom = moved < startY;
                startY = moved;
                break;
            case MotionEvent.ACTION_UP:
                pointerId = INVALID;
                break;
        }
        return canLoadMore() && !isScroll2Bottom;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isRefreshing) {
            return super.onTouchEvent(event);
        }

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointerId = MotionEventCompat.getPointerId(event, 0);
                startY = getMotionEventY(event);
                break;
            case MotionEvent.ACTION_MOVE:
                endY = getMotionEventY(event);
                offset = (endY - startY) * 0.8f;
                if (offset > threshold && offset < 2 * threshold) {
                    offset = threshold + (int) ((offset - threshold) * 0.6);
                }
                recyclerView.setTranslationY(offset);
                loadingView.setTranslationY(offset);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isRefreshing = true;

                if (offset < threshold) {
                    //do nothing
                    animToStartPosition();
                    return false;
                }

                animToRefreshPosition();

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animToStartPosition();
                        if (listener != null) {
                            listener.onRefresh();
                        }
                    }
                }, 1000);
                break;
        }

        return false;
    }

    /**
     * 刷新状态
     */
    private void animToRefreshPosition() {
        recyclerView.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .translationY(loadingViewHeight)
                .setListener(null)
                .start();

        loadingView.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .translationY(loadingViewHeight)
                .start();
    }


    /**
     * 回复到正常状态
     */
    private void animToStartPosition() {
        recyclerView.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isRefreshing = false;
                    }
                })
                .start();

        loadingView.animate()
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .translationY(0)
                .start();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        setClickable(true);
        addView(initLoadingView(), 0);
        setOnTouchListener(this);
        setPadding(0, -loadingViewHeight, 0, 0);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    /**
     * loading view
     *
     * @return
     */
    private View initLoadingView() {
        if (loadingLayoutId != -1) {
            loadingView = LayoutInflater.from(getContext()).inflate(loadingLayoutId, null);
        } else {
            //默认loading view
            loadingView = new ProgressBar(getContext());
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, loadingViewHeight);
            loadingView.setLayoutParams(params);
            loadingView.setPadding(0, (int)CommonUtils.dp2px(6), 0, (int)CommonUtils.dp2px(6));
        }
        return loadingView;
    }

    /**
     * 是否能加载更多
     *
     * @return
     */
    private boolean canLoadMore() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
    }

    /**
     * 获得触摸点的Y值
     *
     * @param e
     * @return
     */
    private float getMotionEventY(MotionEvent e) {
        int index = MotionEventCompat.findPointerIndex(e, pointerId);
        if (index < 0) return INVALID;
        return MotionEventCompat.getY(e, index);
    }

    public void setOnLoadingListener(OnPtrLoadingListener listener) {
        this.listener = listener;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public interface OnPtrLoadingListener {
        void onRefresh();
    }

}
