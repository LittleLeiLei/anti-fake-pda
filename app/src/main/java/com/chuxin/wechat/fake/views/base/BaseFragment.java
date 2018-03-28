package com.chuxin.wechat.fake.views.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;

import butterknife.ButterKnife;

/**
 *
 * Created by chao on 2018/3/12.
 */

public abstract class BaseFragment extends Fragment {

    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mInflater = inflater;

        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    protected abstract int getLayoutId();

    protected void initView (View view) {}

    protected void initData () {}


    protected void toast (String content) {
        MyApp.get().toast(content);
    }

    protected boolean resolveIntent (Intent intent) {
        boolean flag = true;
        if (getActivity() != null && intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            flag = false;
            toast("未找到对应组件");
        }
        return flag;
    }
}
