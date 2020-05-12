package com.xknower.multithread.chapter.two;

/**
 * 同步块, synchronized 关键字 将代码块
 * <p>
 * synchronized (object) {
 * }
 *
 * @author xknower
 */
public class MySynchronizedBlock {

    // 多个同步块, 标识一对临界区, 实现同一时刻, 只能由一个线程在这些同步块中执行
    private void run() {
        Runnable r = () -> {
            synchronized (this) {
                // 相同临界区
            }
        };

        synchronized (this) {
            // 相同临界区
        }
    }

}
