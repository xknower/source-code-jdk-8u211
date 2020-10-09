package com.xknower.utils.queue.entity;

import com.xknower.utils.queue.IMsg;

import java.io.Serializable;

/**
 * 消息定义 [车联网消息, T808及终端私有协议]
 * <p>
 * 消息 : 消息头 + 消息体 + 消息校验 + 消息开始结束标识符
 *
 * @author xknower
 */
public interface IPackage extends IMsg<Integer>, Serializable {

    /**
     * 数据转成字节流
     *
     * @return
     */
    default byte[] writeToBytes() {
        return new byte[0];
    }

    /**
     * 读取字节流，解析出数据
     *
     * @param bytes
     */
    default void readFromBytes(byte[] bytes) {
    }
}
