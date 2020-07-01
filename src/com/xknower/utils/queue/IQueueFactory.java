package com.xknower.utils.queue;

import java.io.Serializable;

/**
 * 消息消费和生产队列, 初始化工厂类
 *
 * @author yuan
 */
public interface IQueueFactory<M> {

    /**
     * 发往指定槽, 根据消息HASH标识自动计算发往的槽
     *
     * @param msg 消息
     * @return 消息发往的队列
     */
    IQueue send(M msg);

    /**
     * 发往指定槽
     *
     * @param msg       消息
     * @param specified 指定消息发往的槽
     * @return 消息发往的队列
     */
    IQueue sendSpecified(M msg, Serializable specified);

    // 消息队列创建

    /**
     * 创建消息队列
     *
     * @param size              根据消息队列数初始化一组队列, 消息队列标识为对应的值
     * @param messageQueueCount 队列消息容量
     * @return 消息处理队列
     */
    IQueue build(int size, int messageQueueCount);

    /**
     * 创建消息队列
     *
     * @param name              队列名称
     * @param messageQueueCount 队列消息容量
     * @return 消息处理队列
     */
    IQueue build(String name, int messageQueueCount);
}
