package com.example.utils;

import com.beust.jcommander.internal.Maps;
import org.springframework.cglib.beans.BeanMap;

import java.util.Map;

/**
 * @author WangYunwei [2022-08-22]
 */
public class MapUtil {

    public static <T> Map<String, Object> beanToMap(final T bean) {

        final Map<String, Object> newHashMap = Maps.newHashMap();
        if (null != bean) {
            final BeanMap create = BeanMap.create(bean);
            for (final Object key : create.keySet()) {
                newHashMap.put(key.toString(), create.get(key));
            }
        }
        return newHashMap;
    }

    public static <T> T mapToBean(final Map<String, Object> map, final T bean) {

        final BeanMap create = BeanMap.create(bean);
        create.putAll(map);
        return bean;
    }
}
