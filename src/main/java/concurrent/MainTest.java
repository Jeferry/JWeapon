/**
 * YOUTU SOFTWARE Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maojifeng
 * @version MainTest.java, v 0.1 maojifeng
 * @date 2018/2/5 10:46
 * @comment 测试类
 */
public class MainTest {

    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
    static final ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<>(16);

    public static void main(String[] args) {
        String s = "id";
        int i = s.hashCode();
        int j = i >>>16;
        j = j ^ i;
        j = j & HASH_BITS;

        System.out.println(hashMap.put(s, 1));
    }

}
