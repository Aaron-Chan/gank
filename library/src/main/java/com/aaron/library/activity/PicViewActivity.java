package com.aaron.library.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.aaron.library.R;
import com.aaron.library.R2;
import com.aaron.library.presenter.PicViewPresenter;
import com.aaron.library.utils.GlideUtils;
import com.aaron.library.utils.ToastUtils;
import com.aaron.library.view.PicView;

import butterknife.BindView;

public class PicViewActivity extends BaseActivity<PicViewPresenter> implements PicView {

    private static final String ARG_URL = "url";
    private static final String ARG_TITLE = "title";
    private static final String ARG_ZOOM = "zoom";
    private static final String ARG_THEME = "theme";

    @BindView(R2.id.photoView)
    ImageView mPhotoView;

    @BindView(R2.id.iv_common_photo)
    ImageView mCommonPhoto;
    private String mUrl;
    private String mTitle;
    private boolean mZoom;
    private int mTheme;

    /**
     * 图片 可缩放
     *
     * @param context context
     * @param url     链接
     * @param title   标题
     */
    public static void open(Context context, String url, String title) {
        context.startActivity(createIntent(context, url, title));
    }

    /**
     * 图片不缩放,主要为了gif图片
     *
     * @param context context
     * @param url     链接
     * @param title   标题
     */
    public static void openNotZoom(Context context, String url, String title) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_ZOOM, false);
        context.startActivity(intent);
    }

    public static void openNotZoomTheme(Context context, String url, String title, @StyleRes int theme) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_ZOOM, false);
        intent.putExtra(ARG_THEME, theme);
        context.startActivity(intent);
    }

    public static void openNotZoomTheme(Context context, String url, String title, View view, @StyleRes int theme) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_ZOOM, false);
        intent.putExtra(ARG_THEME, theme);
        startWithScaleAnimation(context, view, intent);
    }

    public static void openTheme(Context context, String url, String title, @StyleRes int theme) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_THEME, theme);
        context.startActivity(intent);
    }

    public static void openTheme(Context context, String url, String title, View view, @StyleRes int theme) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_THEME, theme);
        startWithScaleAnimation(context, view, intent);
    }

    public static void openNotZoom(Context context, String url, String title, View view) {
        Intent intent = createIntent(context, url, title);
        intent.putExtra(ARG_ZOOM, false);
        startWithScaleAnimation(context, view, intent);
    }

    public static void open(Context context, String url, String title, View view) {
        Intent intent = createIntent(context, url, title);
        // 共享控件
        startWithScaleAnimation(context, view, intent);
    }

    private static Intent createIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, PicViewActivity.class);
        intent.putExtra(ARG_URL, url);
        intent.putExtra(ARG_TITLE, title);
        return intent;
    }

    private static void startWithScaleAnimation(Context context, View view, Intent intent) {
        ActivityOptionsCompat activityOptionsCompat;
        activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), view.getHeight());
        ActivityCompat.startActivity(context, intent, activityOptionsCompat.toBundle());
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        mTheme = getIntent().getIntExtra(ARG_THEME, DEFAULT_INVALID_THEME_ID);
        mUrl = getIntent().getStringExtra(ARG_URL);
        mTitle = getIntent().getStringExtra(ARG_TITLE);
        // 默认可缩放
        mZoom = getIntent().getBooleanExtra(ARG_ZOOM, true);
    }

    @Override
    protected void initData() {
        mPresenter = new PicViewPresenter(this);
    }


    @Override
    protected void initViews() {
        setTitle(mTitle, true);
        ImageView photo = mZoom ? mPhotoView : mCommonPhoto;
        if (mZoom) {
            mPhotoView.setVisibility(View.VISIBLE);
            mCommonPhoto.setVisibility(View.GONE);
        } else {
            mPhotoView.setVisibility(View.GONE);
            mCommonPhoto.setVisibility(View.VISIBLE);
        }
        photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(PicViewActivity.this)
                        .setMessage(R.string.dialog_message_save_pic)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.downloadFile(mUrl);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;

            }
        });
        GlideUtils.loadImage(photo, mUrl);
    }

    @Override
    protected boolean hasFragment() {
        return false;
    }

    protected int getLayoutId() {
        return R.layout.activity_pic_view;
    }

    @Override
    protected int getCurrentThemeId() {
        return mTheme;
    }
    @Override
    public void showError(String errorMsg) {
        ToastUtils.showShort(R.string.toast_save_pic_failed);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void showDownloadSuccess() {
        ToastUtils.showShort(R.string.toast_save_pic_success);
    }

}
