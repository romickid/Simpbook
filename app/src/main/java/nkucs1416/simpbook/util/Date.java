package nkucs1416.simpbook.util;

import java.util.Calendar;

public class Date {
    private int year;
    private int month;
    private int day;

    /**
     * 使用年/月/日作为参数的构造函数
     * @param y 年
     * @param m 月
     * @param d 日
     */
    public Date(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
    }

    /**
     * 调用系统当前日期的构造函数
     */
    public Date() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取"日"
     * @return 日
     */
    public int getDay() {
        return day;
    }

    /**
     * 获取"月"
     * @return 月
     */
    public int getMonth() {
        return month;
    }

    /**
     * 获取"年"
     * @return 年
     */
    public int getYear() {
        return year;
    }

    /**
     * 获取"星期"
     * @return 星期的字符串
     */
    public String getWeekOfDate() {
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,day);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
