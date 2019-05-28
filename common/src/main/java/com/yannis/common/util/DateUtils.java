package com.yannis.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  14:58
 * @email : 923080261@qq.com
 * @description : 时间格式工具类
 */
public class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String JUHE_FORMAT_PATTERN = "MM/dd";

    private static final String FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String DATE_AND_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_AND_TIME_PATTERN_WITH_WEEK = "yyyy-MM-dd HH:mm:ss EEEE";

    static final String FORMAT_PATTERN_SHORT = "yyyy-MM-dd";
    private static String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 获取当前日期
     *
     * @return MM/dd
     */
    public static String getJuheDate() {
        SimpleDateFormat format = new SimpleDateFormat(JUHE_FORMAT_PATTERN, Locale.CHINA);
        String date = format.format(new Date());
        String front = date.substring(0, 2);
        String behind = date.substring(3, 5);
        if (front.startsWith("0")) {
            front = front.substring(1, front.length());
        }
        if (behind.startsWith("0")) {
            behind = behind.substring(1, behind.length());
        }
        return String.valueOf(front + "/" + behind);


    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN, Locale.CHINA);
        return format.format(new Date());

    }


    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 更具毫秒数获取当前是星期几
     *
     * @param timeMiss
     * @return
     */
    public static String getWeekOfDate(long timeMiss) {
        Date date = new Date(timeMiss);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String getCurrentDateAndTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATE_AND_TIME_PATTERN);
        return format.format(new Date());
    }

    public static String getDesignedDateAndTimeWithWeek(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATE_AND_TIME_PATTERN_WITH_WEEK);
        return format.format(date);
    }

    public static String getDesignedDataAndTime(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATE_AND_TIME_PATTERN);
        return format.format(date);
    }

    public static String getDesignedDataAndTime(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATE_AND_TIME_PATTERN);
        return format.format(time);

    }

    /**
     * 获取制定毫秒数之前的日期
     *
     * @param timeDiff
     * @return
     */
    public static String getDesignatedDate(long timeDiff) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);
        long nowTime = System.currentTimeMillis();
        long designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     */
    public static String getPrefixDate(String count) {
        Calendar cal = Calendar.getInstance();
        int day = 0 - Integer.parseInt(count);
        // int amount   代表天数
        cal.add(Calendar.DATE, day);
        Date datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);
        return format.format(datNew);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);
        return format.format(date);
    }

    /**
     * 字符串转换日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        //str =  " 2008-07-10 19:20:00 " 格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);
        if (!TextUtils.isEmpty(str)) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * java中怎样计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
     */
    public void timeSubtract() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin = null;
        Date end = null;
        try {
            begin = dfs.parse("2004-01-02 11:30:24");
            end = dfs.parse("2004-03-26 13:31:40");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 除以1000是为了转换成秒
        long between = (end.getTime() - begin.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分"
                + second1 + "秒");
    }
}
