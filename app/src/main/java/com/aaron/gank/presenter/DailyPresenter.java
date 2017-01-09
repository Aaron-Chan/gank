package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.constans.Constants;
import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.Response;
import com.aaron.gank.data.entity.CategoryTitle;
import com.aaron.gank.data.entity.DailyHeader;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.model.impl.DailyDataModel;
import com.aaron.gank.rx.DefaultSubscriber;
import com.aaron.gank.view.DailyView;
import com.aaron.library.presenter.ListPresenter;
import com.aaron.library.rx.BaseSubscriber;
import com.aaron.library.utils.DateUtils;
import com.aaron.library.utils.RxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by Aaron on 2016/12/25.
 */

public class DailyPresenter extends ListPresenter<DailyView> {

    private IDailyDataModel mDailyDataModel;
    private List<Date> mPublishDates;
    private boolean mRefresh;
    private int mTotalPageCount;

    public DailyPresenter(@NonNull DailyView view, @NonNull List items) {
        super(view, items);
        mDailyDataModel = DailyDataModel.getInstance();
        mPublishDates = new ArrayList<>();
    }

    @Override
    protected void loadData(final int pageIndex) {
        if (pageIndex == 1) {
            mRefresh = true;
        }

        // 获取发布日期
        if (mPublishDates == null || mRefresh) {
            mCompositeSubscription.add(mDailyDataModel.getPublishDates()
                    .compose(RxUtils.<Response<List<String>>>getDefaultTransformer())
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            getDailyData(pageIndex);
                        }
                    })
                    .subscribe(new DefaultSubscriber<Response<List<String>>>() {
                        @Override
                        public void onSuccess(@NonNull Response<List<String>> listResponse) {
                            List<String> results = listResponse.getResults();
                            mPublishDates.clear();
                            for (String result : results) {
                                mPublishDates.add(DateUtils.string2Date(result, Constants.PUBLISH_DATE_FORMAT));
                            }
                            // 降序排列
                            Collections.sort(mPublishDates);
                            Collections.reverse(mPublishDates);
                            // 计算总页数
                            mTotalPageCount = mPublishDates.size() / getPageSize()
                                    + (mPublishDates.size() % getPageSize() == 0 ? 0 : 1);
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            onDataObtainFailed(e);
                        }
                    }));
        } else {
            getDailyData(pageIndex);
        }
    }

    @Override
    protected int getPageSize() {
        return 3;
    }

    // 获取每日干货
    private void getDailyData(final int pageIndex) {
        if (mTotalPageCount < pageIndex) {
            // 加载完成
            mView.showLoadCompleted();
        } else {
            mCompositeSubscription.add(mDailyDataModel.getDailyData(getDates(pageIndex))
                    .compose(RxUtils.<List<DailyData>>getDefaultTransformer())
                    .map(new Func1<List<DailyData>, List<GankEntity>>() {
                        @Override
                        public List<GankEntity> call(List<DailyData> dailyDatas) {
                            // 转换
                            return transformToGankList(dailyDatas);
                        }
                    })
                    .subscribe(new BaseSubscriber<List<GankEntity>>() {
                        @Override
                        public void onSuccess(@NonNull List<GankEntity> gankEntityList) {
                            onDataObtained(pageIndex, gankEntityList);
                        }

                        @Override
                        public void onFailed(Throwable e) {
                            onDataObtainFailed(e);
                        }
                    }));

        }
    }


    @SuppressWarnings("unchecked")
    @NonNull
    private List<GankEntity> transformToGankList(List<DailyData> dailyDatas) {
        List gankList = new ArrayList<>();
        for (DailyData dailyData : dailyDatas) {
            DailyData.Results results = dailyData.getResults();
            if (results.welfareData != null) {
                GankEntity gankEntity = results.welfareData.get(0);
                gankList.add(new DailyHeader(gankEntity.getPublishedAt(), gankEntity.getUrl()));
            }
            if (results.androidData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_ANDROID));
                gankList.addAll(results.androidData);
            }
            if (results.iosData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_IOS));
                gankList.addAll(results.iosData);
            }
            if (results.jsData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_JS));
                gankList.addAll(results.jsData);
            }
            if (results.videoData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_VIDEO));
                gankList.addAll(results.videoData);
            }
            if (results.resourcesData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_RESOURCES));
                gankList.addAll(results.resourcesData);
            }
            if (results.appData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_APP));
                gankList.addAll(results.appData);
            }
            if (results.recommendData != null) {
                gankList.add(new CategoryTitle(Constants.CATEGORY_RECOMMEND));
                gankList.addAll(results.recommendData);
            }
        }
        return gankList;
    }

    private List<Date> getDates(int pageIndex) {
        int fromIndex = (pageIndex - 1) * getPageSize();
        int toIndex = fromIndex + getPageSize();
        // 防止越界
        if (toIndex > mPublishDates.size()) {
            toIndex = mPublishDates.size();
        }
        return mPublishDates.subList(fromIndex, toIndex);
    }


}
