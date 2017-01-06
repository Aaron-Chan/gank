package com.aaron.gank.data.entity;

import java.util.Date;

/**
 * Created by Aaron on 2017/1/6.
 */

public class DailyHeader {

    private Date date;
    private String imgUrl;

    public DailyHeader(Date date, String imgUrl) {
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
