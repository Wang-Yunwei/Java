package com.mdsd.cloud.utils;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hxn
 */
@Component
public class RedisUtil {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public static String PREFIX = "PIAT:";

    /**
     * 指定缓存失效时间
     */
    public boolean expire(final String key, final long time) {

        try {
            if (time > 0) {
                this.redisTemplate.expire(PREFIX + key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(final String key) {

        return this.redisTemplate.getExpire(PREFIX + key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(final String key) {

        try {
            return this.redisTemplate.hasKey(PREFIX + key);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(final String... key) {

        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(PREFIX + key[0]);
            } else {
                this.redisTemplate.delete(CollectionUtils.arrayToList(PREFIX + key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(final String key) {

        return PREFIX + key == null ? null : this.redisTemplate.opsForValue().get(PREFIX + key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(final String key, final Object value) {

        try {
            this.redisTemplate.opsForValue().set(PREFIX + key, value);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(final String key, final Object value, final long time) {

        try {
            if (time > 0) {
                this.redisTemplate.opsForValue().set(PREFIX + key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(PREFIX + key, value);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(final String key, final long delta) {

        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return this.redisTemplate.opsForValue().increment(PREFIX + key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(final String key, final long delta) {

        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return this.redisTemplate.opsForValue().increment(PREFIX + key, -delta);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(final String key, final String item) {

        return this.redisTemplate.opsForHash().get(PREFIX + key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(final String key) {

        return this.redisTemplate.opsForHash().entries(PREFIX + key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(final String key, final Map<String, Object> map) {

        try {
            this.redisTemplate.opsForHash().putAll(PREFIX + key, map);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(final String key, final Map<String, Object> map, final long time) {

        try {
            this.redisTemplate.opsForHash().putAll(PREFIX + key, map);
            if (time > 0) {
                this.expire(PREFIX + key, time);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(final String key, final String item, final Object value) {

        try {
            this.redisTemplate.opsForHash().put(PREFIX + key, item, value);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(final String key, final String item, final Object value, final long time) {

        try {
            this.redisTemplate.opsForHash().put(PREFIX + key, item, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(final String key, final Object... item) {

        this.redisTemplate.opsForHash().delete(PREFIX + key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(final String key, final String item) {

        return this.redisTemplate.opsForHash().hasKey(PREFIX + key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(final String key, final String item, final double by) {

        return this.redisTemplate.opsForHash().increment(PREFIX + key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(final String key, final String item, final double by) {

        return this.redisTemplate.opsForHash().increment(PREFIX + key, item, -by);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(final String key) {

        try {
            return this.redisTemplate.opsForSet().members(PREFIX + key);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(final String key, final Object value) {

        try {
            return this.redisTemplate.opsForSet().isMember(PREFIX + key, value);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(final String key, final Object... values) {

        try {
            return this.redisTemplate.opsForSet().add(PREFIX + key, values);
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(final String key, final long time, final Object... values) {

        try {
            final Long count = this.redisTemplate.opsForSet().add(PREFIX + key, values);
            if (time > 0) {
                this.expire(key, time);
            }
            return count;
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(final String key) {

        try {
            return this.redisTemplate.opsForSet().size(PREFIX + key);
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(final String key, final Object... values) {

        try {
            final Long count = this.redisTemplate.opsForSet().remove(PREFIX + key, values);
            return count;
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(final String key, final long start, final long end) {

        try {
            return this.redisTemplate.opsForList().range(PREFIX + key, start, end);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(final String key) {

        try {
            return this.redisTemplate.opsForList().size(PREFIX + key);
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(final String key, final long index) {

        try {
            return this.redisTemplate.opsForList().index(PREFIX + key, index);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(final String key, final Object value) {

        try {
            this.redisTemplate.opsForList().rightPush(PREFIX + key, value);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(final String key, final Object value, final long time) {

        try {
            this.redisTemplate.opsForList().rightPush(PREFIX + key, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(final String key, final List<Object> value) {

        try {
            this.redisTemplate.opsForList().rightPushAll(PREFIX + key, value);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(final String key, final List<Object> value, final long time) {

        try {
            this.redisTemplate.opsForList().rightPushAll(PREFIX + key, value);
            if (time > 0) {
                this.expire(key, time);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(final String key, final long index, final Object value) {

        try {
            this.redisTemplate.opsForList().set(PREFIX + key, index, value);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(final String key, final long count, final Object value) {

        try {
            final Long remove = this.redisTemplate.opsForList().remove(PREFIX + key, count, value);
            return remove;
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 模糊查询获取key值
     *
     * @param pattern
     * @return
     */
    public Set keys(final String pattern) {

        return this.redisTemplate.keys(pattern);
    }

    /**
     * 使用Redis的消息队列
     *
     * @param channel
     * @param message 消息内容
     */
    public void convertAndSend(final String channel, final Object message) {

        this.redisTemplate.convertAndSend(channel, message);
    }

    /**
     * 将数据添加到Redis的list中（从右边添加）
     *
     * @param listKey
     * @param time    有效期
     * @param values  待添加的数据
     */
    public void addToListRight(final String listKey, final long time, final TimeUnit timeUnit, final Object... values) {
        //绑定操作
        final BoundListOperations<String, Object> boundValueOperations = this.redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过期时间
        boundValueOperations.expire(time, timeUnit);
    }

    /**
     * 根据起始结束序号遍历Redis中的list
     *
     * @param listKey
     * @param start   起始序号
     * @param end     结束序号
     * @return
     */
    public List<Object> rangeList(final String listKey, final long start, final long end) {
        //绑定操作
        final BoundListOperations<String, Object> boundValueOperations = this.redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }

    /**
     * 弹出右边的值 --- 并且移除这个值
     *
     * @param listKey
     */
    public Object rifhtPop(final String listKey) {
        //绑定操作
        final BoundListOperations<String, Object> boundValueOperations = this.redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }

   /* public static void main(String[] args) throws IOException {
        String fromPic = "D:\\1239d1b727874bf09f3127d2ed498f0e.png";
        File file = new File(fromPic);
        File toPic = new File("D:\\1239d1b727874bf09f3127d2ed498f0e_1.png");
        Thumbnails.of(file).scale(0.5f).outputQuality(1f).toFile(toPic);
    }*/
}
