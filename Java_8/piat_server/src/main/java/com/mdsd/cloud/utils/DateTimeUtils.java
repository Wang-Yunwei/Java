package com.mdsd.cloud.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wgy
 **/
public class DateTimeUtils {
    //计算两个时间差
    public static String minsBetween(final Date date1, final Date date2) {

        final Calendar cal = Calendar.getInstance();
        if (date1 == null || date2 == null) {
            return "0";
        }
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        //算上当天
        final int abs = Math.abs(Integer.parseInt(String.valueOf((time2 - time1) / 60000L)) + 1);
        return String.valueOf(abs);
    }

    /**
     * Date转localDateTime
     */
    public static LocalDateTime changeDate(final Date date) {

        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }

    /**
     * localDateTime转String
     */
    public static String changeString(final LocalDateTime date) {

        final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String localTime = df.format(date);
        return localTime;
    }
}
