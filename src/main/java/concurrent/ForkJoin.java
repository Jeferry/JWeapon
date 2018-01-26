/**
 * YOUTU SOFTWARE Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package concurrent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author maojifeng
 * @version ForkJoin.java, v 0.1 maojifeng
 * @date 2018/1/25 14:59
 * @comment ForkJoin framework study
 */
public class ForkJoin {


    class ForkJoinSumTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = -1765649363912569375L;

        private static final int THRESHOLD = 500000;

        private long[] array;

        private int low;

        private int high;

        ForkJoinSumTask(long[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            // 小于阈值则直接计算
            if (high - low <= THRESHOLD) {
                for (int i = low; i < high; i++) {
                    sum += array[i];
                }
            }
            // 否则，切割成2个子任务
            int mid = (low + high) >>> 1;
            ForkJoinSumTask left = new ForkJoinSumTask(array, low, mid);
            ForkJoinSumTask right = new ForkJoinSumTask(array, mid + 1, high);

            // 2.分别计算
            left.fork();
            right.fork();

            // 3.合并结果
            sum = left.join() + right.join();

            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = getArray(1000000);

        System.out.println(Arrays.toString(array));

        // 1.创建任务
        ForkJoin forkJoin = new ForkJoin();
        ForkJoinSumTask forkJoinSumTask = forkJoin.new ForkJoinSumTask(array, 0, array.length - 1);

        long begin = System.currentTimeMillis();

        // 2、创建线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 3、提交到线程池
        forkJoinPool.submit(forkJoinSumTask);

        // 4、获取结果
        int result = forkJoinSumTask.get();

        long end = System.currentTimeMillis();

        System.out.println(String.format("结果 %s 耗时 %sms", result, end - begin));

        begin = System.currentTimeMillis();

        result = sumSingle(array);

        end = System.currentTimeMillis();

        System.out.println(String.format("结果 %s 耗时 %sms", result, end - begin));

    }

    private static long[] getArray(int size) {
        Random random = new Random();
        long[] newArray = new long[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = random.nextInt();
        }
        return newArray;
    }

    private static int sumSingle(long[] arrays) {
        int sum = 0;
        for (long l : arrays) {
            sum += l;
        }
        return sum;
    }

}
