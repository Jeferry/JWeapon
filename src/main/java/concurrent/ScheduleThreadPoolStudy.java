package concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author mao
 */
public class ScheduleThreadPoolStudy {

    private static DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

    public static void main(String[] args) {
        //指定有意义的线程名称，方便出错时回溯
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("newFixedThreadPool-%d").build();
        //FixedThreadPool
        ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(3, namedThreadFactory);

        printTimeNow(true);
        //execute once after delay
        scheduleService.schedule(() -> {
            printTimeNow(false);
            System.out.println(Thread.currentThread().getName() + " Boom!!!——Once");
        }, 3, SECONDS);

        scheduleService.scheduleAtFixedRate(() -> {
            printTimeNow(false);
            System.out.println(Thread.currentThread().getName() + " Boom!!!——NOT ONCE");
        }, 5, 2, SECONDS);
    }

    private static void printTimeNow(boolean withln) {
        String date = LocalDateTime.now().format(PATTERN);
        if (withln) {
            System.out.println(date);
        } else {
            System.out.print(date + " ");
        }
    }
}
