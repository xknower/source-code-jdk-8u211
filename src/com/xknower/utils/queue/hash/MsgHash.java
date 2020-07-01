package com.xknower.utils.queue.hash;

import java.io.Serializable;

/**
 * 消息处理槽分配器, 根据消息标识符分配到指定的处理槽
 * <p>
 * (相同消息标识只能分配到同一个唯一的处理器槽, 同一个处理器槽可以包含不能消息标识的消息)
 *
 * @author xknower
 */
public class MsgHash implements IMsgHash {

    /**
     * 消息槽
     */
    private int slot;

    public MsgHash(int slot) {
        this.slot = slot;
    }

    @Override
    public Serializable hash(Serializable id, Serializable group) {
        return Math.abs(id.hashCode() / (group.hashCode() == 0 ? 10 : group.hashCode())) % slot;
    }

    @Override
    public Serializable hash(Serializable id) {
        return Math.abs(id.hashCode()) % slot;
    }
}
