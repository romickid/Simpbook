package nkucs1416.simpbook.record;

import java.util.Calendar;

public class Date {
    int year;
    int month;
    int day;

    Date(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
    }

    Date() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
