package com.xknower.multithread.chapter.four;

/**
 * 线程局部变量
 *
 * @author xknower
 */
public class MyThreadLocal {
    private static volatile ThreadLocal<String> userId = new ThreadLocal<>();

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                if (name.equals("A")) {
                    userId.set("foxtrot");
                } else {
                    userId.set("charlie");
                }
                //
                System.out.println(name + " " + userId.get());
            }
        };

        Thread thdA = new Thread(r);
        thdA.setName("A");
        Thread thdB = new Thread(r);
        thdB.setName("B");

        thdA.start();
        thdB.start();
    }
}
