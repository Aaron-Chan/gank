package com.aaron.gank.repository;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.Response;
import com.aaron.gank.data.SearchData;
import com.aaron.gank.data.entity.GankEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 * gank.io api service
 */

public interface GankService {

    String BASE_URL = "http://www.gank.io/api/";
    String GANK_ITEM_PUBLISH_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 获取某天的干货
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DailyData> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * 随机获取某个目录的干货
     *
     * @param category 目录
     * @param count    个数
     * @return
     */
    @GET("random/data/{category}/{count}")
    Observable<Response<List<GankEntity>>> getRandomData(@Path("category") String category, @Path("count") int count);

    /**
     * 获取发过干货日期
     *
     * @return 2017-01-05 形式日期
     */
    @GET("day/history")
    Observable<Response<List<String>>> getPublishDates();

    /**
     * 获取某个目录的干货
     *
     * @param category 目录
     * @param count    每页个数
     * @param page     页数
     * @return
     */
    @GET("data/{category}/{count}/{page}")
    Observable<Response<List<GankEntity>>> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);

    /**
     * 搜索干货
     *
     * @param category 目录
     * @param count    每页个数
     * @param page     页数
     * @return
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<SearchData> getSearchData(@Path("category") String category, @Path("count") int count, @Path("page") int page);

}
