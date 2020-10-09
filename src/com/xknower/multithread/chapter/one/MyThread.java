package com.xknower.multithread.chapter.one;

/**
 * 创建线程, 通过继承 Thread
 *
 * @author xknower
 */
public class MyThread extends Thread {

    /**
     * 重写 run 方法实现线程
     */
    @Override
    public void run() {
        // perform some work
        System.out.println("Hello MyThread");
    }

    // MyThread thread = new MyThread();

}
