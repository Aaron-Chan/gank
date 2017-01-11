package com.aaron.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.aaron.library.R;
import com.aaron.library.R2;
import com.aaron.library.presenter.PicViewPresenter;
import com.aaron.library.utils.GlideUtils;
import com.aaron.library.utils.ToastUtils;
import com.aaron.library.view.PicView;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PicViewActivity extends BaseActivity<PicViewPresenter> implements PicView {

    private static final String ARG_URL = "url";
    private static final String ARG_TITLE = "title";

    @BindView(R2.id.photoView)
    ImageView mPhotoView;
    private String mUrl;
    private String mTitle;
    private PhotoViewAttacher mAttacher;


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
        mPresenter = new PicViewPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
    }


    @Override
    protected void initViews() {
        setTitle(mTitle, true);
        mAttacher = new PhotoViewAttacher(mPhotoView);
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
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
        ViewCompat.setTransitionName(mPhotoView, getString(R.string.pic_activity_transition_name));
        GlideUtils.loadImage(mPhotoView, mUrl);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPhotoView.getLayoutParams();
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
//        mPhotoView.setLayoutParams(layoutParams);
    }


    protected int getLayoutId() {
        return R.layout.activity_pic_view;
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.showShort(R.string.toast_save_pic_failed);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showDownloadSuccess() {
        ToastUtils.showShort(R.string.toast_save_pic_success);
    }

}
