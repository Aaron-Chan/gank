package com.aaron.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.MenuItem;
import android.view.View;

import com.aaron.library.R;
import com.aaron.library.R2;
import com.aaron.library.presenter.PicViewPresenter;
import com.aaron.library.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

public class PicViewActivity extends BaseActivity<PicViewPresenter> {

    private static final String ARG_URL = "url";
    private static final String ARG_TITLE = "title";

    @BindView(R2.id.photoView)
    PhotoView mPhotoView;
    private String mUrl;
    private String mTitle;


    public static void open(Activity activity, String url, String title) {
        activity.startActivity(createIntent(activity, url, title));
    }

    public static void open(Activity activity, String url, String title, View view) {
        Intent intent = createIntent(activity, url, title);

        // 共享控件
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, activity.getString(R.string.pic_activity_transition_name));

//        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
//                view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), view.getHeight());
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
    }

    private static Intent createIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, PicViewActivity.class);
        intent.putExtra(ARG_URL, url);
        intent.putExtra(ARG_TITLE, title);
        return intent;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(ARG_URL);
        mTitle = getIntent().getStringExtra(ARG_TITLE);
    }

    @Override
    protected void initData() {
        mPresenter = getPicViewPresenter();
    }

    protected PicViewPresenter getPicViewPresenter() {
        return null;
    }

    @Override
    protected void initViews() {
        setTitle(mTitle, true);
        ViewCompat.setTransitionName(mPhotoView, getString(R.string.pic_activity_transition_name));
        GlideUtils.loadImage(mPhotoView, mUrl);
    }


    protected int getLayoutId() {
        return R.layout.activity_pic_view;
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R2.id.iv_download)
    public void onClick() {

    }
}
