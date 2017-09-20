package com.stacktips.view.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static boolean isSameMonth(final Calendar c1, final Calendar c2) {
        if (c1 == null || c2 == null) {
            return false;
        }

        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }

    public static boolean isToday(final Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isToday(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return isToday(calendar);
    }

    public static boolean isSameDay(final Date lhs, final Date rhs) {
        final Calendar lhsCal = toCalendar(lhs);

        final Calendar rhsCal = toCalendar(rhs);

        return isSameDay(lhsCal, rhsCal);
    }

    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null)
            throw new IllegalArgumentException("The dates must not be null");
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isPastDay(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (date.before(calendar.getTime())) ? true : false;
    }

    public static boolean isBetween(final Date day, final Date min, final Date max) {
        return day.compareTo(min) >= 0 && day.compareTo(max) <= 0;
    }

    public static Date addDays(final Date date, final int days) {
        final Calendar calendar = toCalendar(date);
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

    public static Calendar toCalendar(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date now() {
        return normalize(new Date());
    }

    public static Date normalize(final Date date) {
        final Calendar calendar = toCalendar(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

}
