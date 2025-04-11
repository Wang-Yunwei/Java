package com.mdsd.cloud.utils;

import com.mdsd.cloud.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author WangYunwei [2021-09-30]
 */
@Slf4j
@Component
public class PrimaryKeyUtil {

    RedisUtil redisUtil;

    public PrimaryKeyUtil(final RedisUtil redisUtil) {

        this.redisUtil = redisUtil;
    }

    /**
     * 获取流水号主键,例:202109300001
     *
     * @param tableName 数据库表名
     * @return String
     */
    public String getFlowingWaterId(final String tableName) {

        //设置Redis key的前缀,例: TABLE_KEY:MED_CLINIC_CHECK_MASTER
        final String key = MessageFormat.format(CommonConstants.PATTERN, CommonConstants.TABLE_KEY, tableName);
        //查询key是否存在,不存在返回1,存在的话则自增加1
        final Long tableId = this.redisUtil.incr(key, CommonConstants.ONE);
        // 设置key过期时间1天,保证每天的流水号从1开始
        if (tableId == CommonConstants.ONE) {
            final long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
            log.info("{}失效时间剩余: {}秒", key, seconds);
            this.redisUtil.expire(key, seconds);
        }
        //位数为4,不够的话在左边补 0 ,比如 1 会变成  0001
        final String value = StringUtils.leftPad(String.valueOf(tableId), CommonConstants.FOUR,
                String.valueOf(CommonConstants.ZERO));
        return String.format("%s%s", DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()), value);
    }
}
