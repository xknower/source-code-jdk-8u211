package com.xknower.utils.queue.handler;

/**
 * 消息消费监控适配器 [同一个消息可以被不同的监控器监控处理]
 *
 * @param <T> 消息对象
 */
public interface IMonitor<T> {

    /**
     * 消息监控器
     *
     * @param t 消息
     */
    void monitor(T t);
}
