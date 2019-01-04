package com.romickid.simpbook.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
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
    private static String getStrDate(Date date) {
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

    /**
     * 获取特定日期的所在周的星期一的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 设置一个星期的第一天是星期一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取特定日期的所在周的星期日的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 设置一个星期的第一天是星期一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day + 6);

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取特定日期的所在月的第一天的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取特定日期的所在月的最后一天的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取特定日期的所在年的第一天的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateYearFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        calendar.set(Calendar.DAY_OF_YEAR, 1);

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取特定日期的所在年的最后一天的日期实例
     *
     * @param date 特定日期
     * @return 日期实例
     */
    public static Date getDateYearLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth()-1, date.getDay());

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 构建选择日期的Dialog
     *
     * @param textView 显示日期的textView
     * @param context default
     * @return 返回Dialog
     */
    public static Dialog createDialogDate(final TextView textView, Context context) {
        Dialog dialog;
        DatePickerDialog.OnDateSetListener listener;
        Date date = getDate(textView);

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTextViewDate(textView, new Date(year, month + 1, dayOfMonth));
            }
        };

        dialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, date.getYear(), date.getMonth() - 1, date.getDay());
        return dialog;
    }

}
