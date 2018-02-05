/**
 * YOUTU SOFTWARE Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maojifeng
 * @version ConcurrentMapStudy.java, v 0.1 maojifeng
 * @date 2018/2/2 11:43
 * @comment 线程安全集合学习
 */
public class ConcurrentMapStudy {

    private static final ConcurrentHashMap<String, Object> concurrentMap = new ConcurrentHashMap<>(4);

    public static void main(String[] args) {
        Thread[] t = new Thread[10];
        for (int i = 0; i < t.length; i++) {
            t[i] = putValue();
        }

        for (Thread thread : t) {
            thread.run();
        }

        System.out.println("final result:id=" + concurrentMap.get("id"));

    }

    private static Thread putValue() {
        return new Thread(() -> {
            int random = new Random().nextInt();
            System.out.print("put:" + random + " result:" + concurrentMap.put("id", random));
            System.out.println(" Time:" + System.nanoTime());
        });
    }


}
