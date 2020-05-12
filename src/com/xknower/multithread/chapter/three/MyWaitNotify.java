package com.xknower.multithread.chapter.three;

/**
 * 等待/通知
 *
 * @author xknower
 */
public class MyWaitNotify {

    private boolean notify = true;

    public void waitMethod() {
        synchronized (this) {
            // 存在假唤醒可能, 所以需要在 忙循环中使用
            // 调用之前测试, 确保活跃性。因为如果测试条件不存在但条件满足, 同时 通知先于 wait() 方法调用。
            // 调用之后测试, 确保安全性。确保测试条件正确通过。
            while (notify) {
                try {
                    this.wait();
                } catch (InterruptedException ie) {
                }
            }

            //
            // Perform an action that's appropriate to condition.
            System.out.println("Wait is over, and is running.");
        }
    }

    public void notifyMethod() {
        synchronized (this) {
            // set the condition.
            notify = false;

            this.notifyAll();
        }
    }

}
