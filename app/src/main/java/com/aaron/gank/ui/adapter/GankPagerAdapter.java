package com.aaron.gank.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aaron.gank.ui.fragment.GankFragment;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 * 分类子fragment的适配器
 */

public class GankPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;

    public GankPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setTitles(@NonNull List<String> titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
