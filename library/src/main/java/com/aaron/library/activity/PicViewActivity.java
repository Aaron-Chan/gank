package com.aaron.library.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    @BindView(R2.id.photoView)
    ImageView mPhotoView;
    private String mUrl;
    private String mTitle;


    public static void open(Context context, String url, String title) {
        context.startActivity(createIntent(context, url, title));
    }

    public static void open(Context context, String url, String title, View view) {
        Intent intent = createIntent(context, url, title);
        // 共享控件
        ActivityOptionsCompat activityOptionsCompat;
        activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                view, view.getWidth() / 2, view.getHeight() / 2, view.getWidth(), view.getHeight());
        ActivityCompat.startActivity(context, intent, activityOptionsCompat.toBundle());
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
        mPresenter = new PicViewPresenter(this);
    }


    @Override
    protected void initViews() {
        setTitle(mTitle, true);
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
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
        GlideUtils.loadImage(mPhotoView, mUrl);
    }


    protected int getLayoutId() {
        return R.layout.activity_pic_view;
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
