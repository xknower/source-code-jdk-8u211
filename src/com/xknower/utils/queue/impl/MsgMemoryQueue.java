package com.xknower.utils.queue.impl;

import com.xknower.utils.queue.IQueue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 消息内存队列
 *
 * @author xknower
 */
public class MsgMemoryQueue<T> implements IQueue<T> {

    private final String name;

    private final BlockingQueue<T> queue;

    public MsgMemoryQueue(String name, int size) {
        this.name = name;
        this.queue = new LinkedBlockingDeque<>(size);
    }

    @Override
    public String name() {
        return this.name;
    }

    /**
     * 放入队列时候, 设置指标
     *
     * @param message
     */
    @Override
    public void put(T message) {
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            System.out.println(this.name + " => 线程中断, 无法插入新数据");
        }
    }

    @Override
    public List<T> pullList() throws Exception {
        try {
            return Collections.singletonList(this.queue.take());
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public T pull() throws Exception {
        return this.queue.take();
    }
}
