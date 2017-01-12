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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aaron.gank.R;
import com.aaron.gank.constans.Constants;
import com.aaron.gank.ui.fragment.CategoryFragment;
import com.aaron.gank.ui.fragment.DailyFragment;
import com.aaron.gank.ui.fragment.GirlsFragment;
import com.aaron.library.activity.BaseActivity;
import com.aaron.library.utils.SharePrefUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

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
    private boolean mIsGirlShown;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  UmengUpdateAgent.update(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        changeTheme();
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

    private void changeTheme() {
        boolean nightMode = SharePrefUtils.getBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, false);
        setTheme(nightMode ? R.style.NightTheme : R.style.AppTheme);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void initViews() {
        boolean nightMode = SharePrefUtils.getBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, false);
        mNavigationView.getMenu().findItem(R.id.menu_item_night_mode).setChecked(nightMode);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //toggle check state
                boolean checked = !item.isChecked();
                item.setChecked(checked);
                //close drawer
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_item_about:
                        //
                        break;
                    case R.id.menu_item_night_mode:
                        //
//                        boolean nightMode = SharePrefUtils.getBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, false);
//                        SharePrefUtils.putBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, !nightMode);
//                        item.setChecked(!nightMode);
//                        recreate();

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
                        fragmentClass = CategoryFragment.class;
                        break;
                    case R.id.menu_item_girl:
                        fragmentClass = GirlsFragment.class;
                        break;
                }
                switchFragment(fragmentClass);
                return true;
            }

        });
    }

    @Override
    protected boolean hasFragment() {
        return true;
    }

    private void toggleNightMode(@NonNull MenuItem item) {
//        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//        if(mode == Configuration.UI_MODE_NIGHT_YES) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            // blah blah
//        }
//
//        recreate();

        if (item.isChecked()) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
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
            // 设置显示方式菜单项是否显示
            if (mCurrentFragment instanceof GirlsFragment && !mIsGirlShown) {// 从非妹子切换到妹子视图
                mIsGirlShown = true;
                invalidateOptionsMenu();
            } else if (mIsGirlShown) {// 从非妹子切换到妹子视图
                mIsGirlShown = false;
                invalidateOptionsMenu();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_main;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_display_mode);
        menuItem.setVisible(mIsGirlShown);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_display_mode:
                if (mCurrentFragment instanceof GirlsFragment) {
                    ((GirlsFragment) mCurrentFragment).changeDisplayMode();
                    return true;
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(String errorMsg) {

    }
}
