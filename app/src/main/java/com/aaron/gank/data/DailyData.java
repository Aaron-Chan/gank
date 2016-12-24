package com.aaron.gank.data;

import com.aaron.gank.data.entity.GankEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aaron on 2016/12/24.
 */

public class DailyData extends Response<DailyData.Results> {

    protected List<String> category;

    public class Results {
        @SerializedName("Android")
        public List<GankEntity> androidData;

        @SerializedName("福利")
        public List<GankEntity> welfareData;

        @SerializedName("iOS")
        public List<GankEntity> iosData;

        @SerializedName("前端")
        public List<GankEntity> jsData;

        @SerializedName("休息视频")
        public List<GankEntity> videoData;

        @SerializedName("拓展资源")
        public List<GankEntity> resourcesData;

        @SerializedName("App")
        public List<GankEntity> appData;

        @SerializedName("瞎推荐")
        public List<GankEntity> recommendData;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
