package com.xknower.multithread.chapter.two;

/**
 * 死锁
 *
 * @author xknower
 */
public class SynchronizedDeadlock {

    private final Object lock1 = new Object();

    private final Object lock2 = new Object();

    public void instanceMethod1() {
        synchronized (lock1) {
            // lock1 临界区
            synchronized (lock2) {
                // lock2 临界区
                System.out.println("first thread in instanceMethod1");
                // critical section guarded first by lock1 and then by lock2
                // 离开 lock2 临界区
            }
            // 离开 lock1 临界区
        }
    }

    public void instanceMethod2() {
        synchronized (lock2) {
            // lock2 临界区
            synchronized (lock1) {
                // lock1 临界区
                System.out.println("second thread in instanceMethod2");
                // critical section guarded first by lock2 and then by lock1
                // 离开 lock1 临界区
            }
            // 离开 lock2 临界区
        }
    }

    public static void main(String[] args) {

        final SynchronizedDeadlock dl = new SynchronizedDeadlock();

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    dl.instanceMethod1();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        };

        Thread thdA = new Thread(r1);

        //
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    dl.instanceMethod2();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        };

        Thread thdB = new Thread(r2);

        thdA.start();
        thdB.start();
    }
}
