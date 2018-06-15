package nkucs1416.simpbook.network;

import nkucs1416.simpbook.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 * 工具类
 */

public class Utils {

    /**
     * 将日期类型时间转为int类型时间
     *
     * @param date 日期类型时间
     */
    public int switchDatetoTime(Date date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        int datetime = year * 10000 + month * 100 + day;
        return datetime;
    }

    /**
     * 将int类型时间转为日期类型时间
     *
     * @param time int类型时间
     */
    public Date switchTimetoDate(int time) {
        int year = time % 10000;
        int month = time % 100 - year * 100;
        int day = time - year * 10000 - month * 100;
        Date datetime = new Date(year, month, day);
        return datetime;
    }
    /**
     * 求出本周第一天
     *
     *
     */
    public int getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        SimpleDateFormat s=new SimpleDateFormat("yyyyMMdd");
        String curDate = s.format(c.getTime());  //当前日期
        return Integer.valueOf(curDate).intValue();
    }
    /**
     * 求出本月第一天
     *
     *
     */
    public int getFirstOfThisMonth(Date date) {
        int dayofMonth = switchDatetoTime(new Date(date.getYear(), date.getMonth(), 1));
        return dayofMonth;
    }
    /**
     * 求出本年第一天
     *
     *
     */
    public int getFirstOfThisYear(Date date) {
        int dayofYear = switchDatetoTime(new Date(date.getYear(), 1, 1));
        return dayofYear;
    }
}
