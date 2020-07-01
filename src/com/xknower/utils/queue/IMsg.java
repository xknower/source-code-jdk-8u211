package com.xknower.utils.queue;

import java.io.Serializable;

/**
 * 消息定义
 *
 * @author xknower
 */
public interface IMsg<T> extends Serializable {

    /**
     * 消息唯一标识符
     *
     * @return ID
     */
    String hash();

    /**
     * 获取消息类型
     *
     * @return 消息类型
     */
    T type();
}
