package nkucs1416.simpbook.network;

import nkucs1416.simpbook.util.Date;


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
}
