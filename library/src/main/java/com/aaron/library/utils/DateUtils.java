package com.aaron.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aaron on 2017/1/5.
 */
public class DateUtils {

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

}
