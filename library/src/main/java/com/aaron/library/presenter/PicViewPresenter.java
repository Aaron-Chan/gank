package com.aaron.library.presenter;

import android.support.annotation.NonNull;

import com.aaron.library.view.PicView;

/**
 * Created by AaronChan on 2017/1/9.
 */

public abstract class PicViewPresenter extends BasePresenter<PicView> {

    public PicViewPresenter(@NonNull PicView view) {
        super(view);
    }
}
