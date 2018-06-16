package nkucs1416.simpbook.util;

import android.widget.TextView;

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


    // static函数
    /**
     * 获取String形式的日期
     *
     * @param date 日期
     * @return String
     */
    public static String getStrDate(Date date) {
        return date.getYear() + "/" + date.getMonth() + "/" + date.getDay();
    }

    /**
     * 设置日期为文本框显示内容
     * @param textView 传入的textView
     * @param date 日期
     */
    public static void setTextViewDate(TextView textView, Date date) {
        textView.setText(getStrDate(date));
    }

    /**
     * 从textView中获取日期信息
     * @param textView 被获取的textView
     * @return 日期实例
     */
    public static Date getDate(TextView textView) {
        String strDate = textView.getText().toString();
        String[] listStrDate = strDate.split("/");
        return new Date(
                Integer.parseInt(listStrDate[0]),
                Integer.parseInt(listStrDate[1]),
                Integer.parseInt(listStrDate[2])
        );
    }

    /**
     * 从calendar中获取日期信息
     * @param calendar 被获取的calendar
     * @return 日期实例
     */
    private static Date getDate(Calendar calendar) {
        return new Date(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * 获取相对天数的日期实例
     * @param date 基准日期
     * @param days 相对天数
     * @return 相对天数的实例
     */
    public static Date getDateAdd(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(),date.getMonth()-1,date.getDay());
        cal.add(Calendar.DAY_OF_MONTH, days);
        return getDate(cal);
    }

    /**
     * Date的比较函数
     *
     * @param d1 date1
     * @param d2 date2
     * @return result
     */
    public static int compareDate(Date d1, Date d2) {
        if (d1.getYear() != d2.getYear()) {
            return d1.getYear() - d2.getYear();
        }
        else if (d1.getMonth() != d2.getMonth()) {
            return d1.getMonth() - d2.getMonth();
        }
        else if (d1.getDay() != d2.getDay()) {
            return d1.getDay() - d2.getDay();
        }
        else {
            return 0;
        }
    }
}
