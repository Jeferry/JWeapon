/**
 * YOUTU SOFTWARE Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author maojifeng
 * @version QueueStudy.java, v 0.1 maojifeng
 * @date 2018/1/26 15:35
 * @comment 队列学习
 */
public class QueueStudy {
    /**
     * 定义一个盘子类，可以放鸡蛋和取鸡蛋
     */
    static class BigPlate {
        /**
         * 装鸡蛋的盘子，最多放5个鸡蛋
         */
        private BlockingQueue<Object> eggs = new ArrayBlockingQueue<>(2);


        /**
         * 放鸡蛋
         *
         * @param egg
         */
        public void putEgg(Object egg) {
            try {
                eggs.put(egg);
                System.out.println("放入鸡蛋");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 取鸡蛋
         *
         * @return
         */
        public Object getEgg() {
            Object egg = null;
            try {
                egg = eggs.take();
                // 下面输出有时不准确，因为与take操作不是一个原子操作
                System.out.println("拿到鸡蛋");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return egg;
        }
    }

    /**
     * 放鸡蛋线程
     */
    static class AddThread extends Thread {

        private BigPlate bigPlate;
        private Object egg = new Object();

        AddThread(BigPlate plate) {
            this.bigPlate = plate;
        }

        @Override
        public void run() {
            bigPlate.putEgg(egg);
        }
    }

    /**
     * 取鸡蛋线程
     */
    static class GetThread extends Thread {
        private BigPlate bigPlate;

        GetThread(BigPlate bigPlate) {
            this.bigPlate = bigPlate;
        }

        @Override
        public void run() {
            bigPlate.getEgg();
        }
    }

    public static void main(String[] args) {
        BigPlate bigPlate = new BigPlate();

        // 先启动10个放鸡蛋线程
        for (int i = 0; i < 10; i++) {
            new AddThread(bigPlate).start();
        }

        // 再启动10个取鸡蛋线程
        for (int i = 0; i < 10; i++) {
            new GetThread(bigPlate).start();
        }

    }

}
