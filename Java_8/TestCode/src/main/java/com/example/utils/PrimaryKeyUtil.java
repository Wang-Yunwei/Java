package com.example.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author WangYunwei [2021-09-30]
 */
@Component
public class PrimaryKeyUtil {

//    @Resource
//    RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * 获取流水号主键,例:202109300001
//     *
//     * @param tableName 数据库表名
//     * @return String
//     */
//    public String getFlowingWaterId(final String tableName) {
//
//        //设置Redis key的前缀,例: PHFA:MED_CLINIC_CHECK_MASTER
//        final String key = MessageFormat.format(CommonConstants.PATTERN, tableName);
//        //查询key是否存在,不存在返回1,存在的话则自增加1
//        final Long tableId = this.redisTemplate.opsForValue().increment(key, 1);
//        // 设置key过期时间1天,保证每天的流水号从1开始
//        assert tableId != null;
//        if (tableId.equals(1L)) {
//            final long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
//            log.info("{}失效时间剩余: {}秒", key, seconds);
//            this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
//        }
//        //位数为4,不够的话在左边补 0 ,比如 1 会变成  0001
//        final String value = StringUtils.leftPad(String.valueOf(tableId), 4,
//                String.valueOf(0));
//        return String.format("%s%s", DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now()), value);
//    }

    /**
     * 获取UUID
     */
    public static Object getUUID(final Integer num) {

        if (null != num) {
            if (num > 0) {
                final List<String> result =new ArrayList();
                for (int i = 0; i < num; i++) {
                    result.add(UUID.randomUUID().toString().replace("-", ""));
                }
                return result;
            }
        }
        return UUID.randomUUID().toString().replace("-", "");
    }
}
