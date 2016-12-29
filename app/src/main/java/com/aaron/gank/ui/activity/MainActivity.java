package com.aaron.gank.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aaron.gank.R;
import com.aaron.gank.presenter.MainPresenter;
import com.aaron.gank.ui.fragment.DailyFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private Map<String, Fragment> mFragmentMap = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();

        mCurrentFragment = mFragmentManager.findFragmentById(R.id.fl_content);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCurrentFragment == null) {
            mCurrentFragment = new DailyFragment();
            transaction.add(R.id.fl_content, mCurrentFragment);
        }
        List<Fragment> fragments = mFragmentManager.getFragments();

        if (savedInstanceState != null) {
            for (Fragment fragment : fragments) {
                transaction.hide(fragment);
            }
        }
        //避免当state丢失时抛出异常
        transaction.show(mCurrentFragment).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //toggle check state
                item.setChecked(!item.isChecked());
                //close drawer
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_item_about:
                        //
                        break;
                }
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class<?> fragmentClass = null;
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        fragmentClass = DailyFragment.class;
                        break;
                    case R.id.menu_item_category:
                        fragmentClass = DailyFragment.class;
                        break;
                    case R.id.menu_item_girl:
                        fragmentClass = DailyFragment.class;
                        break;
                }
                switchFragment(fragmentClass);
                return true;
            }

        });
    }

    private void switchFragment(Class<?> fragmentClass) {
        if (fragmentClass != null) {
            Fragment fragment;
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            String key = fragmentClass.getName();
            fragment = mFragmentMap.get(key);
            if (fragment == null) {
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    mFragmentMap.put(key, fragment);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            transaction.hide(mCurrentFragment);
            if (fragment.isAdded()) {
                transaction.show(fragment).commit();
            } else {
                fragment.setUserVisibleHint(true);
                transaction.add(R.id.fl_content, fragment).commit();
            }
            mCurrentFragment = fragment;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showError(String errorMsg) {

    }
}
