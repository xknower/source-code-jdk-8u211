package com.xknower.multithread.chapter.one;

/**
 * 线程睡眠
 *
 * @author xknower
 */
public class MyThreadSleep {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                int count = 0;
                while (!Thread.interrupted()) {
                    System.out.println(name + ": " + count++);
                }
            }
        };

        Thread tA = new Thread(r);
        Thread tB = new Thread(r);

        tA.start();
        tB.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }

        tA.interrupt();
        tB.interrupt();
    }
}
