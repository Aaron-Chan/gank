package com.aaron.library.rx;

import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Aaron on 2017/1/9.
 */

public abstract class BaseDownloadSubscriber extends Subscriber<ResponseBody> {

    private String mFilePath;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private long mFileSizeDownloaded = 0;
    private long mFileSize = 0;

    public BaseDownloadSubscriber(String filename) {
        mFilePath = getFileStorePath() + File.separator + filename;
    }

    /**
     * 获取文件保存路径
     *
     * @return 路径
     */
    protected abstract String getFileStorePath();


    protected abstract void onProgress(double progress, long downloadByte, long totalByte);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Logger.e(e.getMessage());
    }

    @Override
    public final void onNext(ResponseBody responseBody) {
    }


    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(mFilePath);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress((mFileSizeDownloaded * 1.0f / mFileSize), mFileSizeDownloaded, mFileSize);

                        }
                    });
                }

                outputStream.flush();

                return true;
            } catch (final IOException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(e);
                    }
                });
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
            return false;
        }

    }
}
