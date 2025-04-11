package com.mdsd.cloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
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
