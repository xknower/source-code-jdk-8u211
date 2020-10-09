package com.xknower.utils.queue.impl;

import com.xknower.utils.queue.entity.IPackage;

/**
 * 消息定义
 *
 * @author xknower
 */
public class DefaultMsg implements IPackage {

    /**
     * 消息唯一标识符
     */
    private String no;

    /**
     * 消息类型
     */
    private int type;

    @Override
    public String hash() {
        return no;
    }

    @Override
    public Integer type() {
        return type;
    }

    /**
     * 数据转成字节流
     *
     * @return
     */
    @Override
    public byte[] writeToBytes() {
        return new byte[0];
    }

    /**
     * 读取字节流，解析出数据
     *
     * @param bytes
     */
    @Override
    public void readFromBytes(byte[] bytes) {
        System.out.println();
    }
}
