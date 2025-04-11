package com.mdsd.cloud.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-07-01]
 */
@Slf4j
public class MyLocalDateUtil {

    /**
     * 根据年获取日期
     *
     * @param year 年份
     * @return List<String> 例:["2022-01-01","2022-01-02","2022-01-03",...]
     */
    public static List<String> getDateByYear(final int year) {

        final List<String> result = Lists.newArrayList();
        final String str = "%s-%s-%s";
        for (int i = 1; i <= 12; i++) {
            final String month = StringUtils.leftPad(String.valueOf(i), 2,
                    String.valueOf(0));
            final LocalDate firstDayOfYear = LocalDate.parse(String.format(str, year, month, "01"));
            final int lastDayOfMonth = Integer.parseInt(firstDayOfYear.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("dd")));
            for (int j = 1; j <= lastDayOfMonth; j++) {
                result.add(String.format(str, year, month, StringUtils.leftPad(String.valueOf(j), 2,
                        String.valueOf(0))));
            }
        }
        return result;
    }

    /**
     * 获取两个日期的时间差
     *
     * @param param1 日期1
     * @param param2 日期2
     */
    public static int timeDifference(final LocalDateTime param1, final LocalDateTime param2) {

        log.info("计算【{}】和【{}】两个时间的差", param1, param2);
        //获取秒数
        final long param1Second = param1.toEpochSecond(ZoneOffset.ofHours(0));
        final long param2Second = param2.toEpochSecond(ZoneOffset.ofHours(0));
        log.info("param1= {} 秒,param2= {} 秒", param1Second, param2Second);
        final int absSeconds = Math.toIntExact(Math.abs(param1Second - param2Second));
        final int ss = absSeconds % 60;//获取秒数
        final int mm = absSeconds / 60 % 60;//获取分钟数
        final int HH = absSeconds / 60 / 60 % 24;//获取小时数
        final int dd = absSeconds / 60 / 60 / 24;//获取天数
        log.info("param1与param2之间相差: {}天{}小时{}分{}秒", dd, HH, mm, ss);
        return absSeconds;
    }

    /**
     * 获取两个月份之前的字符串日期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    public static List<String> getMonthOfDate(final LocalDate startDate, final LocalDate endDate) {

        final List<String> result = Lists.newArrayList();
        if (startDate.getYear() < endDate.getYear()) {
            //年份不同
            for (int i = startDate.getMonthValue(); i <= 12; i++) {
                result.add(String.format("%s-%s-01", startDate.getYear(), StringUtils.leftPad(String.valueOf(i), 2, String.valueOf(0))));
            }
            for (int i = 1; i < endDate.getMonthValue(); i++) {
                result.add(String.format("%s-%s-01", endDate.getYear(), StringUtils.leftPad(String.valueOf(i), 2, String.valueOf(0))));
            }
        } else {
            //年份相同
            for (int i = startDate.getMonthValue(); i < endDate.getMonthValue(); i++) {
                result.add(String.format("%s-%s-01", startDate.getYear(), StringUtils.leftPad(String.valueOf(i), 2, String.valueOf(0))));
            }
        }
        return result;
    }

    /**
     * 获取日期的开始时间和结束时间
     *
     * @param localDate 日期
     * @return Map<String, LocalDateTime> 例: {"todayStart",2022-07-05 00:00:00},{"todayEnd",2022-07-05 23:59:59}
     */
    public static Map<String, LocalDateTime> getTodayStartAndEnd(final LocalDate localDate) {

        final Map<String, LocalDateTime> result = Maps.newHashMap();
        result.put("todayStart", LocalDateTime.of(localDate, LocalTime.MIN));
        result.put("todayEnd", LocalDateTime.of(localDate, LocalTime.MAX));
        return result;
    }

}
