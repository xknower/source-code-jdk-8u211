package com.xknower.utils.queue;

import java.util.List;

/**
 * 消息处理器队列
 *
 * @author xknower
 */
public interface IQueue<M> {

    /**
     * 获取队列名
     *
     * @return Name
     */
    String name();

    /**
     * 压入消息
     *
     * @param msg
     */
    void put(M msg);

    /**
     * 弹出消息
     *
     * @return 消息列表
     * @throws Exception
     */
    List<M> pullList() throws Exception;

    /**
     * 弹出消息
     *
     * @return 唯一消息
     * @throws Exception
     */
    M pull() throws Exception;
}
