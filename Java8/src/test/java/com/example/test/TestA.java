package com.example.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-04-19]
 */
public class TestA {

    /**
     * 验证HashMap的线程不安全性
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Map<Integer,Integer> hashMap = new HashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            int threadNum = i;
            service.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    hashMap.put(threadNum * 1000 + j, j);
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("hashMap.size() = " + hashMap.size());
    }
}
