package com.aaron.gank.ui.fragment;


import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.aaron.gank.R;
import com.aaron.gank.presenter.CategoryPresenter;
import com.aaron.gank.ui.adapter.GankPagerAdapter;
import com.aaron.gank.view.CategoryView;
import com.aaron.library.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;


public class CategoryFragment extends BaseFragment implements CategoryView {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private GankPagerAdapter mGankPagerAdapter;


    @Override
    protected void initData() {
        CategoryPresenter categoryPresenter = new CategoryPresenter(this);
        categoryPresenter.getCategoriesNames();
    }

    @Override
    protected void initViews() {
        mGankPagerAdapter = new GankPagerAdapter(getChildFragmentManager());
        mViewpager.setAdapter(mGankPagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void showCategories(@NonNull List<String> names) {
        mGankPagerAdapter.setTitles(names);
    }

    @Override
    public void showError(String errorMsg) {

    }
}
