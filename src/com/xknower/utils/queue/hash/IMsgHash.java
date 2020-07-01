package com.xknower.utils.queue.hash;

import java.io.Serializable;

/**
 * 消息处理器槽 [根据消息标识, 适配消息到不同的处理槽]
 *
 * @author xknower
 */
public interface IMsgHash {

    /**
     * 消息处理操分配
     *
     * @param id 消息标识
     * @return Serializable
     */
    Serializable hash(Serializable id);

    /**
     * 支持分组的消息消息处理槽分配
     *
     * @param id    消息标识
     * @param group 消息分组
     * @return Serializable
     */
    Serializable hash(Serializable id, Serializable group);
}
