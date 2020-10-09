package com.xknower.utils.queue.handler;

import java.io.Serializable;

/**
 * 消息消费处理器适配器 [一个消息只能被一个消费处理器处理]
 * <p>
 *
 * @param <M> 消息对象
 */
public interface IHandler<M> {

    /**
     * 消息消费处理器标识
     *
     * @return Serializable
     */
    Serializable support();

    /**
     * 消息消费处理器
     *
     * @param m 消息
     */
    void execute(M m);
}
