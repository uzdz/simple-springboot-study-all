package com.uzdz.study.test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadMain {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new DaemonThreadFactory());

        scheduledExecutorService.schedule(new MyTask(), 1, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new MyTask(), 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(100);
    }

    static class MyTask implements Runnable {

        public MyTask() {
        }

        @Override
        public void run() {

            System.out.println("正在执行task");
        }
    }

    private static final class DaemonThreadFactory implements ThreadFactory {
        private AtomicInteger atomicInteger = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("schedule-pool-Thread-" + atomicInteger.getAndIncrement());
            thread.setDaemon(true);
            return null;
        }
    }
}
