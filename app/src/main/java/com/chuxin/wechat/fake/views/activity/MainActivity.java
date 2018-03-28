package com.chuxin.wechat.fake.views.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.utils.CommonUtils;
import com.chuxin.wechat.fake.views.adapter.MainPagerAdapter;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.chuxin.wechat.fake.views.fragment.HomePageFragment;
import com.chuxin.wechat.fake.views.fragment.PersonalFragment;
import com.chuxin.wechat.fake.views.ui.NavWidget;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main) ViewPager mPager;
    @BindView(R.id.nw_nav) NavWidget mNavWidget;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toast(getString(R.string.text_login_success));
    }

    @Override
    protected void initView() {
        initNavWidget();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    public void onBackPressed() {
        CommonUtils.doubleBack2Finish(this, System.currentTimeMillis());
    }

    private void initNavWidget () {
        MainPagerAdapter mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mAdapter.add(HomePageFragment.newInstance(), getString(R.string.nav_tab_home))
            .add(PersonalFragment.newInstance(), getString(R.string.nav_tab_personal))
            .commit();
        mPager.setAdapter(mAdapter);
        mNavWidget.addTab(new NavWidget.Tab(this).icon(R.mipmap.ic_scan).name(R.string.nav_tab_home));
        mNavWidget.addTab(new NavWidget.Tab(this).icon(R.mipmap.ic_personal).name(R.string.nav_tab_personal));
        mNavWidget.setupWithViewPager(mPager);
    }
}
