package com.chuxin.wechat.fake.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.views.activity.LoginActivity;
import com.chuxin.wechat.fake.views.base.BaseFragment;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人
 * Created by chao on 2018/3/12.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.tv_manager_name)
    TextView mManagerText;

    @BindView(R.id.tv_role_name)
    TextView mRoleText;

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mManagerText.setText(MyApp.get().getManagerWrapper().getManager().getName());
        mRoleText.setText(MyApp.get().getManagerWrapper().getManager().getFakeRole().getName());
    }

    @OnClick(R.id.btn_setting)
    public void redirect2Setting() {
        Intent intent = new Intent(IntentAction.PAGE_SETTING);
        resolveIntent(intent);
    }
}
