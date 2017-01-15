package com.aaron.library.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aaron on 2017/1/5.
 */
public class DateUtils {
    /**
     * 秒与毫秒的倍数
     */
    private static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    private static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    private static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    private static final int DAY = 86400000;

    private DateUtils() {
        throw new IllegalAccessError();
    }

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT;

    static {
        DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    /**
     * String 转换 Date
     *
     * @param str    str
     * @param format format
     * @return Date
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format, Locale.CHINA).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 年月日
     */
    public static String formatDate(Date date) {
        return DEFAULT_DATE_FORMAT.format(date);
    }

    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return String.format("%tc", millis);// U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format("%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format("%d分钟前", span / MIN);
        }
        // 获取当天00:00
        long wee = (now / DAY) * DAY;
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

}
