package com.xknower.multithread.chapter.one;

/**
 * 线程实现案例
 *
 * @author xknower
 */
public class MyThreadDemo {

    public static void main(String[] args) {
        boolean isDaemon = args.length != 0;

        //
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // 获取当前运行线程对象
                Thread thd = Thread.currentThread();
                while (true) {
                    System.out.printf("%s is %s alive and in %s " + "state%n",
                            thd.getName(), thd.isAlive() ? "" : "not", thd.getState());
                }
            }
        };

        //
        Thread t = new Thread(r, "thd1");
        if (isDaemon) {
            t.setDaemon(true);
        }
        System.out.printf("%s is %s alive and in %s state %n",
                t.getName(), t.isAlive() ? "" : "not", t.getState());

        Thread t2 = new Thread(r);
        t2.setName("thd2");
        if (isDaemon) {
            t2.setDaemon(true);
        }
        System.out.printf("%s is %s alive and in %s state %n",
                t2.getName(), t2.isAlive() ? "" : "not", t2.getState());

        t.start();
        t2.start();
    }
}
