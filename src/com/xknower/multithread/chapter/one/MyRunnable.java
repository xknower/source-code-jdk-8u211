package com.xknower.multithread.chapter.one;

/**
 * 创建线程, 通过实现接口 Runnable
 *
 * @author xknower
 */
public class MyRunnable {

    private void Runnable() {
        // Runnable 创建
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // perform some work
                System.out.println("Hello from thread");
            }
        };

        // Runnable 创建, 使用 lambda
        Runnable rL = () -> System.out.println("Hello from thread");

        // Thread 创建
        Thread t = new Thread(r);

        //
    }
}
