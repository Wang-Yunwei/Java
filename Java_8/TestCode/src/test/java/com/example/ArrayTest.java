package com.example;

import org.springframework.util.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2024-06-12]
 */
public class ArrayTest {

    private static byte count;

    public static void main(String[] args) {

        System.out.println(count);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("123", "456");
        map.values().forEach(el -> System.out.println(el));

    }
}
