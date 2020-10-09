package com.xknower.utils.queue.impl;

import com.xknower.utils.queue.IMsg;
import com.xknower.utils.queue.IQueue;
import com.xknower.utils.queue.IQueueFactory;
import com.xknower.utils.queue.hash.IMsgHash;
import com.xknower.utils.queue.hash.MsgHash;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息队列工厂类
 *
 * @author xknower
 */
public class DefaultQueueFactory implements IQueueFactory<IMsg> {

    /**
     * 按组分不同的队列
     */
    private Map<Serializable, IQueue<IMsg>> groupQueueList = new ConcurrentHashMap<>();

    /**
     * 消处理槽计算
     */
    private IMsgHash msgHash;

    @Override
    public IQueue build(String name, int messageQueueSize) {
        if (msgHash == null) {
            synchronized (this) {
                if (msgHash == null) {
                    msgHash = new MsgHash(messageQueueSize);
                }
            }
        }
        return new MsgMemoryQueue(name, messageQueueSize);
    }

    @Override
    public IQueue build(int size, int messageQueueSize) {
        if (msgHash == null) {
            synchronized (this) {
                if (msgHash == null) {
                    msgHash = new MsgHash(messageQueueSize);
                }
            }
        }

        // 初始化一组消息处理队列
        IQueue msgQueue = groupQueueList()
                .computeIfAbsent(size, k -> new MsgMemoryQueue(String.format("消息队列[%s]", k), messageQueueSize));

        System.out.println(String.format("创建消息队列 ==> [ %s ]", size));
        return msgQueue;
    }

    @Override
    public IQueue send(IMsg msg) {
        return sendSpecified(msg, msgHash.hash(msg.hash(), 0));
    }

    @Override
    public IQueue sendSpecified(IMsg msg, Serializable specified) {
        IQueue<IMsg> build = groupQueueList().get(specified);
        if (build == null) {
            System.out.println(String.format("没有消息队列 => [%s]", specified));
            return null;
        }
        build.put(msg);
        return build;
    }

    /**
     * 消息队列列表
     */
    public Map<Serializable, IQueue<IMsg>> groupQueueList() {
        return groupQueueList;
    }
}
