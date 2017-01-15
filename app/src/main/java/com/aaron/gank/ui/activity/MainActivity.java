package com.aaron.gank.ui.activity;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.aaron.gank.R;
import com.aaron.gank.constans.Constants;
import com.aaron.gank.ui.fragment.CategoryFragment;
import com.aaron.gank.ui.fragment.DailyFragment;
import com.aaron.gank.ui.fragment.GirlsFragment;
import com.aaron.gank.utils.ThemeUtils;
import com.aaron.library.activity.BaseActivity;
import com.aaron.library.utils.SharePrefUtils;
import com.umeng.update.UmengUpdateAgent;

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
         UmengUpdateAgent.update(this);
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
        // 加入夜间模式的Switch
        SwitchCompat nightModeSwitchCompat = new SwitchCompat(this);
        boolean nightMode = SharePrefUtils.getBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, false);
        nightModeSwitchCompat.setChecked(nightMode);
        nightModeSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePrefUtils.putBoolean(MainActivity.this, Constants.SP_KEY_NIGHT_MODE, isChecked);
                recreate();
            }
        });
        mNavigationView.getMenu().findItem(R.id.menu_item_night_mode)
                .setActionView(nightModeSwitchCompat);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //close drawer
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_item_about:
                        // 显示关于页面
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setTitle("关于");
                        View view = getLayoutInflater().inflate(R.layout.dialog_about_app, null);
                        dialog.setContentView(view);
                        dialog.show();
                        break;
                    case R.id.menu_item_night_mode:
                        break;
                }
                return true;
            }
        });
        // 设置 抽屉视图
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
        // 设置底部item被选状态
        if (mCurrentFragment instanceof DailyFragment) {
            mBottomNavigation.getMenu().findItem(R.id.menu_item_home).setChecked(true);
        } else if (mCurrentFragment instanceof CategoryFragment) {
            mBottomNavigation.getMenu().findItem(R.id.menu_item_category).setChecked(true);
        } else if (mCurrentFragment instanceof GirlsFragment) {
            mBottomNavigation.getMenu().findItem(R.id.menu_item_girl).setChecked(true);
        }
    }

    @Override
    protected boolean hasFragment() {
        return true;
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
    protected int getCurrentThemeId() {
        return ThemeUtils.getCurrentTheme();
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_main;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_display_mode);
        menuItem.setVisible(mIsGirlShown);
        Drawable drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, android.R.color.white));
            menuItem.setIcon(drawable);
        }
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
