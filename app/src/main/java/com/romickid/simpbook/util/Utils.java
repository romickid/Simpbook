package com.romickid.simpbook.util;

import com.romickid.simpbook.util.Date;
import java.util.Calendar;

/**
 * 工具类
 */
public class Utils {

    /**
     * 将日期类型时间转为int类型时间
     *
     * @param date 日期类型时间
     * @return int形式时间
     */
    public int switchDateToTime(Date date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        int datetime = year * 10000 + month * 100 + day;
        return datetime;
    }

    /**
     * 将int形式时间转为日期类型时间
     *
     * @param time int类型时间
     * @return Date类型时间
     */
    public Date switchTimeToDate(int time) {
        String strTime = String.valueOf(time);
        int year = Integer.valueOf(strTime.substring(0,4));
        int month = Integer.valueOf(strTime.substring(4,6));
        int day = Integer.valueOf(strTime.substring(6,8));
        return new Date(year, month, day);
    }

    /**
     * 获取特定日期的当周的第一天
     *
     * @param date 特定日期
     * @return 当周的第一天
     */
    public int getMondayOfThisWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth()-1, date.getDay());

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return switchDateToTime(new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)));
    }

    public int getSundayOfThisWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth()-1, date.getDay());

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + 6);
        return switchDateToTime(new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * 获取特定日期的当月的第一天
     *
     * @param date 特定日期
     * @return 当月的第一天
     */
    public int getFirstOfThisMonth(Date date) {
        return switchDateToTime(new Date(date.getYear(), date.getMonth(), 1));
    }

    /**
     * 获取特定日期的当年的第一天
     *
     * @param date 特定日期
     * @return 当年的第一天
     */
    public int getFirstOfThisYear(Date date) {
        return switchDateToTime(new Date(date.getYear(), 1, 1));
    }

}
