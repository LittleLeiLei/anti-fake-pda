package com.chuxin.wechat.fake.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chao on 2018/3/12.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> frgs = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public MainPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter add (Fragment frg, String title) {
        frgs.add(frg);
        titles.add(title);
        return this;
    }

    public void commit () {
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return frgs.get(position);
    }

    @Override
    public int getCount() {
        return frgs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
