package com.xknower.utils;

/**
 * 线程工具类
 */
public class ThreadUtil {

    /**
     * 休眠毫秒
     *
     * @param millis 毫秒数
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ite) {
        }
    }
}
