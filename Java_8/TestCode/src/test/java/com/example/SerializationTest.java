package com.example;

import org.springframework.util.SerializationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangYunwei [2024-02-02]
 */
public class SerializationTest {

    public static void main(String[] args){

        HashMap<String, String> map = new HashMap<>();

        map.put("1","1");
        map.put("2","2");
        map.put("3","3");

        byte[] serialize = SerializationUtils.serialize(map);
        Object deserialize = SerializationUtils.deserialize(serialize);
        System.out.println(serialize);
        System.out.println(deserialize);
    }
}
