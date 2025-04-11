package com.mdsd.cloud.utils;

import com.mdsd.cloud.response.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {
    /**
     * List属性拷贝
     *
     * @param sourceList  源List
     * @param targetClass 目标类
     * @param <V>         源类型
     * @param <T>         目标类型
     * @return 转换后的List
     */
    public static <V, T> List<T> copyList(List<V> sourceList, Class<T> targetClass) {
        Constructor<T> constructor;
        try {
            constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new BusinessException("内部错误");
        }
        List<T> targetList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            for (V source : sourceList) {
                T target = null;
                try {
                    target = constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            }
        }
        return targetList;
    }

    /**
     * 根据字段对列表分组
     *
     * @param sourceList 未分组列表
     * @param classifier 分组字段
     * @param <T>        输入元素的类型
     * @param <K>        分组字段类型
     * @return 已分组列表
     * @see Collectors#groupingBy(Function)
     */
    public static <T, K> Map<K, List<T>> groupingByKey(List<T> sourceList, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        return groupingByKeyForStream(sourceList.parallelStream(), classifier);
    }

    /**
     * 根据字段统计总数
     *
     * @param sourceList 未分组列表
     * @param classifier 分组字段
     * @param <T>        输入元素的类型
     * @param <K>        分组字段类型
     * @return 统计的总数
     */
    public static <T, K> Map<K, Long> countByKey(List<T> sourceList, Function<? super T, ? extends K> classifier) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        return sourceList.stream().collect(Collectors.groupingBy(classifier, Collectors.counting()));
    }

    public static <T, K> Map<K, Long> countByKey(List<T> sourceList, Function<? super T, ? extends K> classifier,
                                                 Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        return sourceList.stream().filter(predicate).collect(Collectors.groupingBy(classifier, Collectors.counting()));
    }

    public static <T, K> Map<K, List<T>> groupingByKeyForStream(Stream<T> sourceStream, Function<? super T, ? extends K> classifier) {
        if (sourceStream == null) {
            return null;
        }
        return sourceStream.collect(Collectors.groupingBy(classifier));
    }
}
