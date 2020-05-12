package com.xknower.multithread.chapter.one;

/**
 * 线程中断
 *
 * @author xknower
 */
public class MyThreadInterrupt {

    public static void main(String[] args) {
        //
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

        //
        Thread thd = new Thread(r);
        Thread thd2 = new Thread(r);
        thd.start();
        thd2.start();

        //
        while (true) {
            double n = Math.random();
            if (n >= 0.49999999 && n < 0.50000001) {
                break;
            }

            //
            thd.interrupt();
            thd2.interrupt();
        }
    }
}
