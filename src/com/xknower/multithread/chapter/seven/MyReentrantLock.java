package com.xknower.multithread.chapter.seven;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 *
 * @author xknower
 */
public class MyReentrantLock {

    public static void main(String[] args) {
        //
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        //
        final ReentrantLock lock = new ReentrantLock();

        //
        class Work implements Runnable {
            private final String name;

            Work(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                lock.lock();
                try {
                    if (lock.isHeldByCurrentThread()) {
                        System.out.printf("Thread %s entered critical section.%n", name);
                    }
                    System.out.printf("Thread %s perfroming work.%n", name);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    System.out.printf("Thread %s finished working.%n", name);
                } finally {
                    lock.unlock();
                }
            }
        }

        executorService.execute(new Work("ThdA"));
        executorService.execute(new Work("ThdB"));

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        executorService.shutdownNow();
    }
}
