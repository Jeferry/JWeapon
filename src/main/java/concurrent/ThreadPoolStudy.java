package concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mao
 */
public class ThreadPoolStudy {
    public static void main(String[] args) {
        //指定有意义的线程名称，方便出错时回溯
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("newFixedThreadPool-%d").build();
        //FixedThreadPool
        ExecutorService threadPool = Executors.newFixedThreadPool(3, namedThreadFactory);
        //CachedThreadPool
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(namedThreadFactory);
        //SingleThreadExecutor
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(namedThreadFactory);

        Thread t = new Thread(() -> System.out.println("Thread run!"));
        t.run();

        try {

            for (int i = 1; i < 5; i++) {
                final int taskID = i;
                threadPool.execute(() -> {
                    for (int j = 1; j < 5; j++) {
                        try {
                            // 为了测试出效果，让每次任务执行都需要一定时间
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + ":第" + taskID + "次任务的第" + j + "次执行");
                    }
                });
            }
        } finally {
            // 任务执行完毕，关闭线程池。平时使用，线程池一般是不会关闭的
            threadPool.shutdown();
        }
    }
}
