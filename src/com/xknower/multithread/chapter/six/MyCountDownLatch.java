package com.xknower.multithread.chapter.six;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时门闩
 *
 * @author xknower
 */
public class MyCountDownLatch {

    final static int NTHREADS = 3;

    public static void main(String[] args) {
        // 主线程就绪之前, 禁止任何工作线程继续执行
        final CountDownLatch startSignal = new CountDownLatch(1);
        // 工作线程完成之前, 禁止主线程继续执行
        final CountDownLatch doneSignal = new CountDownLatch(NTHREADS);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    report("entered run()");
                    // wait until told to ...
                    startSignal.await();
                    // ... proceed
                    report("doing work");
                    Thread.sleep((int) (Math.random() * 1000));
                    // reduce count on which main thread is ...
                    // waiting
                    doneSignal.countDown();
                } catch (InterruptedException ie) {
                    System.err.println(ie);
                }
            }

            void report(String s) {
                System.out.println(System.currentTimeMillis() + ": " + Thread.currentThread() + ": " + s);
            }
        };

        //
        ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS);
        for (int i = 0; i < NTHREADS; i++) {
            executorService.execute(r);
        }

        try {
            System.out.println("main thread doing something");
            Thread.sleep(1000);

            // let all threads proceed
            startSignal.countDown();
            System.out.println("main thread doing something else");
            // wait for all threads to finish
            doneSignal.await();

            executorService.shutdownNow();
        } catch (InterruptedException ie) {
            System.err.println(ie);
        }
    }
}
