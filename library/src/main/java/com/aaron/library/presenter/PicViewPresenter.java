package com.aaron.library.presenter;

import android.support.annotation.NonNull;

import com.aaron.library.AaronApplication;
import com.aaron.library.rx.BaseDownloadSubscriber;
import com.aaron.library.rx.FileService;
import com.aaron.library.rx.ServiceFactory;
import com.aaron.library.utils.FileUtils;
import com.aaron.library.utils.RxUtils;
import com.aaron.library.view.PicView;

import java.util.UUID;

import okhttp3.ResponseBody;
import rx.functions.Action1;

/**
 * Created by AaronChan on 2017/1/9.
 */

public class PicViewPresenter extends BasePresenter<PicView> {

    public PicViewPresenter(@NonNull PicView view) {
        super(view);
    }

    public void downloadFile(String url) {
        String filename = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        final BaseDownloadSubscriber baseDownloadSubscriber = new BaseDownloadSubscriber(filename) {
            @Override
            protected String getFileStorePath() {
                return FileUtils.getCacheDir(AaronApplication.sContext).getAbsolutePath();
            }

            @Override
            protected void onProgress(double progress, long downloadByte, long totalByte) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError(e.getMessage());
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mCompositeSubscription.remove(this);
                mView.showDownloadSuccess();
            }
        };
        mCompositeSubscription.add(ServiceFactory.getInstance().createService(FileService.class)
                .download(url)
                .compose(RxUtils.<ResponseBody>getDefaultTransformer())
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        baseDownloadSubscriber.writeResponseBodyToDisk(responseBody);
                    }
                })
                .subscribe(baseDownloadSubscriber));
    }
}
