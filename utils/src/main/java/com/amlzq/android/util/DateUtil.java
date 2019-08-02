package com.amlzq.android.util;

import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.content.res.RUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amlzq on 2018/5/28.
 * <p>
 * 日期工具类
 */

public class DateUtil {

    private static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    /**
     * @param date date
     * @return 日期加时间
     */
    public static String formatDataTime(long date) {
        return FORMAT_DATETIME.format(new Date(date));
    }

    /**
     * @param date date
     * @return 返回日期
     */
    public static String formatDate(long date) {
        return formatDate(new Date(date));
    }

    public static String formatDate(Date date) {
        return FORMAT_DATE.format(date);
    }

    /**
     * @param date date
     * @return 返回时间
     */
    public static String formatTime(long date) {
        return FORMAT_TIME.format(new Date(date));
    }

    /**
     * @param year  年
     * @param month 月
     * @return 获取某年某月的总天数
     */
    public static int getDayTotal(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /**
     * @param secondTimestamp 时间戳（秒）
     * @return 将时间戳转化成日期
     */
    public static String Unix2Date(long secondTimestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(secondTimestamp * 1000);
        return formatDate(calendar.getTime());
    }

    /**
     * @param secondTimestamp 时间戳（秒）
     * @return 将时间戳转化成日期
     */
    public static String Unix2Date(String secondTimestamp) {
        long time = Long.parseLong(secondTimestamp);
        return Unix2Date(time);
    }

    /**
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断当前日期是星期几
     */
    public static int getWeek(String pTime) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(FORMAT_DATE.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param date date
     * @return 计算日期之间相隔几天
     */
    public static long compareDataToNow(String date) {
        Date passDate, nowDate;
        long diff = -100L, days = -100L;
        try {
            passDate = FORMAT_DATE.parse(date);
            String nowStr = FORMAT_DATE.format(new Date());
            nowDate = FORMAT_DATE.parse(nowStr);

            diff = passDate.getTime() - nowDate.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * @param milliseconds 毫秒/13位数
     * @return 时间格式
     */
    public static String getTimeShowString(long milliseconds) {
        String dataString = "";
        String timeStringBy24 = "";

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todayBegin = todayStart.getTime();
        Date yesterdayBegin = new Date(todayBegin.getTime() - 3600 * 24 * 1000);
        Date preYesterday = new Date(yesterdayBegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todayBegin)) {
            return getTodayTimeBucket(currentTime);
        } else if (!currentTime.before(yesterdayBegin)) {
            dataString = ContextHolder.getString(RUtil.string("yesterday"));
        } else if (!currentTime.before(preYesterday)) {
            dataString = ContextHolder.getString(RUtil.string("before_yesterday"));
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateFormatter.format(currentTime);
        }

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeFormatter.format(currentTime);
        return dataString + " " + timeStringBy24;
    }

    /**
     * @param date Date
     * @return 根据不同时间段，显示不同时间
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return ContextHolder.getString(RUtil.string("early_morning")) + " " + timeFormatter.format(date);
        } else if (hour >= 5 && hour < 12) {
            return ContextHolder.getString(RUtil.string("morning")) + " " + timeFormatter.format(date);
        } else if (hour >= 12 && hour < 18) {
            return ContextHolder.getString(RUtil.string("afternoon")) + " " + timeFormatter.format(date);
        } else if (hour >= 18 && hour < 24) {
            return ContextHolder.getString(RUtil.string("evening")) + " " + timeFormatter.format(date);
        }
        return "";
    }

    /**
     * 秒（10位数）单位的时间戳
     * PHP是这个值
     */
    public static String getSecondTimestamp() {
        // System.currentTimeMillis()的单位是毫秒(millisecond)
        long timeStampSec = System.currentTimeMillis() / 1000;
        return String.format(Locale.getDefault(), "%010d", timeStampSec);
//        String ts = System.currentTimeMillis() + "";
//        return ts.substring(0, 10);
    }

    /**
     * @param secondTimestamp 秒（10位数）单位的时间戳
     * @return 毫秒单位的时间戳
     */
    public static String toMillisecondTimestamp(String secondTimestamp) {
        return secondTimestamp + "000";
    }

    /**
     * 将时间戳转为日期
     * 格式: 当天，昨天，昨天之前
     * from com.hyphenate.util.DateUtils.java
     */
    public static String getTimestampString(Date date) {
        String pattern = null;
        String language = Locale.getDefault().getLanguage();
        boolean isChinese = language.startsWith("zh");
        long milliseconds = date.getTime();
        if (isSameDay(milliseconds)) {
            if (isChinese) {
                pattern = "aa hh:mm";
            } else {
                pattern = "hh:mm aa";
            }
        } else if (isYesterday(milliseconds)) {
            if (!isChinese) {
                return "Yesterday " + (new SimpleDateFormat("hh:mm aa", Locale.ENGLISH)).format(date);
            }

            pattern = "昨天aa hh:mm";
        } else if (isChinese) {
            pattern = "M月d日aa hh:mm";
        } else {
            pattern = "MMM dd hh:mm aa";
        }

        return isChinese ? (new SimpleDateFormat(pattern, Locale.CHINESE)).format(date) : (new SimpleDateFormat(pattern, Locale.ENGLISH)).format(date);
    }

    private static boolean isSameDay(long milliseconds) {
        TimeInfo info = getTodayStartAndEndTime();
        return milliseconds > info.getStartTime() && milliseconds < info.getEndTime();
    }

    private static boolean isYesterday(long milliseconds) {
        TimeInfo info = getYesterdayStartAndEndTime();
        return milliseconds > info.getStartTime() && milliseconds < info.getEndTime();
    }

    public static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Date date = calendar.getTime();
        long milliseconds = date.getTime();
        Calendar var4 = Calendar.getInstance();
        var4.set(11, 23);
        var4.set(12, 59);
        var4.set(13, 59);
        var4.set(14, 999);
        Date var5 = var4.getTime();
        long var6 = var5.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(milliseconds);
        info.setEndTime(var6);
        return info;
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Date date = calendar.getTime();
        long milliseconds = date.getTime();
        Calendar var4 = Calendar.getInstance();
        var4.add(5, -1);
        var4.set(11, 23);
        var4.set(12, 59);
        var4.set(13, 59);
        var4.set(14, 999);
        Date var5 = var4.getTime();
        long var6 = var5.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(milliseconds);
        info.setEndTime(var6);
        return info;
    }

}
